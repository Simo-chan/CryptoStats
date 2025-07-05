package com.example.cryptostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.cryptostats.core.navigation.NavigationGraph
import com.example.cryptostats.core.presentation.ThemeViewModel
import com.example.cryptostats.ui.theme.CryptoStatsTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.themeState.value.isLoading
            }
        }
        setContent {
            val navController = rememberNavController()
            val viewModel: ThemeViewModel = koinViewModel()
            val themeState by viewModel.themeState.collectAsStateWithLifecycle()

            if (!themeState.isLoading) {
                Crossfade(
                    targetState = themeState.isDarkTheme,
                    animationSpec = tween(1000)
                ) { isDarkTheme ->
                    CryptoStatsTheme(darkTheme = isDarkTheme) {
                        NavigationGraph(navController)
                    }
                }
            }
        }
    }
}


