package com.simochan.cryptostats.crypto.presentation.coin_search

import androidx.compose.runtime.Immutable
import com.simochan.cryptostats.core.domain.util.NetworkError
import com.simochan.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class SearchState(
    val searchResults: List<CoinUI> = emptyList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val isSearchResultEmpty: Boolean = false,
    val searchError: NetworkError? = null,
)