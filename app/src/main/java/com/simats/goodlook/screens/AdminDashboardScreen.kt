package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.simats.com.network.response.ApiClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(onNavigate: (String) -> Unit = {}) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentAdminScreen by remember { mutableStateOf("Dashboard") }
    var showAdminSettingsAlertsDialog by remember { mutableStateOf(false) }
    var settingsAlerts by remember { mutableStateOf<List<com.simats.com.network.response.NotificationItem>>(emptyList()) }
    
    LaunchedEffect(showAdminSettingsAlertsDialog) {
        if (showAdminSettingsAlertsDialog) {
            try {
                val notifs = ApiClient.apiService.getNotifications(com.simats.com.network.request.GetNotificationsRequest("admin"))
                if (notifs.isSuccessful) {
                    settingsAlerts = notifs.body()?.notifications?.filter { it.app_source == "Settings" } ?: emptyList()
                }
            } catch (e: Exception) {}
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(280.dp), drawerContainerColor = Color.White) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("SafeConnect", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Admin", style = MaterialTheme.typography.titleLarge, color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                val menu = listOf("Dashboard", "Family Members", "Devices", "Call Alerts", "Location", "Notifications", "Settings")
                menu.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item) },
                        selected = currentAdminScreen == item,
                        onClick = { 
                            currentAdminScreen = item
                            scope.launch { drawerState.close() }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFF0D61D2),
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.Black
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                        }
                    },
                    actions = {
                        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.padding(end = 16.dp)) {
                            IconButton(onClick = { showAdminSettingsAlertsDialog = true }) {
                                Icon(Icons.Default.Notifications, contentDescription = "Alerts", tint = Color.Black)
                            }
                            Box(modifier = Modifier.padding(top = 8.dp, end = 8.dp).size(8.dp).clip(CircleShape).background(Color.Red))
                        }
                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.Gray), contentAlignment = Alignment.Center) {
                           Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            containerColor = Color.White
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                when (currentAdminScreen) {
                    "Dashboard" -> AdminDashboardContent()
                    "Family Members" -> AdminFamilyMembersScreen()
                    "Devices" -> AdminDeviceDetailsScreen()
                    "Call Alerts" -> AdminCallMonitoringScreen(onNavigate = onNavigate)
                    "Location" -> LocationScreen()
                    "Notifications" -> AdminNotificationsScreen()
                    "Settings" -> SettingsPrivacyScreen(onNavigate = onNavigate)
                    else -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Screen $currentAdminScreen", color = Color.Gray) }
                }
            }
        }
    }
    
    if (showAdminSettingsAlertsDialog) {
        AlertDialog(
            onDismissRequest = { showAdminSettingsAlertsDialog = false },
            title = { Text("User Permission Alerts") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    if (settingsAlerts.isEmpty()) {
                        Text("No recent permission changes from paired devices.")
                    } else {
                        settingsAlerts.forEach { alert ->
                            Text("- ${alert.message_content}", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("  ${alert.timestamp}", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(bottom=8.dp))
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showAdminSettingsAlertsDialog = false }) { Text("Close") } }
        )
    }
}

@Composable
fun AdminDashboardContent() {
    val scope = rememberCoroutineScope()
    var members by remember { mutableStateOf<List<com.simats.com.network.response.FamilyMemberItem>>(emptyList()) }
    var notifications by remember { mutableStateOf<List<com.simats.com.network.response.NotificationItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val res = ApiClient.apiService.getFamilyMembers(com.simats.com.network.request.GetFamilyMembersRequest("admin"))
            if(res.isSuccessful) {
                members = res.body()?.members ?: emptyList()
            }
            val notifs = ApiClient.apiService.getNotifications(com.simats.com.network.request.GetNotificationsRequest("admin"))
            if(notifs.isSuccessful) {
                notifications = notifs.body()?.notifications ?: emptyList()
            }
        } catch(e: Exception) {}
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(start = 24.dp, end = 24.dp, top = 0.dp, bottom = 24.dp)) {
        // Title block matching exact screenshot styling
        Text("Overview", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Real-time SafeConnect safety summary", fontSize = 16.sp, color = Color.Gray)
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {}, 
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text("Add Family Member", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        // Stats
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Devices loaded from backend successfully
            AdminStatCardFullWidth("Connected Devices", members.size.toString(), "Currently monitoring", Icons.Default.Phone, true)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Recent Activity", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Intercepted safety events from connected devices", fontSize = 12.sp, color = Color.Gray)
            }
            TextButton(onClick = { }) { Text("View All") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (notifications.isEmpty()) {
                    Text("No recent activity.", modifier = Modifier.padding(8.dp))
                } else {
                    notifications.take(3).forEachIndexed { index, notif ->
                        AdminActivityRow(notif.timestamp.takeLast(8), notif.app_source, notif.message_content.take(30), notif.user__username, notif.sender)
                        if (index < notifications.size - 1 && index < 2) Divider(color = Color(0xFFE5E7EB))
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatCardFullWidth(label: String, value: String, hint: String, icon: androidx.compose.ui.graphics.vector.ImageVector, emphasis: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(), 
        colors = CardDefaults.cardColors(containerColor = Color.White), 
        shape = RoundedCornerShape(16.dp), 
        border = BorderStroke(1.dp, if(emphasis) Color(0xFF0D61D2) else Color(0xFFE5E7EB)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Text(label, style = MaterialTheme.typography.titleMedium, color = Color.DarkGray)
                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFF0F4FC)), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = Color(0xFF0D61D2), modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(value, fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(hint, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Composable
fun AdminStatCardSquare(label: String, value: String, hint: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, isDanger: Boolean = false) {
    Card(
        modifier = modifier, 
        colors = CardDefaults.cardColors(containerColor = Color.White), 
        shape = RoundedCornerShape(16.dp), 
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(label, style = MaterialTheme.typography.titleSmall, color = Color.DarkGray, modifier = Modifier.weight(1f))
                Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(if(isDanger) Color(0xFFFEF2F2) else Color(0xFFF3F4F6)), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = if(isDanger) Color.Red else Color.Black, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(value, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(hint, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun AdminActivityRow(time: String, action: String, detail: String, person: String, device: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(time, style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.width(68.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(action + if(detail.isNotEmpty()) " · " + detail else "", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Black)
            if (person.isNotEmpty() || device.isNotEmpty()) {
                Text("$person ${if(person.isNotEmpty() && device.isNotEmpty()) "•" else ""} $device", style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
        }
    }
}
