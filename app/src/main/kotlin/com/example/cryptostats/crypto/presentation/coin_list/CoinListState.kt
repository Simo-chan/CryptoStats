package com.example.cryptostats.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.crypto.domain.Coin
import com.example.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUI> = emptyList(),
    val favoriteCoins: List<Coin> = emptyList(),
    val searchResults: List<CoinUI> = emptyList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val searchError: NetworkError? = null,
    val selectedCoin: CoinUI? = null,
    val errorMessage: NetworkError? = null,
)
