package com.example.cryptostats.crypto.presentation.coin_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.util.toDisplayableMessage
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptostats.crypto.presentation.coin_list.components.FavoriteCoinCard
import com.example.cryptostats.crypto.presentation.coin_list.components.ShimmerLoadingList
import com.example.cryptostats.crypto.presentation.coin_list.components.TryAgainButton
import com.example.cryptostats.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun CoinListScreen(
    state: CoinListState,
    listState: LazyListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when {
        state.isLoading -> ShimmerLoadingList()
        state.isError -> TryAgainButton(
            state.errorMessage?.toDisplayableMessage(context)
                ?: stringResource(R.string.error_unknown), onAction = onAction
        )

        else -> CoinList(state = state, listState = listState, onAction = onAction)
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
                modifier = Modifier
                    .padding(top = 45.dp)
                    .padding(horizontal = 16.dp)
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
        items(state.coins) { coinUI ->
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
    CryptoStatsTheme {
        Surface {
            CoinListScreen(
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