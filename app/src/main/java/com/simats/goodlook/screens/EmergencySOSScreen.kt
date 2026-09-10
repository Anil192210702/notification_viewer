package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmergencySOSScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Emergency Help", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.Red)
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = {},
            modifier = Modifier.size(200.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("SOS", style = MaterialTheme.typography.displayLarge, color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Press and hold for 3 seconds to send an emergency alert.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = Color.Black)
        
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Emergency Contacts:", fontWeight = FontWeight.Bold, color = Color.Red)
                Text("- Sister\\n- Parent\\n- Trusted Contact", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Location will be shared with selected emergency contacts.", style = MaterialTheme.typography.bodySmall, color = Color.Red)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("Manage Emergency Contacts", color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Black), shape = RoundedCornerShape(25.dp)) {
            Text("Cancel", color = Color.White)
        }
    }
}
