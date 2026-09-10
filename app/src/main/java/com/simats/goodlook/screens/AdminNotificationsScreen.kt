package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.com.network.response.ApiClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNotificationsScreen() {
    var notifications by remember { mutableStateOf<List<com.simats.com.network.response.NotificationItem>>(emptyList()) }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedChatSender by remember { mutableStateOf<String?>(null) }
    
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val prefs = context.getSharedPreferences("ReadTimestamps", android.content.Context.MODE_PRIVATE)

    // Tracks the most recent timestamp read for each specific Sender natively clearing Badges
    val localReadTimestamps = remember { mutableStateMapOf<String, String>() }

    LaunchedEffect(Unit) {
        prefs.all.forEach { (key, value) ->
            if (value is String) {
                localReadTimestamps[key] = value
            }
        }
    }
    
    val filters = listOf("All", "WhatsApp", "Instagram", "Messages", "Settings")

    LaunchedEffect(Unit) {
        try {
            val email = com.simats.goodlook.SessionManager.loggedInEmail
            val res = ApiClient.apiService.getNotifications(com.simats.com.network.request.GetNotificationsRequest(email))
            if(res.isSuccessful) {
                notifications = res.body()?.notifications?.filter { !it.app_source.equals("Call", ignoreCase = true) } ?: emptyList()
            }
        } catch(e: Exception) {}
    }

    val filteredNotifications = notifications.filter {
        selectedFilter == "All" || it.app_source.equals(selectedFilter, ignoreCase = true)
    }
    
    val isChatCategory = selectedFilter.lowercase() in listOf("whatsapp", "instagram", "messages")

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (selectedChatSender != null) {
                    IconButton(onClick = { selectedChatSender = null }, modifier = Modifier.padding(end = 8.dp)) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                }
                Text(if (selectedChatSender != null) selectedChatSender!! else "App Notifications", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            }
            if (selectedChatSender == null) {
                TextButton(onClick = {
                    val email = com.simats.goodlook.SessionManager.loggedInEmail
                    scope.launch {
                        try {
                            val req = com.simats.com.network.request.ClearDataRequest(email, "notifications")
                            val res = com.simats.com.network.response.ApiClient.apiService.clearData(req)
                            if (res.isSuccessful) {
                                notifications = emptyList()
                                android.widget.Toast.makeText(context, "Deleted successfully from DB!", android.widget.Toast.LENGTH_SHORT).show()
                            } else {
                                android.widget.Toast.makeText(context, "Failed to clear DB. Did you restart the Django server?", android.widget.Toast.LENGTH_LONG).show()
                            }
                        } catch(e: Exception) {}
                    }
                }) {
                    Text("Clear Data", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        if (selectedChatSender == null) {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color(0xFF3B82F6) else Color(0xFFE5E7EB))
                            .selectable(selected = isSelected, onClick = { 
                                selectedFilter = filter
                                selectedChatSender = null
                            }, role = Role.Tab)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        
        Box(modifier = Modifier.weight(1f)) {
            if (filteredNotifications.isEmpty()) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     Text("No data available for $selectedFilter.", color = Color.Gray)
                 }
            } else {
                if (isChatCategory && selectedChatSender == null) {
                    val groupedSenders = filteredNotifications.groupBy { it.sender }
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(groupedSenders.keys.toList()) { sender ->
                            val latestMsg = groupedSenders[sender]?.maxByOrNull { it.timestamp }
                            
                            // Calculate Unread dynamically based on internal Read Timestamps cache natively
                            val readTime = localReadTimestamps[sender] ?: ""
                            val msgCount = groupedSenders[sender]?.count { it.timestamp > readTime } ?: 0
                            
                            val (icon, bg) = when (selectedFilter.lowercase()) {
                                "whatsapp" -> Icons.Default.Share to Color(0xFFDCFCE7)
                                "instagram" -> Icons.Default.Person to Color(0xFFFFEDD5)
                                else -> Icons.Default.Email to Color(0xFFE0E7FF)
                            }
                            
                            Card(
                                modifier = Modifier.fillMaxWidth().clickable { 
                                    // Mark all up to the latest timestamp structurally tracking logic
                                    localReadTimestamps[sender] = latestMsg?.timestamp ?: ""
                                    prefs.edit().putString(sender, latestMsg?.timestamp ?: "").apply()
                                    selectedChatSender = sender 
                                },
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(2.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier.size(48.dp).clip(CircleShape).background(bg),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(icon, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(24.dp))
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Text(sender, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                                            if (msgCount > 0) {
                                                Box(modifier = Modifier.background(Color(0xFF3B82F6), CircleShape).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                                    Text(msgCount.toString(), style = MaterialTheme.typography.labelSmall, color = Color.White)
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(latestMsg?.message_content ?: "", style = MaterialTheme.typography.bodyMedium, color = Color.Gray, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                } else if (isChatCategory && selectedChatSender != null) {
                    val chatMessages = filteredNotifications.filter { it.sender == selectedChatSender }.sortedBy { it.timestamp }
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(chatMessages) { msg ->
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                                Column(modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp)).padding(12.dp)) {
                                    Text(msg.message_content, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(msg.timestamp, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        item {
                            Text("$selectedFilter Events", color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                        }
                        items(filteredNotifications) { notif ->
                            val (icon, bg) = when (notif.app_source.lowercase()) {
                                "whatsapp" -> Icons.Default.Share to Color(0xFFDCFCE7)
                                "instagram" -> Icons.Default.Person to Color(0xFFFFEDD5)
                                "messages" -> Icons.Default.Email to Color(0xFFE0E7FF)
                                "settings" -> Icons.Default.Settings to Color(0xFFF3F4F6)
                                else -> Icons.Default.Info to Color(0xFFEFF6FF)
                            }
                            CardsLayoutBlock(notif, icon, bg)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardsLayoutBlock(notif: com.simats.com.network.response.NotificationItem, icon: androidx.compose.ui.graphics.vector.ImageVector, bg: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape).background(bg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(notif.user__username, style = MaterialTheme.typography.labelSmall, color = Color.White, modifier = Modifier.background(Color.Black, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp))
                    Text(notif.app_source, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(notif.sender, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text(notif.message_content, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(notif.timestamp, style = MaterialTheme.typography.labelSmall, color = Color.LightGray)
            }
        }
    }
}
