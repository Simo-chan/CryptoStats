package com.example.cryptostats.crypto.presentation.coin_details

import androidx.compose.runtime.Immutable
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class CoinDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val selectedCoin: CoinUI? = null,
    val errorMessage: NetworkError? = null,
)