package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminPreferencesScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Text("Safety Preferences", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Phone Call Alerts", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Incoming Calls\\nAuthorized by: Sister")
                    Switch(checked = true, onCheckedChange = {})
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Location Sharing", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Real-time Tracking\\nAuthorized by: Sister")
                    Switch(checked = true, onCheckedChange = {})
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Important: Changes require the family member's authorization where applicable.", style = MaterialTheme.typography.bodyMedium, color = Color.Red)
    }
}
