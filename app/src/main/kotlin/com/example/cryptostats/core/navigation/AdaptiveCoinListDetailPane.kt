package com.example.cryptostats.core.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailScreen
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailViewModel
import com.example.cryptostats.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptostats.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel(),
    detailViewModel: CoinDetailViewModel = koinViewModel(),
) {
    //TODO() just marking as sth to fix
    val selectedCoin by viewModel.state.collectAsStateWithLifecycle()
    val state by detailViewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scope = rememberCoroutineScope()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    onCoinClick = {},
                    onSearchButtonClick = {}
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(
                    onBackClick = {}
                )
            }
        },
        modifier = modifier
    )
}
