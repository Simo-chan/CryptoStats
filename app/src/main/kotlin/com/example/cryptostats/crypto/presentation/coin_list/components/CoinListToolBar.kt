package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.ThemeAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListToolBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onThemeChange: (ThemeAction) -> Unit,
    onSearchButtonClick: () -> Unit,
    darkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {},
        actions = {
            SearchButton(onClick = onSearchButtonClick)
            ChangeThemeButton(
                darkTheme = darkTheme,
                onClick = { onThemeChange(ThemeAction.OnSetNewTheme) })
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun SearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search)
        )
    }
}

@Composable
private fun ChangeThemeButton(
    darkTheme: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var rotation by remember { mutableFloatStateOf(0f) }
    val animatable = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    IconButton(onClick = {
        onClick()
        coroutineScope.launch {
            animatable.animateTo(
                targetValue = rotation + 360f,
                animationSpec = tween(500)
            ) {
                rotation = value
            }
        }
    }) {
        Icon(
            imageVector = if (darkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = stringResource(R.string.change_theme),
            modifier = Modifier.rotate(rotation)
        )
    }
}