package com.simochan.cryptostats.crypto.presentation.coin_list

import com.simochan.cryptostats.crypto.presentation.models.CoinUI

sealed interface CoinListAction {
    data class OnCoinClick(val coin: CoinUI) : CoinListAction
    object OnSearchButtonClick : CoinListAction
    object OnRefresh : CoinListAction
}