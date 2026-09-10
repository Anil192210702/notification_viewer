package com.simats.goodlook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.simats.com.network.response.ApiClient
import com.simats.com.network.request.PairDeviceRequest

@Composable
fun FamilySetupScreen() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showPairDialog by remember { androidx.compose.runtime.mutableStateOf(false) }
    var childUsername by remember { androidx.compose.runtime.mutableStateOf("") }
    var uniqueId by remember { androidx.compose.runtime.mutableStateOf("") }

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
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF0D61D2))
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(onClick = { showPairDialog = true }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Add Family Member", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Family members must accept your invitation before safety information can be shared.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, textAlign = TextAlign.Center)
    }

    if (showPairDialog) {
        AlertDialog(
            onDismissRequest = { showPairDialog = false },
            title = { Text("Pair with Admin") },
            text = { 
                Column {
                    OutlinedTextField(value = childUsername, onValueChange = { childUsername = it }, label = { Text("Your Username") })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = uniqueId, onValueChange = { uniqueId = it }, label = { Text("Admin Connection ID") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        try {
                            val req = PairDeviceRequest(childUsername, uniqueId)
                            val res = ApiClient.apiService.pairDevice(req)
                            if (res.isSuccessful) {
                                Toast.makeText(context, "Successfully paired!", Toast.LENGTH_SHORT).show()
                                showPairDialog = false
                            } else {
                                Toast.makeText(context, "Pairing failed!", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                           Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) { Text("Connect") }
            }
        )
    }
}
