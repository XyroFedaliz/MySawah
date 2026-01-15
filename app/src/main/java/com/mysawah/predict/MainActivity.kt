package com.mysawah.predict

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext // <--- Import ini
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mysawah.predict.data.local.SaveToken // <--- Import TokenManager
import com.mysawah.predict.ui.navigation.AppNavigation
import com.mysawah.predict.ui.theme.MySawahTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MySawahTheme {
                val context = LocalContext.current
                val token = SaveToken.getToken(context)
                val startDest = if (token != null) "home" else "welcome"
                AppNavigation(startDestination = startDest)
            }
        }
    }
}