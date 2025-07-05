package com.simochan.cryptostats.crypto.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_coins")
data class FavoriteCoin(
    @PrimaryKey val id: String
)
