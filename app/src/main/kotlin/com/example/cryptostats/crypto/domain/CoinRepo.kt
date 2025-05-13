package com.example.cryptostats.crypto.domain

import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface CoinRepo {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun searchCoins(query: String): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError>

    suspend fun saveFavoriteCoin(id: String)
    fun getFavoriteCoins(): Flow<List<String>>
    fun isCoinFavorite(id: String): Flow<Boolean>
    suspend fun deleteFavoriteCoin(id: String)
}