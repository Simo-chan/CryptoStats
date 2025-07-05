package com.example.cryptostats.crypto.presentation.coin_details

import androidx.compose.runtime.Immutable
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.crypto.presentation.coin_details.components.Chips
import com.example.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class CoinDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val coin: CoinUI? = null,
    val selectedChip: Chips = Chips.H24,
    val errorMessage: NetworkError? = null,
)