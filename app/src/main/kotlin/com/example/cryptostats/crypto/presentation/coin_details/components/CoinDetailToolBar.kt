package com.example.cryptostats.crypto.presentation.coin_details.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptostats.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailToolBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        },
        actions = { MarkAsFavoriteButton(onClick = onFavoriteClick, isFavorite = isFavorite) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun MarkAsFavoriteButton(
    onClick: () -> Unit,
    isFavorite: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = remember { Animatable(1f) }
    val animationSpec: SpringSpec<Float> =
        spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy)

    LaunchedEffect(isPressed) {
        if (isPressed) {
            scale.animateTo(
                targetValue = 1.8f,
                animationSpec = animationSpec
            )
        } else {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = animationSpec
            )
        }
    }

    IconButton(
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
            contentDescription = stringResource(R.string.mark_fav),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ToolBarPreview() {
    CoinDetailToolBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        onFavoriteClick = {},
        onBackClick = {},
        isFavorite = true,
    )
}