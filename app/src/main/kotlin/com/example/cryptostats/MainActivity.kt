package com.example.cryptostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            var darkTheme by remember { mutableStateOf(true) }
            CryptoStatsTheme(darkTheme = darkTheme) {
                AnimatedContent(
                    targetState = darkTheme,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(1000)) togetherWith
                                fadeOut(animationSpec = tween(1000))
                    }
                ) { isDark ->
                    val listState = rememberLazyListState()
                    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        floatingActionButton = { ScrollUpFAB(listState) },
                        topBar = {
                            CustomToolBar(
                                scrollBehavior = scrollBehavior,
                                darkTheme = isDark,
                                onClick = { darkTheme = !darkTheme }
                            )
                        }
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
}

