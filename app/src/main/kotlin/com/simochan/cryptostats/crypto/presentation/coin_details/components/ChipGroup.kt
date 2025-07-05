package com.simochan.cryptostats.crypto.presentation.coin_details.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simochan.cryptostats.R

enum class Chips(val value: String) {
    H6("h6"),
    H12("h12"),
    H24("d1")
}

@Composable
fun ChipGroup(
    selectedChip: Chips,
    onChipSelectionChange: (Chips) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Chips.entries.forEach { chipEnum ->
            val label = when (chipEnum) {
                Chips.H24 -> stringResource(R.string.h24)
                Chips.H12 -> stringResource(R.string.h12)
                Chips.H6 -> stringResource(R.string.h6)
            }
            Chip(
                text = label,
                isSelected = selectedChip == chipEnum,
                onClick = { onChipSelectionChange(chipEnum) }
            )
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.Gray.copy(0.4f)
        else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
    )

    Box(
        modifier = modifier
            .padding(4.dp)
            .toggleable(
                value = isSelected,
                onValueChange = { onClick() },
            )
            .clip(CircleShape)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun ChipPreview() {
    Surface {
        Chip(
            text = "24h",
            isSelected = false,
            onClick = {},
        )
    }
}


