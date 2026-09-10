package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RealTimeCallNotificationScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE3F2FD)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("New Call Alert", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color(0xFF0D61D2))
        Spacer(modifier = Modifier.height(48.dp))
        
        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color(0xFF0D61D2))
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Incoming Call", style = MaterialTheme.typography.titleLarge)
        Text("+91 XXXXX XXXXX", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("10:42 PM", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Status: Currently ringing", style = MaterialTheme.typography.bodyMedium, color = Color.Red, fontWeight = FontWeight.Medium)
        
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Family Member: Sister", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                Text("Device: OnePlus Nord 5", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedButton(onClick = { }, modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(25.dp)) {
                Text("Dismiss", color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { }, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
                Text("View Details", color = Color.White)
            }
        }
    }
}
