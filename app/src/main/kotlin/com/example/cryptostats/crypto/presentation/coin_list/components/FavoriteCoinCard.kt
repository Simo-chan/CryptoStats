package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.util.PreviewLightAndDark
import com.example.cryptostats.crypto.presentation.models.CoinUI
import com.example.cryptostats.ui.theme.CryptoStatsTheme

@Composable
fun FavoriteCoinCard(
    coinUI: CoinUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.onTertiaryContainer
    )
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(32.dp),
    ) {
        Box(modifier = Modifier.background(brush = gradientBrush)) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier
                    .clickable { onClick() }
                    .padding(32.dp)
                    .fillMaxSize()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = coinUI.iconRes),
                        contentDescription = coinUI.name,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(45.dp)
                    )
                    Column {
                        Text(
                            text = coinUI.symbol,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                        Text(
                            text = coinUI.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Column {
                    Text(
                        text = "$${coinUI.priceUsd.formatted}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                    PriceChange(coinUI.changePercent24Hr)
                }
                val isPositive = coinUI.changePercent24Hr.value > 0
                Icon(
                    imageVector = if (isPositive) {
                        ImageVector.vectorResource(id = R.drawable.trending_up)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.trending_down)
                    },
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@PreviewLightAndDark
@Composable
private fun FavoriteCoinCardPreview() {
    CryptoStatsTheme(true) {
        Surface {
            FavoriteCoinCard(
                coinUI = previewCoin,
                onClick = {}
            )
        }
    }
}