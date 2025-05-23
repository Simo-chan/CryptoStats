package com.example.cryptostats.core.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.cryptostats.core.presentation.ThemeViewModel
import com.example.cryptostats.crypto.presentation.SelectedCoinViewModel
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailAction
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailScreen
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailViewModel
import com.example.cryptostats.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptostats.crypto.presentation.coin_list.CoinListViewModel
import com.example.cryptostats.crypto.presentation.coin_search.SearchScreen
import com.example.cryptostats.crypto.presentation.coin_search.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.CoinGraph
    ) {
        navigation<Route.CoinGraph>(startDestination = Route.CoinList) {
            composable<Route.CoinList>(
                enterTransition = { slideInHorizontally() },
                exitTransition = { slideOutHorizontally() }
            ) {
                val viewModel = koinViewModel<CoinListViewModel>()
                val themeViewModel = koinViewModel<ThemeViewModel>()
                val selectedViewModel = it.sharedKoinViewModel<SelectedCoinViewModel>(navController)

                LaunchedEffect(true) {
                    selectedViewModel.onSelectCoin(null)
                }

                CoinListScreen(
                    viewModel = viewModel,
                    themeViewModel = themeViewModel,
                    onCoinClick = { coin ->
                        selectedViewModel.onSelectCoin(coin)
                        navController.navigate(
                            Route.CoinDetails(coin.id)
                        )
                    },
                    onSearchButtonClick = {
                        navController.navigate(
                            Route.CoinSearch
                        )
                    }
                )
            }
            composable<Route.CoinDetails>(
                enterTransition = {
                    slideInHorizontally { initialOffset ->
                        initialOffset
                    }
                },
                exitTransition = {
                    slideOutHorizontally { initialOffset ->
                        initialOffset
                    }
                }
            ) {
                val viewModel = koinViewModel<CoinDetailViewModel>()
                val selectedViewModel = it.sharedKoinViewModel<SelectedCoinViewModel>(navController)
                val selectedCoin by selectedViewModel.selectedCoin.collectAsStateWithLifecycle()

                LaunchedEffect(selectedCoin) {
                    selectedCoin?.let {
                        viewModel.onAction(CoinDetailAction.OnSelectedCoinChange(it))
                    }
                }

                CoinDetailScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigate(Route.CoinList)
                    }
                )
            }
            composable<Route.CoinSearch>(
                enterTransition = { expandHorizontally() },
                exitTransition = { shrinkHorizontally() }) {
                val viewModel = koinViewModel<SearchViewModel>()
                //TODO() add onCoinClick
                SearchScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.navigateUp() },
                    onCoinClick = {}
                )
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}