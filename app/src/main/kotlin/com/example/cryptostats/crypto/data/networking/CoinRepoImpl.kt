package com.example.cryptostats.crypto.data.networking

import com.example.cryptostats.core.data.networking.ktor.constructUrl
import com.example.cryptostats.core.data.networking.ktor.makeCall
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.core.domain.util.Result
import com.example.cryptostats.core.domain.util.map
import com.example.cryptostats.crypto.data.networking.dto.CoinListDto
import com.example.cryptostats.crypto.data.networking.dto.CoinPriceListDto
import com.example.cryptostats.crypto.data.networking.mapper.toCoin
import com.example.cryptostats.crypto.data.networking.mapper.toCoinPrice
import com.example.cryptostats.crypto.domain.Coin
import com.example.cryptostats.crypto.domain.CoinPrice
import com.example.cryptostats.crypto.domain.CoinRepo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class CoinRepoImpl(private val httpClient: HttpClient) : CoinRepo {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return makeCall<CoinListDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data
                .filter { it.changePercent24Hr != null && it.priceUsd != null && it.marketCapUsd != null }
                .map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = startTime
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = endTime
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return makeCall<CoinPriceListDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}