package com.simats.goodlook

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppNotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val context = applicationContext
        if (!SessionManager.isLoggedIn(context)) return

        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: "Unknown"
        val text = extras.getCharSequence("android.text")?.toString() ?: ""

        var appSource = ""
        var track = false

        if (packageName.contains("whatsapp", ignoreCase = true)) {
            if (SessionManager.getToggleState(context, "whatsappEnabled")) {
                appSource = "WhatsApp"
                track = true
            }
        } else if (packageName.contains("instagram", ignoreCase = true)) {
            if (SessionManager.getToggleState(context, "instagramEnabled")) {
                appSource = "Instagram"
                track = true
            }
        } else if (packageName.contains("messaging", ignoreCase = true) || packageName.contains("sms", ignoreCase = true)) {
            if (SessionManager.getToggleState(context, "notificationsEnabled", true)) {
                appSource = "Messages"
                track = true
            }
        } else {
            // General OS notifications (emails, updates, etc)
            if (SessionManager.getToggleState(context, "notificationsEnabled", true)) {
                appSource = "Notifications"
                track = true
            }
        }

        if (track && appSource.isNotEmpty() && text.isNotEmpty()) {
            val email = SessionManager.getEmail(context)
            val req = com.simats.com.network.request.NotificationUploadRequest(
                username = email,
                app_source = appSource,
                sender = title, // Usually the person messaging them
                message_content = text,
                timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    com.simats.com.network.response.ApiClient.apiService.uploadNotification(req)
                } catch (e: Exception) {}
            }
        }
    }
}
