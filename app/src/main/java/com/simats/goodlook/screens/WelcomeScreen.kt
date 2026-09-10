package com.simats.goodlook.screens

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
fun WelcomeScreen(
    onGetStartedClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Person, contentDescription = "Family", modifier = Modifier.size(120.dp), tint = Color(0xFF0D61D2))
        Spacer(modifier = Modifier.height(32.dp))
        Text("Stay Connected. Stay Safe.", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Keep trusted family members connected through safety alerts, location sharing and emergency features.", style = MaterialTheme.typography.bodyMedium, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = onGetStartedClick, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D61D2)), shape = RoundedCornerShape(25.dp)) {
            Text("Get Started", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onLoginClick, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("I Already Have an Account", color = Color(0xFF0D61D2))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text("You control what information you share.", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}
