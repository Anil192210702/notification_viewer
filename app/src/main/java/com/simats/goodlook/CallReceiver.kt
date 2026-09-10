package com.simats.goodlook

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.PHONE_STATE") {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            
            val prefs = context.getSharedPreferences("CallPrefs", Context.MODE_PRIVATE)
            val lastState = prefs.getString("lastState", TelephonyManager.EXTRA_STATE_IDLE)
            val isIncoming = prefs.getBoolean("isIncoming", false)
            
            // If the Intent provided a valid number, save it! This prevents duplicate empty broadcasts from overwriting it.
            if (!incomingNumber.isNullOrBlank() && incomingNumber != "Unknown Number") {
                prefs.edit().putString("savedNumber", incomingNumber).apply()
            }
            
            if (state == lastState) return
            
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                prefs.edit()
                     .putBoolean("isIncoming", true)
                     .putString("lastState", state).apply()
            } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                if (lastState == TelephonyManager.EXTRA_STATE_RINGING) {
                     prefs.edit().putBoolean("isIncoming", true).putString("lastState", state).apply()
                } else {
                     prefs.edit().putBoolean("isIncoming", false).putString("lastState", state).apply()
                }
            } else if (state == TelephonyManager.EXTRA_STATE_IDLE) {
                var savedNum = prefs.getString("savedNumber", "") ?: ""
                
                // Fallback: If we still don't have the number, query the CallLog for the last call
                if (savedNum.isBlank() || savedNum == "Unknown Number") {
                    try {
                        val cursor = context.contentResolver.query(
                            android.provider.CallLog.Calls.CONTENT_URI,
                            arrayOf(android.provider.CallLog.Calls.NUMBER),
                            null, null, android.provider.CallLog.Calls.DATE + " DESC LIMIT 1"
                        )
                        cursor?.use {
                            if (it.moveToFirst()) {
                                savedNum = it.getString(0) ?: ""
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                
                if (savedNum.isBlank()) savedNum = "Unknown Number"
                
                val resolvedName = getContactName(context, savedNum)
                val finalNameAndNumber = if (resolvedName != savedNum) {
                    "$resolvedName\n$savedNum"
                } else {
                    "Unknown\n$savedNum"
                }
                
                val statusText = if (lastState == TelephonyManager.EXTRA_STATE_RINGING) {
                    "Missed Call"
                } else if (isIncoming) {
                    "Answered Call"
                } else {
                    "Outgoing Call"
                }
                
                prefs.edit().putString("lastState", state).putString("savedNumber", "").apply() // Reset saved number

                
                if (SessionManager.isLoggedIn(context) && SessionManager.getToggleState(context, "callsEnabled")) {
                    val email = SessionManager.getEmail(context)
                    val req = com.simats.com.network.request.NotificationUploadRequest(
                        username = email,
                        app_source = "Call",
                        sender = finalNameAndNumber,
                        message_content = statusText,
                        timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
                    )
                    
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            com.simats.com.network.response.ApiClient.apiService.uploadNotification(req)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    private fun getContactName(context: Context, phoneNumber: String): String {
        if (phoneNumber == "Unknown Number" || phoneNumber.isBlank()) return phoneNumber
        var contactName = phoneNumber
        try {
            val uri = android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI.buildUpon()
                .appendPath(phoneNumber).build()
            val projection = arrayOf(android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME)
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME)
                    if (index != -1) {
                        contactName = cursor.getString(index)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return contactName
    }
}
