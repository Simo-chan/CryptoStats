package com.example.cryptostats.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.example.cryptostats.crypto.domain.Coin
import com.example.cryptostats.core.presentation.util.getDrawableIdForCoin
import com.example.cryptostats.crypto.presentation.coin_details.DataPoint
import java.text.NumberFormat
import java.util.Locale

data class CoinUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList()
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String,
)

fun Coin.toCoinUI(): CoinUI {
    return CoinUI(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd!!.toDisplayableNumber(),
        priceUsd = priceUsd!!.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr!!.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}
