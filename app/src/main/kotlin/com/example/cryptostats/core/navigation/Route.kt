package com.example.cryptostats.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object CoinGraph : Route

    @Serializable
    data object CoinList : Route

    @Serializable
    data object CoinSearch : Route

    @Serializable
    data class CoinDetails(val id: String) : Route
}