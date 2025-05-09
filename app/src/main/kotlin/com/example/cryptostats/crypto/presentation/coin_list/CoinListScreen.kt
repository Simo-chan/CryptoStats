package com.example.cryptostats.crypto.presentation.coin_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.ThemeViewModel
import com.example.cryptostats.core.presentation.util.toDisplayableMessage
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinSearchBar
import com.example.cryptostats.crypto.presentation.coin_list.components.FavoriteCoinCard
import com.example.cryptostats.crypto.presentation.coin_list.components.MainToolBar
import com.example.cryptostats.crypto.presentation.coin_list.components.ScrollUpFAB
import com.example.cryptostats.crypto.presentation.coin_list.components.ShimmerLoadingList
import com.example.cryptostats.crypto.presentation.coin_list.components.TryAgainButton
import com.example.cryptostats.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptostats.ui.theme.CryptoStatsTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    onAction: (CoinListAction) -> Unit,
    coinListViewModel: CoinListViewModel = koinViewModel(),
    themeViewModel: ThemeViewModel = koinViewModel(),
) {
    val themeState by themeViewModel.themeState.collectAsStateWithLifecycle()
    val state by coinListViewModel.state.collectAsStateWithLifecycle()

    CoinListScreenContent(
        state = state,
        isDarkTheme = themeState.isDarkTheme,
        onAction = onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinListScreenContent(
    state: CoinListState,
    isDarkTheme: Boolean,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isSearchBarExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = { ScrollUpFAB(listState) },
        topBar = {
            if (isSearchBarExpanded) {
                CoinSearchBar(
                    onHideClick = { isSearchBarExpanded = false },
                    state = state,
                    onAction = onAction
                )
            } else {
                MainToolBar(
                    scrollBehavior = scrollBehavior,
                    darkTheme = isDarkTheme,
                    onThemeChange = { onAction(CoinListAction.OnSetNewTheme) },
                    onSearchButtonClick = { isSearchBarExpanded = true }
                )
            }
        }
    ) { innerPadding ->
        when {
            state.isLoading -> ShimmerLoadingList(modifier = modifier.padding(innerPadding))

            state.errorMessage != null -> TryAgainButton(
                message = state.errorMessage.toDisplayableMessage(context),
                onAction = onAction,
                modifier = modifier.padding(innerPadding)
            )

            else ->
                CoinList(
                    state = state,
                    listState = listState,
                    onAction = onAction,
                    modifier = modifier.padding(innerPadding)
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
        item {
            Text(
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item { FavoriteCoinList() }
        item {
            Text(
                text = stringResource(R.string.top100),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 45.dp)
                    .padding(horizontal = 16.dp)
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
private fun FavoriteCoinList(modifier: Modifier = Modifier) {
    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
        item {
            FavoriteCoinCard(
                coinUI = previewCoin,
                onClick = {},
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        item {
            FavoriteCoinCard(
                coinUI = previewCoin,
                onClick = {},
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        item {
            FavoriteCoinCard(
                coinUI = previewCoin,
                onClick = {},
                modifier = Modifier.padding(horizontal = 8.dp)
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
                isDarkTheme = true
            )
        }
    }
}