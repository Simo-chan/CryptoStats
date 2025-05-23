package com.example.cryptostats.crypto.presentation.coin_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptostats.R
import com.example.cryptostats.core.presentation.util.toDisplayableMessage
import com.example.cryptostats.crypto.presentation.coin_list.CoinListAction
import com.example.cryptostats.crypto.presentation.coin_list.CoinListState

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinSearchBar(
    onHideClick: () -> Unit,
    onAction: (CoinListAction) -> Unit,
    state: CoinListState,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = state.searchQuery,
                onQueryChange = { onAction(CoinListAction.OnSearchQueryChange(it)) },
                onSearch = { keyboardController?.hide() },
                expanded = true,
                onExpandedChange = {},
                modifier = modifier,
                placeholder = { Text(text = stringResource(R.string.search_hint)) },
                leadingIcon = {
                    IconButton(onClick = onHideClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.close_search)
                        )
                    }
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = state.searchQuery.isNotBlank()
                    ) {
                        IconButton(onClick = { onAction(CoinListAction.OnSearchQueryChange("")) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close_hint)
                            )
                        }
                    }
                }
            )
        },
        expanded = true,
        onExpandedChange = {}
    ) {
        when {
            state.isSearching -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            state.searchError != null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.searchError.toDisplayableMessage(context))
            }

            else -> LazyColumn {
                items(items = state.searchResults) { searchResult ->
                    SearchItem(
                        searchUI = searchResult,
                        onClick = { onAction(CoinListAction.OnCoinClick(searchResult)) },
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}*/
