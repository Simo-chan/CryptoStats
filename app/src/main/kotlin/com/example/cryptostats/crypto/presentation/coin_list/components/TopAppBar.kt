package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cryptostats.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {},
        actions = {
            SearchButton()
            ChangeThemeButton()
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun SearchButton(modifier: Modifier = Modifier) {
    IconButton(onClick = { TODO() }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search)
        )
    }
}

@Composable
private fun ChangeThemeButton(modifier: Modifier = Modifier) {
    IconButton(onClick = { TODO() }) {
        Icon(
            imageVector = Icons.Default.DarkMode,
            contentDescription = stringResource(R.string.change_theme)
        )
    }
}