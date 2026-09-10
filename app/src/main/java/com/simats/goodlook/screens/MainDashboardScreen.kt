package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(
    onFamilyClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var callsEnabled by remember(context) { mutableStateOf(com.simats.goodlook.SessionManager.getToggleState(context, "callsEnabled")) }
    var notificationsEnabled by remember(context) { mutableStateOf(com.simats.goodlook.SessionManager.getToggleState(context, "notificationsEnabled", true)) }
    var whatsappEnabled by remember(context) { mutableStateOf(com.simats.goodlook.SessionManager.getToggleState(context, "whatsappEnabled")) }
    var instagramEnabled by remember(context) { mutableStateOf(com.simats.goodlook.SessionManager.getToggleState(context, "instagramEnabled")) }
    
    var showConnectDialog by remember { mutableStateOf(false) }
    var connectionId by remember { mutableStateOf("") }
    var adminName by remember { mutableStateOf<String?>(null) }
    var bondName by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun uploadSettingsChange(settingName: String, enabled: Boolean) {
        scope.launch {
            try {
                val req = com.simats.com.network.request.NotificationUploadRequest(
                    username = com.simats.goodlook.SessionManager.loggedInEmail,
                    app_source = "Settings",
                    sender = "System",
                    message_content = "$settingName ${if (enabled) "ENABLED" else "DISABLED"}",
                    timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
                )
                com.simats.com.network.response.ApiClient.apiService.uploadNotification(req)
            } catch (e: Exception) {}
        }
    }

    val callPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        callsEnabled = granted
        com.simats.goodlook.SessionManager.saveToggleState(context, "callsEnabled", granted)
        
        scope.launch {
            try {
                val req = com.simats.com.network.request.NotificationUploadRequest(
                    username = com.simats.goodlook.SessionManager.loggedInEmail,
                    app_source = "Settings",
                    sender = "System",
                    message_content = if (granted) "Call Tracking ENABLED" else "Call Tracking Permission Denied",
                    timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
                )
                com.simats.com.network.response.ApiClient.apiService.uploadNotification(req)
            } catch (e: Exception) {}
        }
    }

    var userAlerts by remember { mutableStateOf<List<com.simats.com.network.response.NotificationItem>>(emptyList()) }
    var showAlertsDialog by remember { mutableStateOf(false) }
    
    var profileName by remember { mutableStateOf(com.simats.goodlook.SessionManager.loggedInUser) }
    var profileImageBase64 by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val req = com.simats.com.network.request.GetAdminRequest(com.simats.goodlook.SessionManager.loggedInEmail)
            val res = com.simats.com.network.response.ApiClient.apiService.getPairedAdmin(req)
            if (res.isSuccessful) {
                adminName = res.body()?.adminUsername
                bondName = res.body()?.bond
            }
            
            // Sync Native Android Hardware constraints straight to Server tracking dynamically
            val bm = context.getSystemService(android.content.Context.BATTERY_SERVICE) as android.os.BatteryManager
            val batteryLevel = bm.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
            com.simats.com.network.response.ApiClient.apiService.updateDeviceMeta(
                com.simats.com.network.request.UpdateDeviceMetaRequest(
                    com.simats.goodlook.SessionManager.loggedInEmail,
                    android.os.Build.MODEL,
                    batteryLevel
                )
            )

            // Resolve explicit inbound network Alerts
            val alertRes = com.simats.com.network.response.ApiClient.apiService.getUserAlerts(com.simats.com.network.request.GetNotificationsRequest(com.simats.goodlook.SessionManager.loggedInEmail))
            if(alertRes.isSuccessful) {
                userAlerts = alertRes.body()?.notifications ?: emptyList()
            }
            
            val profRes = com.simats.com.network.response.ApiClient.apiService.getProfile(com.simats.com.network.request.GetProfileRequest(com.simats.goodlook.SessionManager.loggedInEmail))
            if(profRes.isSuccessful) {
                val body = profRes.body()
                if (!body?.full_name.isNullOrEmpty()) {
                    profileName = body!!.full_name
                }
                profileImageBase64 = body?.profile_image_base64 ?: ""
            }
        } catch (e: Exception) {}
    }

    Scaffold(
        bottomBar = {
            val navColors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0D61D2),
                selectedTextColor = Color(0xFF0D61D2),
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black,
                indicatorColor = Color(0xFFEFF6FF)
            )
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, contentDescription = "Home") }, label = { Text("Home") }, colors = navColors)
                NavigationBarItem(selected = false, onClick = onFamilyClick, icon = { Icon(Icons.Default.Person, contentDescription = "Family") }, label = { Text("Family") }, colors = navColors)
                NavigationBarItem(selected = false, onClick = onSettingsClick, icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }, label = { Text("Settings") }, colors = navColors)
            }
        },
        containerColor = Color(0xFFF9FAFB) // surface-soft
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onSettingsClick() }) {
                    Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFE5E7EB)), contentAlignment = Alignment.Center) {
                        val bitmap = try {
                            if (profileImageBase64.isNotEmpty()) {
                                val decoded = android.util.Base64.decode(profileImageBase64, android.util.Base64.DEFAULT)
                                android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.size)?.asImageBitmap()
                            } else null
                        } catch(e: Exception) { null }
                        
                        if (bitmap != null) {
                            androidx.compose.foundation.Image(bitmap = bitmap, contentDescription = "Profile", contentScale = androidx.compose.ui.layout.ContentScale.Crop, modifier = Modifier.fillMaxSize())
                        } else {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Good Evening,", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        Text(profileName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
                IconButton(onClick = { showAlertsDialog = true }) {
                    BadgedBox(badge = { if(userAlerts.isNotEmpty()) { Badge(containerColor = Color.Red) } }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Who you are sharing with
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sharing Data With", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                    TextButton(onClick = { showConnectDialog = true }) {
                        Text("+ Add Family Member")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFE5E7EB)))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            if (adminName != null) {
                                Text(adminName!!, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(bondName ?: "Admin", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            } else {
                                Text("No Connections", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("Tap + Add Family Member to pair", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                        if (adminName != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF10B981)))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Connected", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // What details to share
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text("What details to share", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(12.dp))

                SharedDetailItem(
                    title = "Incoming / Outgoing Calls",
                    description = "Monitor numbers and call durations",
                    icon = Icons.Default.Phone,
                    isChecked = callsEnabled,
                    onCheckedChange = { 
                        if (it) {
                            callPermissionLauncher.launch(
                                arrayOf(
                                    android.Manifest.permission.READ_PHONE_STATE,
                                    android.Manifest.permission.READ_CALL_LOG,
                                    android.Manifest.permission.READ_CONTACTS
                                )
                            )
                        } else {
                            callsEnabled = false
                            com.simats.goodlook.SessionManager.saveToggleState(context, "callsEnabled", false)
                            uploadSettingsChange("Call Tracking", false)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                SharedDetailItem(
                    title = "Notifications",
                    description = "Share alert notifications",
                    icon = Icons.Default.Notifications,
                    isChecked = notificationsEnabled,
                    onCheckedChange = { 
                        if (it && !com.simats.goodlook.SessionManager.isNotificationListenerEnabled(context)) {
                            context.startActivity(android.content.Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                        }
                        notificationsEnabled = it 
                        com.simats.goodlook.SessionManager.saveToggleState(context, "notificationsEnabled", it)
                        uploadSettingsChange("App Notifications", it)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                SharedDetailItem(
                    title = "WhatsApp",
                    description = "Share activity status on WhatsApp",
                    icon = Icons.Default.Share,
                    isChecked = whatsappEnabled,
                    onCheckedChange = { 
                        if (it && !com.simats.goodlook.SessionManager.isNotificationListenerEnabled(context)) {
                            context.startActivity(android.content.Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                        }
                        whatsappEnabled = it 
                        com.simats.goodlook.SessionManager.saveToggleState(context, "whatsappEnabled", it)
                        uploadSettingsChange("WhatsApp Tracking", it)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                SharedDetailItem(
                    title = "Instagram",
                    description = "Share activity status on Instagram",
                    icon = Icons.Default.Share,
                    isChecked = instagramEnabled,
                    onCheckedChange = { 
                        if (it && !com.simats.goodlook.SessionManager.isNotificationListenerEnabled(context)) {
                            context.startActivity(android.content.Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                        }
                        instagramEnabled = it 
                        com.simats.goodlook.SessionManager.saveToggleState(context, "instagramEnabled", it)
                        uploadSettingsChange("Instagram Tracking", it)
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
    if (showConnectDialog) {
        AlertDialog(
            onDismissRequest = { showConnectDialog = false },
            title = { Text("Connect to Admin") },
            text = { 
                Column {
                    Text("Enter the unique ID provided by the family admin:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = connectionId, 
                        onValueChange = { connectionId = it },
                        placeholder = { Text("e.g. X9K-3F2") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = { 
                    scope.launch {
                        try {
                            val req = com.simats.com.network.request.PairDeviceRequest(com.simats.goodlook.SessionManager.loggedInEmail, connectionId)
                            val res = com.simats.com.network.response.ApiClient.apiService.pairDevice(req)
                            if (res.isSuccessful) {
                                android.widget.Toast.makeText(context, "Successfully paired!", android.widget.Toast.LENGTH_SHORT).show()
                                showConnectDialog = false
                                // refresh visual tree
                                val adminReq = com.simats.com.network.request.GetAdminRequest(com.simats.goodlook.SessionManager.loggedInEmail)
                                val adminRes = com.simats.com.network.response.ApiClient.apiService.getPairedAdmin(adminReq)
                                if (adminRes.isSuccessful) {
                                    adminName = adminRes.body()?.adminUsername
                                    bondName = adminRes.body()?.bond
                                }
                            } else {
                                android.widget.Toast.makeText(context, "Invalid Code!", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        } catch(e: Exception) {
                            android.widget.Toast.makeText(context, "Network Error", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }) { Text("Connect") }
            },
            dismissButton = {
                TextButton(onClick = { showConnectDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showAlertsDialog) {
        AlertDialog(
            onDismissRequest = { showAlertsDialog = false },
            title = { Text("Admin Alerts") },
            text = {
                Column {
                    if (userAlerts.isEmpty()) {
                        Text("No recent alerts from your Admin.")
                    } else {
                        userAlerts.forEach { alert ->
                            Text("- ${alert.message_content}", color = Color.Gray, modifier = Modifier.padding(bottom=4.dp))
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showAlertsDialog=false }) { Text("Close") } }
        )
    }
}

@Composable
fun SharedDetailItem(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (isChecked) Color(0xFFEFF6FF) else Color(0xFFF3F4F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = if (isChecked) Color(0xFF2563EB) else Color(0xFF9CA3AF),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text(description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF2563EB))
            )
        }
    }
}
