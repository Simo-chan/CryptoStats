package com.example.cryptostats.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
class CoinListDto(
    val data: List<CoinDto>,
)