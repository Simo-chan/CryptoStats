package com.simochan.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.simochan.cryptostats.core.presentation.util.PreviewLightAndDark
import com.simochan.cryptostats.crypto.domain.Coin
import com.simochan.cryptostats.crypto.presentation.models.CoinUI
import com.simochan.cryptostats.crypto.presentation.models.toCoinUI
import com.simochan.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun CoinListItem(
    coinUI: CoinUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUI.iconRes),
            contentDescription = coinUI.name,
            tint = Color.Unspecified,
            modifier = Modifier.size(45.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = coinUI.symbol,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = coinUI.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${coinUI.priceUsd.formatted}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            PriceChange(coinUI.changePercent24Hr)
        }
    }
}

@PreviewLightAndDark
@Composable
private fun CoinListItemPreview() {
    CryptoStatsTheme(true) {
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
    changePercent24Hr = 12.4557
).toCoinUI()
