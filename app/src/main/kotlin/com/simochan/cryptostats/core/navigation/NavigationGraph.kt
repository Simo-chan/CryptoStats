package com.simochan.cryptostats.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.simochan.cryptostats.crypto.presentation.SelectedCoinViewModel
import com.simochan.cryptostats.crypto.presentation.coin_details.CoinDetailAction
import com.simochan.cryptostats.crypto.presentation.coin_details.CoinDetailScreen
import com.simochan.cryptostats.crypto.presentation.coin_details.CoinDetailViewModel
import com.simochan.cryptostats.crypto.presentation.coin_list.CoinListScreen
import com.simochan.cryptostats.crypto.presentation.coin_search.SearchScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.CoinGraph
    ) {
        navigation<Route.CoinGraph>(startDestination = Route.CoinList) {
            composable<Route.CoinList>(
                enterTransition = { null },
                exitTransition = { null },
                popEnterTransition = { null },
                popExitTransition = { null }
            ) {
                val selectedViewModel = it.sharedKoinViewModel<SelectedCoinViewModel>(navController)

                LaunchedEffect(true) {
                    selectedViewModel.onSelectCoin(null)
                }
                CoinListScreen(
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
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(700)
                    ) + fadeIn(animationSpec = tween(700))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(700)
                    ) + fadeIn(animationSpec = tween(700))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                }
            ) {
                val viewModel = koinViewModel<CoinDetailViewModel>()
                val selectedViewModel =
                    it.sharedKoinViewModel<SelectedCoinViewModel>(navController)
                val selectedCoin by selectedViewModel.selectedCoin.collectAsStateWithLifecycle()

                LaunchedEffect(selectedCoin) {
                    selectedCoin?.let {
                        viewModel.onAction(CoinDetailAction.OnSelectedCoinChange(it))
                    }
                }

                CoinDetailScreen(
                    onBackClick = {
                        navController.popBackStack(
                            route = Route.CoinList,
                            inclusive = false
                        )
                    }
                )
            }

            composable<Route.CoinSearch>(
                enterTransition = {
                    expandVertically(
                        expandFrom = Alignment.Top,
                        initialHeight = { 0 },
                        animationSpec = tween(700)
                    )
                },
                popExitTransition = {
                    shrinkVertically(
                        shrinkTowards = Alignment.Top,
                        targetHeight = { 0 },
                        animationSpec = tween(700)
                    )
                }
            ) {
                val selectedViewModel = it.sharedKoinViewModel<SelectedCoinViewModel>(navController)

                LaunchedEffect(true) {
                    selectedViewModel.onSelectCoin(null)
                }

                SearchScreen(
                    onBackClick = {
                        navController.popBackStack(
                            route = Route.CoinList,
                            inclusive = false
                        )
                    },
                    onCoinClick = { coin ->
                        selectedViewModel.onSelectCoin(coin)
                        navController.navigate(
                            Route.CoinDetails(coin.id)
                        )
                    }
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
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