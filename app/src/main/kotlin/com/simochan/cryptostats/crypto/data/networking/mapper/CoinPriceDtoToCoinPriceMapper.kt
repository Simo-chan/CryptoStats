package com.simochan.cryptostats.crypto.data.networking.mapper

import com.simochan.cryptostats.crypto.data.networking.dto.CoinPriceDto
import com.simochan.cryptostats.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        time = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}