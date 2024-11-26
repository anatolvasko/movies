package com.clearing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.clearing.core_ui.navigation.MoviesNavigation
import com.clearing.core_ui.theme.ClearingTheme
import com.clearing.navigation.moviesGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ClearingTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MoviesNavigation.MoviesRoute,
                    builder = {
                        moviesGraph(navController = navController)
                    }
                )
            }
        }
    }
}

