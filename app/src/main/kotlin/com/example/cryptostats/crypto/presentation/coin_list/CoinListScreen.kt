package com.example.cryptostats.crypto.presentation.coin_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.ThemeAction
import com.example.cryptostats.core.presentation.ThemeViewModel
import com.example.cryptostats.core.presentation.util.toDisplayableMessage
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinListToolBar
import com.example.cryptostats.crypto.presentation.coin_list.components.FavoriteCoinCard
import com.example.cryptostats.crypto.presentation.coin_list.components.ScrollUpFAB
import com.example.cryptostats.crypto.presentation.coin_list.components.ShimmerLoadingList
import com.example.cryptostats.crypto.presentation.coin_list.components.TryAgainButton
import com.example.cryptostats.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptostats.crypto.presentation.models.CoinUI
import com.example.cryptostats.ui.theme.CryptoStatsTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinListScreen(
    onCoinClick: (CoinUI) -> Unit,
    onSearchButtonClick: () -> Unit,
    viewModel: CoinListViewModel = koinViewModel(),
    themeViewModel: ThemeViewModel = koinViewModel(),
) {
    val themeState by themeViewModel.themeState.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    CoinListScreenContent(
        state = state,
        isDarkTheme = themeState.isDarkTheme,
        onThemeChange = themeViewModel::onAction,
        onAction = { action ->
            when (action) {
                is CoinListAction.OnCoinClick -> onCoinClick(action.coin)
                is CoinListAction.OnSearchButtonClick -> onSearchButtonClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinListScreenContent(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    onThemeChange: (ThemeAction) -> Unit,
    isDarkTheme: Boolean,
) {
    val context = LocalContext.current

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = { ScrollUpFAB(listState) },
        topBar = {
            CoinListToolBar(
                scrollBehavior = scrollBehavior,
                darkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                onSearchButtonClick = { onAction(CoinListAction.OnSearchButtonClick) }
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> ShimmerLoadingList(modifier = Modifier.padding(innerPadding))

            state.errorMessage != null -> TryAgainButton(
                message = state.errorMessage.toDisplayableMessage(context),
                onClick = { onAction(CoinListAction.OnRefresh) },
                modifier = Modifier.padding(innerPadding)
            )

            else ->
                CoinList(
                    state = state,
                    listState = listState,
                    onAction = onAction,
                    modifier = Modifier.padding(innerPadding)
                )
        }
    }
}

@Composable
private fun CoinList(
    modifier: Modifier = Modifier,
    state: CoinListState,
    listState: LazyListState,
    onAction: (CoinListAction) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
    ) {
        if (state.favoriteCoins.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.favorites),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    maxLines = 1
                )
            }
            item { FavoriteCoinList(state = state, onAction = onAction) }
        }
        item {
            Text(
                text = stringResource(R.string.top100),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(16.dp),
               maxLines = 1
            )
        }
        items(
            items = state.coins
        ) { coinUI ->
            CoinListItem(
                coinUI = coinUI,
                onClick = { onAction(CoinListAction.OnCoinClick(coinUI)) },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun FavoriteCoinList(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(
            items = state.favoriteCoins
        ) { coinUI ->
            FavoriteCoinCard(
                coinUI = coinUI,
                onClick = { onAction(CoinListAction.OnCoinClick(coinUI)) },
                modifier = Modifier
                    .width(180.dp)
                    .height(190.dp)
            )
        }
    }
}

@PreviewLightDark
@Preview(widthDp = 320, heightDp = 320)
@Composable
private fun CoinListScreenPreview() {
    CryptoStatsTheme(true) {
        Surface {
            CoinListScreenContent(
                state = CoinListState(
                    coins = (1..100).map {
                        previewCoin.copy(id = it.toString())
                    }
                ),
                onAction = {},
                onThemeChange = {},
                isDarkTheme = true
            )
        }
    }
}