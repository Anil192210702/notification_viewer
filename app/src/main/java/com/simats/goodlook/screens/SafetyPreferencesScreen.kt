package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SafetyPreferencesScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Safety Preferences", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Choose which safety features you want to enable.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        
        PreferenceCard("PHONE CALLS", "Share permitted incoming and missed-call alerts.", Icons.Default.Call)
        PreferenceCard("LOCATION", "Share your current location with your trusted family member.", Icons.Default.LocationOn)
        PreferenceCard("EMERGENCY SOS", "Send an alert to your emergency contacts during an SOS event.", Icons.Default.Warning)
        PreferenceCard("WHATSAPP SAFETY", "Enable supported safety notifications and check-in features.", Icons.Default.Email)
        PreferenceCard("INSTAGRAM SAFETY", "Enable supported safety notifications and check-in features.", Icons.Default.Email)
        PreferenceCard("DEVICE STATUS", "Share basic device connection status.", Icons.Default.Build)
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Save Preferences", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("You can change these preferences at any time.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PreferenceCard(title: String, desc: String, icon: ImageVector) {
    var checked by remember { mutableStateOf(true) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color(0xFF0D61D2))
                Spacer(modifier = Modifier.width(16.dp))
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF0D61D2)))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Learn More", style = MaterialTheme.typography.labelMedium, color = Color(0xFF0D61D2), fontWeight = FontWeight.Bold)
        }
    }
}
