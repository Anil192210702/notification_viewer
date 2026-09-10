package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCallMonitoringScreen(onNavigate: (String) -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    var liveCalls by remember { mutableStateOf<List<com.simats.com.network.response.NotificationItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val email = com.simats.goodlook.SessionManager.loggedInEmail
            val res = com.simats.com.network.response.ApiClient.apiService.getNotifications(com.simats.com.network.request.GetNotificationsRequest(email))
            if(res.isSuccessful) {
                liveCalls = res.body()?.notifications?.filter { it.app_source.equals("Call", ignoreCase = true) } ?: emptyList()
            }
        } catch(e: Exception) {}
    }
    
    val filteredCalls = liveCalls.filter {
        (selectedFilter == "All" || it.message_content.contains(selectedFilter, ignoreCase = true)) &&
        (it.sender.contains(searchQuery, ignoreCase = true) || it.message_content.contains(searchQuery, ignoreCase = true))
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Call Monitoring", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            TextButton(onClick = {
                val email = com.simats.goodlook.SessionManager.loggedInEmail
                scope.launch {
                    try {
                        val req = com.simats.com.network.request.ClearDataRequest(email, "calls")
                        val res = com.simats.com.network.response.ApiClient.apiService.clearData(req)
                        if (res.isSuccessful) {
                            liveCalls = emptyList()
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
        
        Column(modifier = Modifier.padding(20.dp)) {
            // Metrics Header
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(16.dp)).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Calls Logged", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Text("${liveCalls.size}", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color.Black)
                }
                Box(
                    modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFEFF6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, tint = Color(0xFF3B82F6))
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by name or number...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Filter Scroll Row fixing Text wrapping using horizontalScroll
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), 
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Incoming", "Missed", "Answered").forEach { filterType ->
                    FilterChip(
                        selected = selectedFilter == filterType,
                        onClick = { selectedFilter = filterType },
                        label = { Text(filterType, maxLines = 1, overflow = TextOverflow.Visible) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF3B82F6),
                            selectedLabelColor = Color.White,
                            labelColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCalls) { call ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onNavigate("call_details") },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFF3B82F6).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Call, contentDescription = "Call", tint = Color(0xFF3B82F6), modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Family Member: ${call.user__username}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF3B82F6), fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(call.sender, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(call.message_content, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Logged", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color(0xFF3B82F6))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(call.timestamp.substringBefore(" "), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
