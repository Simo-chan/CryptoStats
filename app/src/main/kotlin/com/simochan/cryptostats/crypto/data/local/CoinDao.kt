package com.simochan.cryptostats.crypto.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Upsert
    suspend fun saveFavoriteCoin(favoriteCoin: FavoriteCoin)

    @Query("SELECT * FROM favorite_coins")
    fun getFavoriteCoins(): Flow<List<FavoriteCoin>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_coins WHERE id =:id)")
    fun isCoinFavorite(id: String): Flow<Boolean>

    @Query("DELETE FROM favorite_coins WHERE id =:id")
    suspend fun deleteFavoriteCoin(id: String)
}