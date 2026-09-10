package com.simats.goodlook.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp

data class CallRecord(
    val type: String,
    val name: String,
    val number: String,
    val time: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconTint: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallAlertsScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    
    val allCalls = listOf(
        CallRecord("Incoming", "Mom", "+91 98765 43210", "10:45 AM", Icons.Default.Call, Color(0xFF10B981)),
        CallRecord("Missed", "Unknown", "+91 88888 77777", "09:30 AM", Icons.Default.Warning, Color(0xFFEF4444)),
        CallRecord("Answered", "Dad", "+91 99999 55555", "Yesterday", Icons.Default.Call, Color(0xFF3B82F6)),
        CallRecord("Missed", "Spam", "+91 12345 67890", "Yesterday", Icons.Default.Warning, Color(0xFFEF4444)),
        CallRecord("Incoming", "Brother", "+91 91234 56789", "Sep 5", Icons.Default.Call, Color(0xFF10B981))
    )
    
    val filteredCalls = allCalls.filter {
        (selectedFilter == "All" || it.type == selectedFilter) &&
        (it.name.contains(searchQuery, ignoreCase = true) || it.number.contains(searchQuery))
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        // App Bar with explicit Black title color
        TopAppBar(
            title = { Text("Call Alerts", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color.Black)
        )
        
        Column(modifier = Modifier.padding(20.dp)) {
            // Metrics Header
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(16.dp)).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Calls Logged", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Text("142", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color.Black)
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
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(44.dp).clip(CircleShape).background(call.iconTint.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(call.icon, contentDescription = call.type, tint = call.iconTint, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(call.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(call.number, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(call.type, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = call.iconTint)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(call.time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
