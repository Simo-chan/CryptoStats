package com.example.cryptostats.crypto.presentation.coin_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cryptostats.crypto.presentation.coin_list.components.CoinListItem
import com.example.cryptostats.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptostats.ui.theme.CryptoStatsTheme
import kotlinx.coroutines.launch

@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            CoinList(listState = listState, state = state, onAction = onAction)

            val showButton by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                }
            }
            val coroutineScope = rememberCoroutineScope()
            AnimatedVisibility(
                visible = showButton,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp)
                    .padding(bottom = 16.dp),
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
                )
            ) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun CoinList(
    modifier: Modifier = Modifier,
    state: CoinListState,
    listState: LazyListState = rememberLazyListState(),
    onAction: (CoinListAction) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(state.coins) { coinUI ->
            CoinListItem(
                coinUI = coinUI,
                onClick = { onAction(CoinListAction.OnCoinClick(coinUI)) }
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
                onAction = {}
            )
        }
    }
}