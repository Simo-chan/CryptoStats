package com.example.cryptostats.crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCoin::class],
    version = 1,
    exportSchema = false
)
abstract class CoinDB : RoomDatabase() {
    abstract fun getCoinDao(): CoinDao
}