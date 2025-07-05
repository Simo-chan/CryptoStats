package com.simochan.cryptostats.core.presentation

sealed interface ThemeAction {
    object OnSetNewTheme : ThemeAction
}