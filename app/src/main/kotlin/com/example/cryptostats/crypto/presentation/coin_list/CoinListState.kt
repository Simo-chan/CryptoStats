package com.example.cryptostats.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUI> = emptyList(),
    val selectedCoin: CoinUI? = null,
    val isError: Boolean = false,
    val errorMessage: NetworkError? = null
)
