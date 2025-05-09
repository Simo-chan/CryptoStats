package com.example.cryptostats.crypto.presentation.coin_list

import com.example.cryptostats.crypto.presentation.models.CoinUI

sealed interface CoinListAction {
    data class OnCoinClick(val coinUI: CoinUI) : CoinListAction
    data class OnSearchQueryChange(val query: String): CoinListAction
    object OnRefresh : CoinListAction
}