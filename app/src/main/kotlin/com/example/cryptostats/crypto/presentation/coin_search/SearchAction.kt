package com.example.cryptostats.crypto.presentation.coin_search

import com.example.cryptostats.crypto.presentation.coin_list.CoinListAction
import com.example.cryptostats.crypto.presentation.models.CoinUI

sealed interface SearchAction {
    data class OnSearchQueryChange(val query: String) : SearchAction
    data class OnCoinClick(val coinUI: CoinUI) : SearchAction
    object OnBackClick : SearchAction
}