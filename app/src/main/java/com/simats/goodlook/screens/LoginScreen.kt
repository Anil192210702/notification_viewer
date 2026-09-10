package com.simats.goodlook.screens

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
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onCreateAccountClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Lock, contentDescription = "Logo", modifier = Modifier.size(64.dp), tint = Color(0xFF0D61D2))
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
        
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Email and Password required", Toast.LENGTH_SHORT).show()
                } else {
                    scope.launch {
                        isLoading = true
                        try {
                            val req = com.simats.com.network.request.SignInRequest(email, password)
                            val res = com.simats.com.network.response.ApiClient.apiService.signIn(req)
                            if (res.isSuccessful) {
                                val isAdmin = email.trim().equals("admin", ignoreCase = true)
                                com.simats.goodlook.SessionManager.saveLoginState(context, email, isAdmin)
                                com.simats.goodlook.SessionManager.loggedInEmail = email
                                com.simats.goodlook.SessionManager.loggedInUser = email.substringBefore("@")
                                onLoginClick(email, password)
                            } else {
                                Toast.makeText(context, "Error: ${res.code()}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                        } finally {
                            isLoading = false
                        }
                    }
                }
            }, 
            modifier = Modifier.fillMaxWidth().height(50.dp), 
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)),
            enabled = !isLoading,
            shape = RoundedCornerShape(25.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Sign In", color = Color.White)
            }
        }
        TextButton(onClick = onCreateAccountClick, modifier = Modifier.padding(top = 16.dp)) {
            Text("Create Account", color = Color(0xFF0D61D2))
        }
    }
}
