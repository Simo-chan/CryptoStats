package com.example.cryptostats.crypto.domain

import com.example.cryptostats.core.domain.util.NetworkError
import com.example.cryptostats.core.domain.util.Result

interface CoinRepo {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}