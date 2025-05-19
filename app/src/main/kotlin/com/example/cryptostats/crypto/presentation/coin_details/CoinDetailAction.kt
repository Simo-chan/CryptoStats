package com.example.cryptostats.crypto.presentation.coin_details

sealed interface CoinDetailAction {
    data object OnBackClick: CoinDetailAction
    data class OnFavoriteClick(val id: String): CoinDetailAction
}