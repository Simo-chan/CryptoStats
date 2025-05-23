package com.example.cryptostats.crypto.presentation.coin_search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptostats.core.presentation.util.toDisplayableMessage
import com.example.cryptostats.crypto.presentation.coin_list.components.SearchItem
import com.example.cryptostats.crypto.presentation.models.CoinUI
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onCoinClick: (CoinUI) -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                is SearchAction.OnCoinClick -> onCoinClick(action.coinUI)
                is SearchAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SearchScreenContent(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
) {
    //TODO() just make it look nicer
    LocalSoftwareKeyboardController.current
    remember { FocusRequester() }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onAction(SearchAction.OnBackClick) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Exit Search"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = state.searchQuery,
                onValueChange = { onAction(SearchAction.OnSearchQueryChange(it)) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (state.searchQuery.isEmpty()) {
                            Text(
                                text = "Search...",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.6f
                                    ), fontSize = 18.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )

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
                        onClick = { onAction(SearchAction.OnCoinClick(searchResult)) },
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}