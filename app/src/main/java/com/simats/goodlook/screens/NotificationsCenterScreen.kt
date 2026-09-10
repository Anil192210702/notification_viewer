package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class UIMessage(val title: String, val body: String, val time: String, val isAlert: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsCenterScreen() {
    val messages = listOf(
        UIMessage("Admin Location Check", "The admin requested an update on your live location.", "10:45 AM", false),
        UIMessage("Security Alert", "A new device logged into your safe zone.", "Yesterday", true),
        UIMessage("Admin Pinned Message", "Please remember to keep location tracking enabled during trips.", "Sep 5", false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold, color = Color.Black) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFFAFAFA))) {
            LazyColumn(
                modifier = Modifier.padding(16.dp).fillMaxSize(), 
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("Messages from Admin", color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                }
                items(messages) { msg ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            val iconColor = if (msg.isAlert) Color(0xFFEF4444) else Color(0xFF3B82F6)
                            val icon = if (msg.isAlert) Icons.Default.Warning else Icons.Default.Info
                            Box(
                                modifier = Modifier.size(56.dp).clip(CircleShape).background(iconColor.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(msg.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(msg.body, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(msg.time, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
