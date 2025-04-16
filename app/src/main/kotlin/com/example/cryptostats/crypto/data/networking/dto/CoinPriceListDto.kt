package com.example.cryptostats.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceListDto (
    val data: List<CoinPriceDto>
)