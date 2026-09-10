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
fun WhatsAppSafetyScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("WhatsApp Safety", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color(0xFF25D366))
        Spacer(modifier = Modifier.height(8.dp))
        Text("SafeConnect does not read private WhatsApp conversations. Choose supported safety features that you want to enable.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        
        CardToggle("Safety Notifications", "Enable supported safety-related notifications.")
        CardToggle("Check-In", "Send a manual safety check-in to your trusted family member.")
        CardToggle("Emergency Shortcut", "Provide a quick route to emergency assistance.")
        
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Text("Privacy information: Private WhatsApp messages and conversations are not displayed.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(16.dp))
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
fun CardToggle(title: String, desc: String) {
    var checked by remember { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
            Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF25D366)))
        }
    }
}
