package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptostats.crypto.presentation.models.DisplayableNumber
import com.example.cryptostats.ui.theme.brightGreen
import com.example.cryptostats.ui.theme.brightRed

@Composable
fun PriceChange(
    change: DisplayableNumber,
    modifier: Modifier = Modifier,
) {
    val priceChangeColor = if (change.value < 0.0) {
        brightRed
    } else {
        brightGreen
    }
    Text(
        text = "${change.formatted} %",
        style = MaterialTheme.typography.bodyMedium,
        color = priceChangeColor
    )
}

@Preview
@Composable
fun PriceChangePreview() {
    val change = DisplayableNumber(
        value = 1.4,
        formatted = "1.4"
    )
    PriceChange(change = change)
}