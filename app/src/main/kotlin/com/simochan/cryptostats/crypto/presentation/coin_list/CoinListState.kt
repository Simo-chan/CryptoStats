package com.simochan.cryptostats.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.simochan.cryptostats.core.domain.util.NetworkError
import com.simochan.cryptostats.crypto.presentation.models.CoinUI

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUI> = emptyList(),
    val favoriteCoins: List<CoinUI> = emptyList(),
    val selectedCoin: CoinUI? = null,
    val errorMessage: NetworkError? = null,
)
