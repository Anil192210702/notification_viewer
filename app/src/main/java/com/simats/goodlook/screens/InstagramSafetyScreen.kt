package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InstagramSafetyScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Instagram Safety", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color(0xFFE1306C))
        Spacer(modifier = Modifier.height(8.dp))
        Text("SafeConnect does not read private Instagram messages or account passwords.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        
        InstaCardToggle("Safety Notifications", "Enable supported safety-related notifications.")
        InstaCardToggle("Check-In", "Send a manual safety check-in.")
        InstaCardToggle("Emergency Shortcut", "Quickly access emergency assistance.")
        
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2))) {
            Text("Privacy card: Your Instagram conversations remain private.", style = MaterialTheme.typography.bodySmall, color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Save Preferences", color = Color.White)
        }
    }
}

@Composable
fun InstaCardToggle(title: String, desc: String) {
    var checked by remember { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
            Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE1306C)))
        }
    }
}
