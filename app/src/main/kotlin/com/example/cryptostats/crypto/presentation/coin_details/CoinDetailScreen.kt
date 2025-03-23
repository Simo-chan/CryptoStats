package com.example.cryptostats.crypto.presentation.coin_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.util.preview.PreviewLightAndDark
import com.example.cryptostats.crypto.presentation.coin_details.components.InfoCard
import com.example.cryptostats.crypto.presentation.coin_list.CoinListState
import com.example.cryptostats.crypto.presentation.coin_list.components.previewCoin
import com.example.cryptostats.crypto.presentation.models.toDisplayableNumber
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = coin.iconRes
                ),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )

                val absoluteChangeFormatted =
                    (coin.priceUsd.value * (coin.changePercent24Hr.value / 100))
                        .toDisplayableNumber()
                val isPositive = coin.changePercent24Hr.value > 0.0
                val contentColor = if (isPositive) {
                    Color.Green
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_24h),
                    formattedText = absoluteChangeFormatted.formatted,
                    icon = if (isPositive) {
                        ImageVector.vectorResource(id = R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }
        }
    }
}

@PreviewLightAndDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoStatsTheme {
        Surface {
            CoinDetailScreen(
                state = CoinListState(
                    selectedCoin = previewCoin
                )
            )
        }
    }
}