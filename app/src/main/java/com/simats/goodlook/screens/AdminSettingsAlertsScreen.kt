package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.simats.com.network.response.ApiClient
import com.simats.com.network.response.NotificationItem

@Composable
fun AdminSettingsAlertsScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    var settingsAlerts by remember { mutableStateOf<List<NotificationItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val email = com.simats.goodlook.SessionManager.loggedInEmail
        try {
            val res = ApiClient.apiService.getNotifications(com.simats.com.network.request.GetNotificationsRequest(email))
            if(res.isSuccessful) {
                settingsAlerts = res.body()?.notifications?.filter { it.app_source == "Settings" } ?: emptyList()
            }
        } catch(e: Exception) {}
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Permission Alerts", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            TextButton(onClick = {
                val email = com.simats.goodlook.SessionManager.loggedInEmail
                scope.launch {
                    try {
                        val req = com.simats.com.network.request.ClearDataRequest(email, "notifications")
                        val res = ApiClient.apiService.clearData(req)
                        if (res.isSuccessful) {
                            settingsAlerts = emptyList()
                            android.widget.Toast.makeText(context, "Deleted successfully from DB!", android.widget.Toast.LENGTH_SHORT).show()
                        } else {
                            android.widget.Toast.makeText(context, "Failed to clear DB. Check Render logs.", android.widget.Toast.LENGTH_LONG).show()
                        }
                    } catch(e: Exception) {}
                }
            }) {
                Text("Clear Data", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            if (settingsAlerts.isEmpty()) {
                Text("No recent permission changes from paired devices.", modifier = Modifier.padding(16.dp), color = Color.Gray)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(settingsAlerts) { alert ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFFACC15).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Warning, contentDescription = "Alert", tint = Color(0xFFCA8A04), modifier = Modifier.size(24.dp))
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(alert.message_content, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(alert.timestamp.substringBefore(" "), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
