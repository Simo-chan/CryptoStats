package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp
import com.example.cryptostats.core.presentation.util.preview.PreviewLightAndDark
import com.example.cryptostats.crypto.domain.Coin
import com.example.cryptostats.crypto.presentation.models.CoinUI
import com.example.cryptostats.crypto.presentation.models.toCoinUI
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun CoinListItem(
    coinUI: CoinUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = coinUI.iconRes),
                    contentDescription = coinUI.name,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(50.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coinUI.symbol,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = coinUI.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$ ${coinUI.priceUsd.formatted}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PriceChange(
                        change = coinUI.changePercent24Hr
                    )
                }
            }
        }

    }
}

@PreviewLightAndDark
@Preview(widthDp = 320, heightDp = 320)
@PreviewDynamicColors
@Composable
private fun CoinListItemPreview() {
    CryptoStatsTheme {
        Surface {
            CoinListItem(
                coinUI = previewCoin,
                onClick = {},
                modifier = Modifier
            )
        }
    }
}

internal val previewCoin = Coin(
    id = "BTC",
    rank = 1,
    name = "BitCoin",
    symbol = "BTC",
    marketCapUsd = 1021123009909.23,
    priceUsd = 62000.15,
    changePercent24Hr = 1.4
).toCoinUI()
