package com.example.cryptostats.crypto.presentation.coin_list

import com.example.cryptostats.crypto.presentation.models.CoinUI

sealed interface CoinListAction {
    data class OnCoinClick(val coinUI: CoinUI) : CoinListAction
    object OnSearchButtonClick : CoinListAction
    object OnRefresh : CoinListAction
}