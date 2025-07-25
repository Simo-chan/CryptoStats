package com.simochan.cryptostats.crypto.data.networking.mapper

import com.simochan.cryptostats.crypto.data.networking.dto.CoinDto
import com.simochan.cryptostats.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}