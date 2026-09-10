package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun FamilyMembersScreen() {
    var showConnectDialog by remember { mutableStateOf(false) }
    var connectionId by remember { mutableStateOf("") }
    var adminName by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            val req = com.simats.com.network.request.GetAdminRequest(com.simats.goodlook.SessionManager.loggedInEmail)
            val res = com.simats.com.network.response.ApiClient.apiService.getPairedAdmin(req)
            if (res.isSuccessful) {
                adminName = res.body()?.adminUsername
            }
        } catch (e: Exception) {}
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Family Members", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            if (adminName != null) {
                item { FamilyMemberCard(adminName!!, "Admin Account", "Connected", true) }
            } else {
                item { Text("No paired devices found locally.", color = Color.Gray) }
            }
        }
        
        Button(
            onClick = { showConnectDialog = true },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("+ Add Family Member", color = Color.White)
        }
    }

    if (showConnectDialog) {
        AlertDialog(
            onDismissRequest = { showConnectDialog = false },
            title = { Text("Connect to Admin") },
            text = { 
                Column {
                    Text("Enter the unique ID provided by the family admin:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = connectionId, 
                        onValueChange = { connectionId = it },
                        placeholder = { Text("e.g. X9K-3F2") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = { 
                    scope.launch {
                        try {
                            val req = com.simats.com.network.request.PairDeviceRequest(
                                com.simats.goodlook.SessionManager.loggedInEmail, 
                                connectionId
                            )
                            val res = com.simats.com.network.response.ApiClient.apiService.pairDevice(req)
                            if (res.isSuccessful) {
                                Toast.makeText(context, "Successfully paired!", Toast.LENGTH_SHORT).show()
                                showConnectDialog = false
                                val adminReq = com.simats.com.network.request.GetAdminRequest(com.simats.goodlook.SessionManager.loggedInEmail)
                                val adminRes = com.simats.com.network.response.ApiClient.apiService.getPairedAdmin(adminReq)
                                if (adminRes.isSuccessful) {
                                    adminName = adminRes.body()?.adminUsername
                                }
                            } else {
                                Toast.makeText(context, "Invalid Code!", Toast.LENGTH_SHORT).show()
                            }
                        } catch(e: Exception) {
                            Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) { Text("Connect") }
            },
            dismissButton = {
                TextButton(onClick = { showConnectDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun FamilyMemberCard(name: String, device: String, status: String, fullFeatures: Boolean) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Color(0xFFE5E7EB)))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("$device • $status", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                TextButton(onClick = {}) { Text("View") }
            }
            if (fullFeatures) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tracking Data", style = MaterialTheme.typography.bodySmall)
                    Text("ACTIVE", style = MaterialTheme.typography.bodySmall, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
