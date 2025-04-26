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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.util.toDisplayableMessage
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptostats.crypto.presentation.coin_list.components.CustomSearchBar
import com.example.cryptostats.crypto.presentation.coin_list.components.CustomToolBar
import com.example.cryptostats.crypto.presentation.coin_list.components.FavoriteCoinCard
import com.example.cryptostats.crypto.presentation.coin_list.components.ScrollUpFAB
import com.example.cryptostats.crypto.presentation.coin_list.components.ShimmerLoadingList
import com.example.cryptostats.crypto.presentation.coin_list.components.TryAgainButton
import com.example.cryptostats.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptostats.ui.theme.CryptoStatsTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    viewModel: CoinListViewModel = koinViewModel(),
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isDarkTheme by viewModel.themeState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = { ScrollUpFAB(listState) },
        topBar = {
            CustomSearchBar(modifier = Modifier)
            CustomToolBar(
                scrollBehavior = scrollBehavior,
                darkTheme = isDarkTheme,
                onThemeChange = { onAction(CoinListAction.OnSetNewTheme) }
            )
        }
    ) { innerPadding ->
        CoinListScreenContent(
            state = state,
            listState = listState,
            onAction = onAction,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun CoinListScreenContent(
    state: CoinListState,
    listState: LazyListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when {
        state.isLoading -> ShimmerLoadingList()
        state.isError -> TryAgainButton(
            message =
                //Can improve it by nullifying error message
                state.errorMessage?.toDisplayableMessage(context)
                    ?: stringResource(R.string.error_unknown),
            onAction = onAction
        )

        else ->
            CoinList(
                state = state,
                listState = listState,
                onAction = onAction,
                modifier = modifier
            )
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
                listState = rememberLazyListState()
            )
        }
    }
}