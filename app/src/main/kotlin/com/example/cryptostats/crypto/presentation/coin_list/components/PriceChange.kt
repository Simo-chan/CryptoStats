package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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