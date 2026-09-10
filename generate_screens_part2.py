import os
target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"
os.makedirs(target_dir, exist_ok=True)

files = {}

files["FamilyMembersScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FamilyMembersScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Family Members", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            item { FamilyMemberCard("Sister", "OnePlus Nord 5", "Connected", true) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { FamilyMemberCard("Parent", "Android Device", "Connected", false) }
        }
        
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("+ Add Family Member", color = Color.White)
        }
    }
}

@Composable
fun FamilyMemberCard(name: String, device: String, status: String, fullFeatures: Boolean) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.LightGray))
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
                    Text("Location Sharing", style = MaterialTheme.typography.bodySmall)
                    Text("ON", style = MaterialTheme.typography.bodySmall, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Call Alerts", style = MaterialTheme.typography.bodySmall)
                    Text("ON", style = MaterialTheme.typography.bodySmall, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
"""

files["CallAlertsScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun CallAlertsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Call Alerts", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Call alert sharing: ON", style = MaterialTheme.typography.labelSmall, color = Color(0xFF4CAF50))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            FilterChip(selected = true, onClick = {}, label = { Text("All") })
            FilterChip(selected = false, onClick = {}, label = { Text("Incoming") })
            FilterChip(selected = false, onClick = {}, label = { Text("Missed") })
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            item { CallCard("+91 XXXXX XXXXX", "Incoming Call", "Today • 10:42 PM", "Sister • OnePlus Nord 5", Color.Blue) }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item { CallCard("+91 XXXXX XXXXX", "Missed Call", "Today • 10:18 PM", "Sister • OnePlus Nord 5", Color.Red) }
        }
    }
}

@Composable
fun CallCard(number: String, type: String, time: String, device: String, iconTint: Color) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Phone, contentDescription = null, tint = iconTint, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(number, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(type, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text(device, style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
            }
            Text(time, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}
"""

files["RealTimeCallNotificationScreen.kt"] = """package com.simats.goodlook.screens

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
        Text("New Call Alert", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
        Spacer(modifier = Modifier.height(48.dp))
        
        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color(0xFF1976D2))
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
            Button(onClick = { }, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
                Text("View Details", color = Color.White)
            }
        }
    }
}
"""

files["CallDetailsScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CallDetailsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Call Details", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
            Column(modifier = Modifier.padding(20.dp)) {
                DetailRow("Phone Number", "+91 XXXXX XXXXX")
                DetailRow("Call Type", "Incoming")
                DetailRow("Date", "29 August 2026")
                DetailRow("Time", "10:42 PM")
                DetailRow("Status", "Answered")
                DetailRow("Duration", "2 min 14 sec")
                DetailRow("Device", "OnePlus Nord 5")
                DetailRow("Family Member", "Sister")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Privacy note: This information is shared according to the selected safety preferences.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Back to Alerts", color = Color.White)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
"""

files["LocationScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LocationScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp)) {
            Text("Family Location", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
        
        Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color(0xFFE5E5E5)), contentAlignment = Alignment.Center) {
            Text("Map View Placeholder\nMarker: Sister • OnePlus Nord 5\nStatus: Online • Just now", style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }
        
        Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Location Sharing", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("ON", style = MaterialTheme.typography.titleMedium, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("Refresh") }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))) { Text("Settings") }
                }
            }
        }
    }
}
"""

files["EmergencySOSScreen.kt"] = """package com.simats.goodlook.screens

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
        Text("Press and hold for 3 seconds to send an emergency alert.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = Color.DarkGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Emergency Contacts:", fontWeight = FontWeight.Bold, color = Color.Red)
                Text("- Sister\n- Parent\n- Trusted Contact", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Location will be shared with selected emergency contacts.", style = MaterialTheme.typography.bodySmall, color = Color.Red)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("Manage Emergency Contacts", color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray), shape = RoundedCornerShape(25.dp)) {
            Text("Cancel", color = Color.White)
        }
    }
}
"""

for fn, content in files.items():
    with open(os.path.join(target_dir, fn), "w", encoding="utf-8") as f:
        f.write(content)

print(f"Generated {len(files)} files successfully.")
