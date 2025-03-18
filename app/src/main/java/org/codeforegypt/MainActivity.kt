package org.codeforegypt


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.codeforegypt.core.navigation.Navigation
import org.codeforegypt.core.navigation.Screen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                startDestination = Screen.HomeScreen.route
            )
        }
    }
}

