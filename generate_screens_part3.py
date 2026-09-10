import os
target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"
os.makedirs(target_dir, exist_ok=True)

files = {}

files["WhatsAppSafetyScreen.kt"] = """package com.simats.goodlook.screens

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
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
            Text("Privacy information: Private WhatsApp messages and conversations are not displayed.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(16.dp))
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
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
                Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            }
            Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF25D366)))
        }
    }
}
"""

files["InstagramSafetyScreen.kt"] = """package com.simats.goodlook.screens

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
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Save Preferences", color = Color.White)
        }
    }
}

@Composable
fun InstaCardToggle(title: String, desc: String) {
    var checked by remember { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            }
            Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE1306C)))
        }
    }
}
"""

files["NotificationsCenterScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NotificationsCenterScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Notifications", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            TextButton(onClick = {}) { Text("Mark all as read") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn {
            item { NotificationCard("Emergency", "New incoming call\n+91 XXXXX XXXXX • 10:42 PM", Color.Red) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item { NotificationCard("Location", "Location updated\nSister • 10:35 PM", Color(0xFF4CAF50)) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item { NotificationCard("Device", "Device connected\nOnePlus Nord 5 • 10:30 PM", Color(0xFF1976D2)) }
        }
    }
}

@Composable
fun NotificationCard(category: String, message: String, tagColor: Color) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(category, style = MaterialTheme.typography.labelSmall, color = tagColor, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
"""

files["SettingsPrivacyScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsPrivacyScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp).verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Settings & Privacy", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        SettingsSection("ACCOUNT", listOf("Profile", "Email", "Change Password"))
        SettingsSection("FAMILY", listOf("Family Members", "Connected Devices"))
        SettingsSection("SAFETY", listOf("Safety Preferences", "Emergency Contacts", "Call Alerts", "Location Sharing"))
        SettingsSection("PRIVACY", listOf("Permissions", "Data Sharing", "Privacy Policy", "Delete Call History", "Delete Account"))
        SettingsSection("SECURITY", listOf("Change Password", "Sign Out"))
        
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
            Text("You control what information SafeConnect shares.", style = MaterialTheme.typography.bodySmall, color = Color(0xFF1976D2), modifier = Modifier.padding(16.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SettingsSection(title: String, items: List<String>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Color.Gray)
        Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
            Column {
                items.forEach { item ->
                    TextButton(onClick = {}, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                            Text(item, color = Color.Black)
                        }
                    }
                    if (item != items.last()) {
                        Divider(color = Color.LightGray)
                    }
                }
            }
        }
    }
}
"""

files["AdminLoginScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminLoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF3F4F6)), contentAlignment = Alignment.Center) {
        Card(modifier = Modifier.width(400.dp).padding(24.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(8.dp)) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("SafeConnect Admin", style = MaterialTheme.typography.titleLarge, color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Admin Login", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
                
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                        Text("Remember Me", style = MaterialTheme.typography.bodySmall)
                    }
                    TextButton(onClick = {}) { Text("Forgot Password?", style = MaterialTheme.typography.bodySmall) }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))) {
                    Text("Sign In", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Authorized family safety dashboard", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}
"""

files["AdminDashboardScreen.kt"] = """package com.simats.goodlook.screens

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
fun AdminDashboardScreen() {
    Row(modifier = Modifier.fillMaxSize().background(Color(0xFFF9FAFB))) {
        // Sidebar
        Column(modifier = Modifier.width(250.dp).fillMaxHeight().background(Color.White).padding(16.dp)) {
            Text("SafeConnect Admin", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
            Spacer(modifier = Modifier.height(32.dp))
            val menu = listOf("Dashboard", "Family Members", "Devices", "Call Alerts", "Location", "Emergency Alerts", "Notifications", "Preferences", "Privacy", "Settings")
            menu.forEach { item ->
                TextButton(onClick = {}, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) { Text(item, color = if(item == "Dashboard") Color(0xFF1976D2) else Color.DarkGray) }
                }
            }
        }
        
        // Main Content
        Column(modifier = Modifier.weight(1f).padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Dashboard", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Row {
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Search") }, modifier = Modifier.height(48.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(onClick = {}) { Text("Admin Profile") }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminStatCard("Connected Devices", "12", Modifier.weight(1f))
                AdminStatCard("Calls Today", "34", Modifier.weight(1f))
                AdminStatCard("Missed Calls", "5", Modifier.weight(1f))
                AdminStatCard("Emergency Alerts", "0", Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Recent Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AdminActivityRow("10:42 PM", "Incoming call", "+91 XXXXX XXXXX", "Sister", "OnePlus Nord 5")
                    Divider()
                    AdminActivityRow("10:35 PM", "Location updated", "", "Sister", "OnePlus Nord 5")
                    Divider()
                    AdminActivityRow("10:18 PM", "Missed call", "+91 XXXXX XXXXX", "", "")
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(title: String, value: String, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
        }
    }
}

@Composable
fun AdminActivityRow(time: String, action: String, detail: String, person: String, device: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.weight(1f)) {
            Text(time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(action, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
        Text(detail, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Text(person, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Text(device, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}
"""

for fn, content in files.items():
    with open(os.path.join(target_dir, fn), "w", encoding="utf-8") as f:
        f.write(content)

print(f"Generated {len(files)} files successfully.")
