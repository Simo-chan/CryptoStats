package com.example.cryptostats.crypto.presentation.coin_search

import androidx.compose.runtime.Immutable
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class SearchState(
    val searchResults: List<CoinUI> = emptyList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val isSearchResultEmpty: Boolean = false,
    val searchError: NetworkError? = null,
)