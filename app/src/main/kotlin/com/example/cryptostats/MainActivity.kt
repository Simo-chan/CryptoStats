package com.example.cryptostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.cryptostats.core.navigation.AdaptiveCoinListDetailPane
import com.example.cryptostats.crypto.presentation.coin_list.components.ScrollUpFAB
import com.example.cryptostats.ui.theme.CryptoStatsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoStatsTheme {
                val listState = rememberLazyListState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = { ScrollUpFAB(listState) }
                ) { innerPadding ->
                    AdaptiveCoinListDetailPane(
                        listState = listState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

