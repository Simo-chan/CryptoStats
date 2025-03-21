package com.example.cryptostats.crypto.presentation.coin_list

import com.example.cryptostats.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}