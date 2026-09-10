package com.simats.goodlook.screens

import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.simats.com.network.response.ApiClient
import com.simats.goodlook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAccountScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var profileImageBase64 by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val bytes = context.contentResolver.openInputStream(it)?.readBytes()
                val encoded = Base64.encodeToString(bytes, Base64.DEFAULT)
                profileImageBase64 = encoded
            } catch(e: Exception) {}
        }
    }

    LaunchedEffect(Unit) {
        val userEmail = com.simats.goodlook.SessionManager.loggedInEmail
        try {
            val res = ApiClient.apiService.getProfile(com.simats.com.network.request.GetProfileRequest(userEmail))
            if(res.isSuccessful) {
                val body = res.body()
                fullName = body?.full_name ?: ""
                phoneNumber = body?.phone_number ?: ""
                email = body?.email ?: userEmail
                profileImageBase64 = body?.profile_image_base64 ?: ""
            }
        } catch(e: Exception) {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp)
            .imePadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Profile & Account", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            IconButton(onClick = { 
                if (isEditing) {
                    // Save changes
                    isEditing = false
                    scope.launch {
                        try {
                            val req = com.simats.com.network.request.UpdateProfileRequest(
                                username = com.simats.goodlook.SessionManager.loggedInEmail,
                                full_name = fullName,
                                phone_number = phoneNumber,
                                profile_image_base64 = profileImageBase64
                            )
                            val res = ApiClient.apiService.updateProfile(req)
                            if (res.isSuccessful) {
                                android.widget.Toast.makeText(context, "Profile updated successfully!", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        } catch(e: Exception) {}
                    }
                } else {
                    isEditing = true 
                }
            }) {
                Icon(Icons.Default.Create, contentDescription = "Edit", tint = if (isEditing) Color(0xFF00E676) else Color(0xFF4C1D95))
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Image
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(enabled = isEditing) {
                            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val bitmap = try {
                        if (profileImageBase64.isNotEmpty()) {
                            val decoded = Base64.decode(profileImageBase64, Base64.DEFAULT)
                            android.graphics.BitmapFactory.decodeByteArray(decoded, 0, decoded.size)?.asImageBitmap()
                        } else null
                    } catch(e: Exception) { null }
                    
                    if (bitmap != null) {
                        Image(bitmap = bitmap, contentDescription = "Profile", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                    } else {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(64.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (isEditing) {
                    Text("Tap to Change Photo", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF4C1D95), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileField("FULL NAME", Icons.Default.Person, fullName, isEditing) { fullName = it }
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF3F4F6))
                
                ProfileField("EMAIL ADDRESS", Icons.Default.Email, email, false) { }
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF3F4F6))
                
                ProfileField("PHONE NUMBER", Icons.Default.Phone, phoneNumber, isEditing) { phoneNumber = it }
            }
        }
    }
}

@Composable
fun ProfileField(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, isEditing: Boolean, onValueChanged: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFF3F6FD)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF4C1D95), modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.Gray)
            if (isEditing) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChanged,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4C1D95),
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            } else {
                Text(value.ifEmpty { "Not provided" }, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}
