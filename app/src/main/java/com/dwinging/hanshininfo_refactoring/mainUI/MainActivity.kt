package com.dwinging.hanshininfo_refactoring.mainUI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dwinging.hanshininfo_refactoring.data.prefs.PrefsManager
import com.dwinging.hanshininfo_refactoring.splash.SplashScreen
import com.dwinging.hanshininfo_refactoring.ui.theme.HanshinInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        PrefsManager.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            HanshinInfo(dynamicColor = false) {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) } // Splash 화면
        composable("main") { MainScreen() } // Main 화면
    }
}

