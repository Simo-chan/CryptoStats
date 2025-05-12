package com.example.cryptostats.crypto.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CoinDao {

    @Upsert
    suspend fun saveFavoriteCoin(favoriteCoin: FavoriteCoin)

    @Query("SELECT EXISTS(SELECT * FROM favorite_coins WHERE id =:id)")
    suspend fun isCoinFavorite(id: String): Boolean

    @Query("DELETE FROM favorite_coins WHERE id =:id")
    suspend fun deleteFavoriteCoin(id: String)
}