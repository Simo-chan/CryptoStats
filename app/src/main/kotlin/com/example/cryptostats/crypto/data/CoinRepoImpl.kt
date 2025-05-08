package com.example.cryptostats.crypto.data

import com.example.cryptostats.core.data.data_store.DataStore
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
import kotlinx.coroutines.flow.Flow
import java.time.ZoneId
import java.time.ZonedDateTime

class CoinRepoImpl(
    private val httpClient: HttpClient,
    private val dataStore: DataStore,
) : CoinRepo {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return makeCall<CoinListDto> {
            httpClient.get(
                urlString = constructUrl(ALL_COINS_ENDPOINT)
            )
        }.map { response ->
            response.data
                .filter { it.changePercent24Hr != null && it.priceUsd != null && it.marketCapUsd != null }
                .map { it.toCoin() }
        }
    }

    override suspend fun searchCoins(query: String): Result<List<Coin>, NetworkError> {
        return makeCall<CoinListDto> {
            httpClient.get(
                urlString = constructUrl(ALL_COINS_ENDPOINT)
            ) {
                parameter("search", query)
                parameter("limit", SEARCH_RESULT_LIMIT)
            }
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
            .withZoneSameInstant(ZoneId.of(TIME_ZONE))
            .toInstant()
            .toEpochMilli()
        val endMillis = endTime
            .withZoneSameInstant(ZoneId.of(TIME_ZONE))
            .toInstant()
            .toEpochMilli()

        return makeCall<CoinPriceListDto> {
            httpClient.get(
                urlString = constructUrl("$ALL_COINS_ENDPOINT/$coinId/$PRICE_HISTORY_ENDPOINT")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }

    override suspend fun saveCurrentTheme(isDarkTheme: Boolean) =
        dataStore.saveCurrentTheme(isDarkTheme)

    override fun getCurrentTheme(): Flow<Boolean> = dataStore.currentTheme

    companion object {
        private const val ALL_COINS_ENDPOINT = "/assets"
        private const val TIME_ZONE = "UTC"
        private const val PRICE_HISTORY_ENDPOINT = "/history"
        private const val SEARCH_RESULT_LIMIT = 6
    }
}