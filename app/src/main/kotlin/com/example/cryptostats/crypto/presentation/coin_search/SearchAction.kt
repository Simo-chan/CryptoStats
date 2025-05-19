package com.example.cryptostats.crypto.presentation.coin_search

sealed interface SearchAction {
    data class OnSearchQueryChange(val query: String) : SearchAction
    object OnBackClick : SearchAction
}