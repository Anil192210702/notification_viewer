package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDeviceDetailsScreen() {
    var showPermissionsDialog by remember { mutableStateOf(false) }
    var showDisconnectDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Device Details (Admin)", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text("OnePlus Nord 5", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Status", fontWeight = FontWeight.Bold)
                    Text("Family Member: Sister")
                    Text("Connection: Online")
                    Text("Battery: 78%")
                }
            }
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Safety Features", fontWeight = FontWeight.Bold)
                    Text("Location Sharing: ON", color = Color(0xFF4CAF50))
                    Text("Call Alerts: ON", color = Color(0xFF4CAF50))
                    Text("WhatsApp Safety: OFF", color = Color.Gray)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { showPermissionsDialog = true }) { Text("Manage Permissions") }
            OutlinedButton(onClick = { showDisconnectDialog = true }) { Text("Disconnect Device", color = Color.Red) }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Privacy notice: Only explicitly authorized safety data is displayed.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }

    if (showPermissionsDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionsDialog = false },
            title = { Text("Permissions") },
            text = { Text("Settings successfully verified. You can now tweak individual tracking parameters.") },
            confirmButton = {
                TextButton(onClick = { showPermissionsDialog = false }) { Text("Done") }
            }
        )
    }

    if (showDisconnectDialog) {
        AlertDialog(
            onDismissRequest = { showDisconnectDialog = false },
            title = { Text("Disconnect") },
            text = { Text("Are you sure you want to disconnect this device? Tracking will halt immediately.") },
            confirmButton = {
                TextButton(onClick = { showDisconnectDialog = false }) { Text("Disconnect") }
            },
            dismissButton = {
                TextButton(onClick = { showDisconnectDialog = false }) { Text("Cancel") }
            }
        )
    }
}
