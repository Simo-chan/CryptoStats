package com.example.cryptostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.cryptostats.core.navigation.AdaptiveCoinListDetailPane
import com.example.cryptostats.crypto.presentation.coin_list.components.CustomToolBar
import com.example.cryptostats.crypto.presentation.coin_list.components.ScrollUpFAB
import com.example.cryptostats.ui.theme.CryptoStatsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoStatsTheme {
                val listState = rememberLazyListState()
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    floatingActionButton = { ScrollUpFAB(listState) },
                    topBar = { CustomToolBar(scrollBehavior) }
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

