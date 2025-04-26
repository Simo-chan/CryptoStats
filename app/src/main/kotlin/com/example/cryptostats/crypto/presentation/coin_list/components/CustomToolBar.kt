package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import com.example.cryptostats.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onThemeChange: () -> Unit,
    darkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {},
        actions = {
            SearchButton()
            ChangeThemeButton(darkTheme = darkTheme, onClick = onThemeChange)
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun SearchButton(modifier: Modifier = Modifier) {
    IconButton(onClick = { }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = { text = it },
                onSearch = { },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = modifier,
                placeholder = { Text(text = stringResource(R.string.search_hint)) },
                leadingIcon = {
                    IconButton(onClick = { expanded = false }, enabled = expanded) {
                        Icon(
                            imageVector = if (expanded) Icons.AutoMirrored.Outlined.ArrowBack else Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = text.isNotBlank()
                    ) {
                        IconButton(onClick = { text = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close_hint)
                            )
                        }
                    }
                }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {

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