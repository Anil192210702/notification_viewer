import os
target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"
os.makedirs(target_dir, exist_ok=True)

files = {}

files["AdminCallMonitoringScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminCallMonitoringScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Text("Call Alerts", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Search phone number") }, modifier = Modifier.weight(1f))
            Button(onClick = {}) { Text("Filters") }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Total Calls: 156", fontWeight = FontWeight.Bold)
            Text("Incoming: 89")
            Text("Missed: 12")
            Text("Answered: 55")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("TIME", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("PHONE", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                    Text("TYPE", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("FAMILY MEMBER", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f))
                    Text("STATUS", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("10:42 PM", modifier = Modifier.weight(1f))
                    Text("+91 XXXXX XXXXX", modifier = Modifier.weight(2f))
                    Text("Incoming", modifier = Modifier.weight(1f))
                    Text("Sister", modifier = Modifier.weight(1.5f))
                    Text("New", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
"""

files["AdminFamilyMembersScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminFamilyMembersScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Family Members", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Button(onClick = {}) { Text("+ Add Family Member") }
        }
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Device", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Connection", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Actions", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Sister\nFamily Member", modifier = Modifier.weight(1f))
                    Text("OnePlus Nord 5", modifier = Modifier.weight(1f))
                    Text("Connected", color = Color(0xFF4CAF50), modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.weight(1f)) {
                        TextButton(onClick = {}) { Text("View") }
                        TextButton(onClick = {}) { Text("Manage") }
                    }
                }
            }
        }
    }
}
"""

files["AdminDeviceDetailsScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminDeviceDetailsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
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
            Button(onClick = {}) { Text("Manage Permissions") }
            OutlinedButton(onClick = {}) { Text("Disconnect Device", color = Color.Red) }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Privacy notice: Only explicitly authorized safety data is displayed.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}
"""

files["AdminNotificationsScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminNotificationsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Notifications", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Row {
                TextButton(onClick = {}) { Text("Mark all read") }
                TextButton(onClick = {}) { Text("Clear", color = Color.Red) }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = true, onClick = {}, label = { Text("All") })
            FilterChip(selected = false, onClick = {}, label = { Text("Calls") })
            FilterChip(selected = false, onClick = {}, label = { Text("Emergency") })
        }
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("NEW", style = MaterialTheme.typography.labelSmall, color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
                Text("Incoming call: +91 XXXXX XXXXX", fontWeight = FontWeight.Bold)
                Text("Sister • OnePlus Nord 5 • 10:42 PM", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("EMERGENCY", style = MaterialTheme.typography.labelSmall, color = Color.Red, fontWeight = FontWeight.Bold)
                Text("Emergency alert triggered", fontWeight = FontWeight.Bold)
                Text("Sister • Yesterday", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}
"""

files["AdminPreferencesScreen.kt"] = """package com.simats.goodlook.screens

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
                    Text("Incoming Calls\nAuthorized by: Sister")
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
                    Text("Real-time Tracking\nAuthorized by: Sister")
                    Switch(checked = true, onCheckedChange = {})
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Important: Changes require the family member's authorization where applicable.", style = MaterialTheme.typography.bodyMedium, color = Color.Red)
    }
}
"""

files["AdminPrivacySecurityScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminPrivacySecurityScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Text("Privacy & Security", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
            Text("SafeConnect is designed around transparency and user-controlled data sharing.", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF1976D2), modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("DATA SHARING", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = {}) { Text("Active family connections") }
                    TextButton(onClick = {}) { Text("Shared data categories") }
                }
            }
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("SECURITY", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = {}) { Text("Change password") }
                    TextButton(onClick = {}) { Text("Two-factor authentication") }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("DATA MANAGEMENT", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(onClick = {}) { Text("Export authorized data") }
                    OutlinedButton(onClick = {}) { Text("Delete call history", color = Color.Red) }
                }
            }
        }
    }
}
"""

for fn, content in files.items():
    with open(os.path.join(target_dir, fn), "w", encoding="utf-8") as f:
        f.write(content)

print(f"Generated {len(files)} files successfully.")
