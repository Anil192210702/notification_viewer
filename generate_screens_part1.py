import os

target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"
os.makedirs(target_dir, exist_ok=True)

files = {}

files["SplashScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Lock, contentDescription = "Logo", modifier = Modifier.size(80.dp), tint = Color(0xFF1976D2))
        Spacer(modifier = Modifier.height(16.dp))
        Text("SafeConnect", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Stay Connected. Stay Safe.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Spacer(modifier = Modifier.height(48.dp))
        CircularProgressIndicator(color = Color(0xFF1976D2))
    }
}
"""

files["WelcomeScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Person, contentDescription = "Family", modifier = Modifier.size(120.dp), tint = Color(0xFF1976D2))
        Spacer(modifier = Modifier.height(32.dp))
        Text("Stay Connected. Stay Safe.", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Keep trusted family members connected through safety alerts, location sharing and emergency features.", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Get Started", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("I Already Have an Account", color = Color(0xFF1976D2))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text("You control what information you share.", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}
"""

files["CreateAccountScreen.kt"] = """package com.simats.goodlook.screens

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
fun CreateAccountScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Create Your Account", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = agreed, onCheckedChange = { agreed = it })
            Text("I agree to the Privacy Policy and Terms.", style = MaterialTheme.typography.bodySmall)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Create Account", color = Color.White)
        }
        TextButton(onClick = { }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Already have an account? Sign In", color = Color(0xFF1976D2))
        }
    }
}
"""

files["LoginScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Lock, contentDescription = "Logo", modifier = Modifier.size(64.dp), tint = Color(0xFF1976D2))
        Spacer(modifier = Modifier.height(24.dp))
        Text("Welcome Back", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { }) {
                Text("Forgot Password?", color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Sign In", color = Color.White)
        }
        TextButton(onClick = { }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Create Account", color = Color(0xFF1976D2))
        }
    }
}
"""

files["FamilySetupScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FamilySetupScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Who do you want to stay connected with?", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        
        val options = listOf("Parent", "Sister/Brother", "Spouse", "Child", "Other Trusted Person")
        options.forEach { option ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Add $option", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF1976D2))
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Add Family Member", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Family members must accept your invitation before safety information can be shared.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, textAlign = TextAlign.Center)
    }
}
"""

files["InviteFamilyMemberScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InviteFamilyMemberScreen() {
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("Select Relationship") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Invite Family Member", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Email or Phone Number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
            Text(relationship, color = Color.Black)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), shape = RoundedCornerShape(12.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = Color(0xFF1976D2))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Your family member will receive an invitation and can choose whether to accept.", style = MaterialTheme.typography.bodySmall, color = Color(0xFF1976D2))
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Send Invitation", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Privacy and consent are required before sharing safety information.", style = MaterialTheme.typography.labelSmall, color = Color.Gray, textAlign = TextAlign.Center)
    }
}
"""

files["SafetyPreferencesScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Devices
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
        PreferenceCard("WHATSAPP SAFETY", "Enable supported safety notifications and check-in features.", Icons.Default.Chat)
        PreferenceCard("INSTAGRAM SAFETY", "Enable supported safety notifications and check-in features.", Icons.Default.Chat)
        PreferenceCard("DEVICE STATUS", "Share basic device connection status.", Icons.Default.Devices)
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
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
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color(0xFF1976D2))
                Spacer(modifier = Modifier.width(16.dp))
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF1976D2)))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Learn More", style = MaterialTheme.typography.labelMedium, color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
        }
    }
}
"""

files["PermissionsScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionsScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Review Permissions", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("SafeConnect needs your permission to provide the safety features you selected.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        
        PermCard("Phone", "Required for supported call safety alerts.", Icons.Default.Call)
        PermCard("Location", "Required for family location sharing.", Icons.Default.LocationOn)
        PermCard("Notifications", "Required to deliver safety alerts.", Icons.Default.Notifications)
        PermCard("Emergency/SOS", "Required for emergency assistance features.", Icons.Default.Warning)
        
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Continue", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Permissions can be changed from your device settings.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PermCard(title: String, desc: String, icon: ImageVector) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color(0xFF1976D2))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Status: Allowed", style = MaterialTheme.typography.labelSmall, color = Color(0xFF4CAF50))
                }
                TextButton(onClick = {}) { Text("Review") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
        }
    }
}
"""

files["PermissionSuccessScreen.kt"] = """package com.simats.goodlook.screens

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
fun PermissionSuccessScreen() {
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
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Go to Dashboard", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("Review Preferences", color = Color(0xFF1976D2))
        }
    }
}
"""

files["MainDashboardScreen.kt"] = """package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainDashboardScreen() {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Text("Home") }, label = { Text("Home") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Text("Family") }, label = { Text("Family") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Text("Alerts") }, label = { Text("Alerts") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Text("Settings") }, label = { Text("Settings") })
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.LightGray))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Good Evening, John", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color(0xFF1976D2))
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1976D2))) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Family Safety", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Status: Connected", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Sister", style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("OnePlus Nord 5 • Connected", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Safety Status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusCard("Location", "Sharing", Modifier.weight(1f))
                StatusCard("Call Alerts", "Enabled", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusCard("Emergency SOS", "Ready", Modifier.weight(1f))
                StatusCard("Device", "Online", Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Recent Safety Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            ActivityItem("Incoming call alert", "Today, 10:42 PM")
            ActivityItem("Location updated", "Today, 10:35 PM")
            ActivityItem("Device connected", "Today, 10:30 PM")
        }
    }
}

@Composable
fun StatusCard(title: String, subtitle: String, modifier: Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun ActivityItem(title: String, time: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF1976D2)))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}
"""

for fn, content in files.items():
    with open(os.path.join(target_dir, fn), "w", encoding="utf-8") as f:
        f.write(content)

print(f"Generated {len(files)} files successfully.")
