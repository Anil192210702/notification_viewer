package com.simats.goodlook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simats.goodlook.ui.theme.GoodlookTheme

object SessionManager {
    var loggedInUser: String = "User"
    var loggedInEmail: String = ""

    private const val PREF_NAME = "safeconnect_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_EMAIL = "email"
    private const val KEY_IS_ADMIN = "is_admin"

    fun saveLoginState(context: android.content.Context, email: String, isAdmin: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_EMAIL, email)
            putBoolean(KEY_IS_ADMIN, isAdmin)
            apply()
        }
    }

    fun saveToggleState(context: android.content.Context, key: String, state: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        prefs.edit().putBoolean(key, state).apply()
    }

    fun getToggleState(context: android.content.Context, key: String, default: Boolean = false): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        return prefs.getBoolean(key, default)
    }

    fun isNotificationListenerEnabled(context: android.content.Context): Boolean {
        val pkgName = context.packageName
        val flat = android.provider.Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(pkgName)
    }

    fun clearLoginState(context: android.content.Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(context: android.content.Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun isAdmin(context: android.content.Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_ADMIN, false)
    }

    fun getEmail(context: android.content.Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        return prefs.getString(KEY_EMAIL, "") ?: ""
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            GoodlookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoodlookTheme {
        Greeting("Android")
    }
}

@Composable
fun AppNavigation() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val navController = rememberNavController()
    
    // Determine the robust Start Destination routing off Persistent cache
    val isUserLoggedIn = com.simats.goodlook.SessionManager.isLoggedIn(context)
    val startDest = if (isUserLoggedIn) {
        // Hydrate volatile RAM contexts 
        val emailRaw = com.simats.goodlook.SessionManager.getEmail(context)
        com.simats.goodlook.SessionManager.loggedInEmail = emailRaw
        com.simats.goodlook.SessionManager.loggedInUser = if (emailRaw.contains("@")) emailRaw.substringBefore("@") else emailRaw
        
        if (com.simats.goodlook.SessionManager.isAdmin(context)) "admin_dashboard" else "main_dashboard"
    } else {
        "welcome"
    }

    NavHost(navController = navController, startDestination = startDest) {
        composable("welcome") {
            com.simats.goodlook.screens.WelcomeScreen(
                onGetStartedClick = { navController.navigate("create_account") },
                onLoginClick = { navController.navigate("login") }
            )
        }
        composable("create_account") {
            com.simats.goodlook.screens.CreateAccountScreen(
                onCreateAccountClick = { navController.navigate("main_dashboard") {
                    popUpTo("welcome") { inclusive = true }
                } },
                onSignInClick = { navController.navigate("login") }
            )
        }
        composable("login") {
            com.simats.goodlook.screens.LoginScreen(
                onLoginClick = { email, password -> 
                    if (email.trim().equals("admin", ignoreCase = true) && password == "anilsai@8978") {
                        navController.navigate("admin_dashboard") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    } else {
                        navController.navigate("permissions") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                },
                onCreateAccountClick = { navController.navigate("create_account") }
            )
        }
        composable("admin_dashboard") {
            com.simats.goodlook.screens.AdminDashboardScreen(
                onNavigate = { route -> 
                    if (route == "logout") {
                        com.simats.goodlook.SessionManager.clearLoginState(context)
                        navController.navigate("welcome") {
                            popUpTo(0) { inclusive = true } // Clear entire back stack
                        }
                    } else {
                        navController.navigate(route)
                    }
                }
            )
        }
        composable("permissions") {
            com.simats.goodlook.screens.PermissionsScreen(
                onContinueClick = { navController.navigate("permissions_success") }
            )
        }
        composable("permissions_success") {
            com.simats.goodlook.screens.PermissionSuccessScreen(
                onDashboardClick = {
                    navController.navigate("main_dashboard") {
                        popUpTo("permissions") { inclusive = true }
                    }
                }
            )
        }
        composable("main_dashboard") {
            com.simats.goodlook.screens.MainDashboardScreen(
                onFamilyClick = { navController.navigate("family") },
                onSettingsClick = { navController.navigate("settings") },
                onNotificationsClick = { navController.navigate("notifications") }
            )
        }
        composable("family") {
            com.simats.goodlook.screens.FamilyMembersScreen()
        }
        composable("alerts") {
            com.simats.goodlook.screens.CallAlertsScreen()
        }
        composable("settings") {
            com.simats.goodlook.screens.SettingsPrivacyScreen(
                onNavigate = { route -> 
                    if (route == "logout") {
                        com.simats.goodlook.SessionManager.clearLoginState(context)
                        navController.navigate("welcome") {
                            popUpTo(0) { inclusive = true } // Clear entire back stack
                        }
                    } else {
                        navController.navigate(route)
                    }
                }
            )
        }
        composable("notifications") {
            com.simats.goodlook.screens.NotificationsCenterScreen()
        }
        composable("location") {
            com.simats.goodlook.screens.LocationScreen()
        }
        composable("sos") {
            com.simats.goodlook.screens.EmergencySOSScreen()
        }
        composable("whatsapp") {
            com.simats.goodlook.screens.WhatsAppSafetyScreen()
        }
        composable("instagram") {
            com.simats.goodlook.screens.InstagramSafetyScreen()
        }
        composable("safety_preferences") {
            com.simats.goodlook.screens.SafetyPreferencesScreen()
        }
        composable("call_details") {
            com.simats.goodlook.screens.CallDetailsScreen()
        }
    }
}