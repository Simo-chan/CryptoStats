package com.example.cryptostats.crypto.data.networking

import com.example.cryptostats.core.data.networking.constructUrl
import com.example.cryptostats.core.data.networking.makeCall
import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.core.domain.util.Result
import com.example.cryptostats.core.domain.util.map
import com.example.cryptostats.crypto.data.networking.dto.CoinListDto
import com.example.cryptostats.crypto.data.networking.mapper.toCoin
import com.example.cryptostats.crypto.domain.Coin
import com.example.cryptostats.crypto.domain.CoinRepo
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CoinRepoImpl(private val httpClient: HttpClient) : CoinRepo {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return makeCall<CoinListDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}