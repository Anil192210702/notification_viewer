package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.simats.com.network.response.ApiClient
import com.simats.com.network.request.GetNotificationsRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminFamilyMembersScreen() {
    val scope = rememberCoroutineScope()
    var showAddDialog by remember { mutableStateOf(false) }
    var generatedId by remember { mutableStateOf("") }
    var showViewDialog by remember { mutableStateOf(false) }
    var selectedMember by remember { mutableStateOf<com.simats.com.network.response.FamilyMemberItem?>(null) }
    var members by remember { mutableStateOf<List<com.simats.com.network.response.FamilyMemberItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val email = com.simats.goodlook.SessionManager.loggedInEmail
            val res = ApiClient.apiService.getFamilyMembers(com.simats.com.network.request.GetFamilyMembersRequest(email))
            if(res.isSuccessful) {
                members = res.body()?.members ?: emptyList()
            }
        } catch(e: Exception) {}
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Family Members", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    scope.launch {
                        try {
                            val email = com.simats.goodlook.SessionManager.loggedInEmail
                            val res = ApiClient.apiService.getFamilyMembers(com.simats.com.network.request.GetFamilyMembersRequest(email))
                            if(res.isSuccessful) {
                                members = res.body()?.members ?: emptyList()
                            }
                        } catch(e: Exception) {}
                    }
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { showAddDialog = true }) { Text("+ Add Member") }
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Connection", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Actions", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                if (members.isEmpty()) {
                    Text("No connected devices yet.", modifier = Modifier.padding(16.dp))
                } else {
                    members.forEach { member ->
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(member.username + "\nPhone: " + (member.phone?.takeIf { it.isNotBlank() } ?: "N/A") + "\nFamily ID: " + member.id, style = MaterialTheme.typography.bodyMedium)
                                if (!member.device.isNullOrEmpty()) {
                                    Text("Device: " + member.device + " (Battery: " + member.battery + "%)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                            Text(member.status, color = if(member.status == "Connected") Color(0xFF4CAF50) else Color.Gray, modifier = Modifier.weight(0.7f), fontSize = 12.sp)
                            Column(modifier = Modifier.weight(1.5f), horizontalAlignment = Alignment.End) {
                                TextButton(onClick = { 
                                    scope.launch {
                                        try {
                                            ApiClient.apiService.uploadNotification(
                                                com.simats.com.network.request.NotificationUploadRequest(
                                                    username = member.username,
                                                    app_source = "AdminAlert",
                                                    sender = "Admin",
                                                    message_content = "Notification permission enabled for User Dashboard",
                                                    timestamp = System.currentTimeMillis().toString()
                                                )
                                            )
                                        } catch(e: Exception) {}
                                    }
                                }, contentPadding = PaddingValues(0.dp)) { Text("Permissions", fontSize = 10.sp) }

                                Row(horizontalArrangement = Arrangement.End) {
                                    TextButton(onClick = { 
                                        selectedMember = member
                                        showViewDialog = true 
                                    }, contentPadding = PaddingValues(0.dp)) { Text("View", fontSize = 10.sp) }

                                    TextButton(onClick = { 
                                        scope.launch {
                                            try {
                                                val res = ApiClient.apiService.deleteDevice(com.simats.com.network.request.DeleteDeviceRequest("admin", member.id))
                                                if(res.isSuccessful) {
                                                    val listRes = ApiClient.apiService.getFamilyMembers(com.simats.com.network.request.GetFamilyMembersRequest("admin"))
                                                    if(listRes.isSuccessful) {
                                                        members = listRes.body()?.members ?: emptyList()
                                                    }
                                                }
                                            } catch(e: Exception) {}
                                        }
                                    }, contentPadding = PaddingValues(0.dp)) { Text("Delete", color = Color.Red, fontSize = 10.sp) }
                                }
                            }
                        }
                    }
                }
        }
    }
    }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Invite Member") },
            text = { 
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("To monitor a new device, share this unique connection ID with them to enter in their app:")
                    Spacer(modifier = Modifier.height(16.dp))
                    if (generatedId.isEmpty()) {
                        Button(onClick = { 
                            scope.launch {
                                try {
                                    val res = ApiClient.apiService.generateId(GetNotificationsRequest("admin"))
                                    if (res.isSuccessful) {
                                        generatedId = res.body()?.unique_id ?: "ERROR"
                                    }
                                } catch (e: Exception) {
                                    generatedId = "NET_ERR"
                                }
                            }
                        }) {
                            Text("Generate ID")
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFE5E7EB), RoundedCornerShape(8.dp)).padding(16.dp), contentAlignment = Alignment.Center) {
                            Text(generatedId, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Waiting for user to connect...", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { 
                    showAddDialog = false 
                    scope.launch {
                        try {
                            val res = ApiClient.apiService.getFamilyMembers(com.simats.com.network.request.GetFamilyMembersRequest("admin"))
                            if(res.isSuccessful) {
                                members = res.body()?.members ?: emptyList()
                            }
                        } catch(e: Exception) {}
                    }
                }) { Text("Done") }
            }
        )
    }

    if (showViewDialog && selectedMember != null) {
        val sm = selectedMember!!
        AlertDialog(
            onDismissRequest = { showViewDialog = false },
            title = { Text("User Details") },
            text = { 
                Column {
                    Text("Name: ${sm.username}", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("Phone: ${sm.phone?.takeIf { it.isNotBlank() } ?: "N/A"}")
                    Text("Role: Family Member")
                    Text("Connection ID: ${sm.id}")
                    Text("Status: ${sm.status}", color = if(sm.status == "Connected") Color(0xFF4CAF50) else Color.Gray)
                    if (!sm.device.isNullOrEmpty()) {
                        Text("Device: ${sm.device}", color = Color.Gray)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showViewDialog = false }) { Text("Close") }
            }
        )
    }
}
