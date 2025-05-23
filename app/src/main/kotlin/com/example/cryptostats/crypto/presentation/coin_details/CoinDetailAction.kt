package com.example.cryptostats.crypto.presentation.coin_details

import com.example.cryptostats.crypto.presentation.models.CoinUI

sealed interface CoinDetailAction {
    data object OnBackClick : CoinDetailAction
    data object OnFavoriteClick : CoinDetailAction
    data object OnRefresh : CoinDetailAction
    data class OnSelectedCoinChange(val coin: CoinUI) : CoinDetailAction
}