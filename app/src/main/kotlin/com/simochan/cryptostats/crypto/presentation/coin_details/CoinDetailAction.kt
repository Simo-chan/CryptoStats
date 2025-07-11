package com.simochan.cryptostats.crypto.presentation.coin_details

import com.simochan.cryptostats.crypto.presentation.coin_details.components.Chips
import com.simochan.cryptostats.crypto.presentation.models.CoinUI

sealed interface CoinDetailAction {
    data object OnBackClick : CoinDetailAction
    data object OnFavoriteClick : CoinDetailAction
    data object OnRefresh : CoinDetailAction
    data class OnSelectedCoinChange(val coin: CoinUI) : CoinDetailAction
    data class OnChipSelectionChange(val chip: Chips) : CoinDetailAction
}