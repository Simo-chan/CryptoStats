package com.example.cryptostats.core.presentation

import com.example.cryptostats.crypto.presentation.coin_list.CoinListAction

sealed interface ThemeAction {
    object OnSetNewTheme : ThemeAction
}