package com.example.cryptostats.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.cryptostats.crypto.presentation.SelectedCoinViewModel
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailAction
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailScreen
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailViewModel
import com.example.cryptostats.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptostats.crypto.presentation.coin_search.SearchScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Serializable
data object CoinList : NavKey

@Serializable
data object CoinSearch : NavKey

@Serializable
data class CoinDetails(val id: String) : NavKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NewNavigationGraph() {
    val backStack = rememberNavBackStack(CoinList)
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        backStack = backStack,
        sceneStrategy = listDetailStrategy,
        entryProvider = entryProvider {
            entry<CoinList>(
                metadata = ListDetailSceneStrategy.listPane(
                    detailPlaceholder = {
                        Surface {
                            Box(contentAlignment = Alignment.Center) {
                                Text("Choose from the list")
                            }
                        }
                    }
                )
            ) {
                val selectedViewModel = koinViewModel<SelectedCoinViewModel>()
                LaunchedEffect(true) {
                    selectedViewModel.onSelectCoin(null)
                }

                CoinListScreen(
                    onCoinClick = { coin ->
                        selectedViewModel.onSelectCoin(coin)
                        backStack.add(CoinDetails(coin.id))
                    },
                    onSearchButtonClick = {
                        backStack.add(CoinSearch)
                    }
                )
            }

            entry<CoinDetails>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { args ->
                val viewModel = koinViewModel<CoinDetailViewModel> { parametersOf(args.id) }
                val selectedViewModel = koinViewModel<SelectedCoinViewModel>()
                val selectedCoin by selectedViewModel.selectedCoin.collectAsStateWithLifecycle()

                LaunchedEffect(selectedCoin) {
                    selectedCoin?.let {
                        viewModel.onAction(CoinDetailAction.OnSelectedCoinChange(it))
                    }
                }

                CoinDetailScreen(
                    onBackClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<CoinSearch> {
                SearchScreen(
                    onCoinClick = { coin ->
                        backStack.add(CoinDetails(coin.id))
                    },
                    onBackClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}