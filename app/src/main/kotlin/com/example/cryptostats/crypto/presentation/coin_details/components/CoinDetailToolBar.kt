package com.example.cryptostats.crypto.presentation.coin_details.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = stringResource(R.string.mark_fav),
            tint = if (isFavorite) Color.Yellow else Color.Unspecified
        )
    }
}