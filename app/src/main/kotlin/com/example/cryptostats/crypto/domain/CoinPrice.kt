package com.example.cryptostats.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val priceUsd: Double,
    val time: ZonedDateTime
)
