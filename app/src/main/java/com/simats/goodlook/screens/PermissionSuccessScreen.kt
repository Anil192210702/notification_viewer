package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PermissionSuccessScreen(onDashboardClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = "Success", modifier = Modifier.size(100.dp), tint = Color(0xFF4CAF50))
        Spacer(modifier = Modifier.height(32.dp))
        Text("You're All Set", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your selected safety features are ready.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        
        Spacer(modifier = Modifier.height(32.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text("✓ Phone safety alerts", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("✓ Location sharing", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("✓ Emergency SOS", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("✓ Notifications", style = MaterialTheme.typography.bodyMedium)
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = onDashboardClick, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Go to Dashboard", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("Review Preferences", color = Color(0xFF0D61D2))
        }
    }
}
