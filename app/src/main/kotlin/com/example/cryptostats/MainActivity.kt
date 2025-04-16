package com.example.cryptostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptostats.core.navigation.AdaptiveCoinListDetailPane
import com.example.cryptostats.crypto.presentation.coin_list.CoinListViewModel
import com.example.cryptostats.ui.theme.CryptoStatsTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CoinListViewModel = koinViewModel()
            val isDarkTheme by viewModel.themeState.collectAsStateWithLifecycle()

            Crossfade(targetState = isDarkTheme, animationSpec = tween(1000)) { newTheme ->
                CryptoStatsTheme(darkTheme = newTheme) {
                    Scaffold { inner-> }
                    AdaptiveCoinListDetailPane()
                }
            }
        }
    }
}


