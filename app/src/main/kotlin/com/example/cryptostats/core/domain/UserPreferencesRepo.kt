package com.example.cryptostats.core.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepo {
    suspend fun setNewTheme(isDarkTheme: Boolean)
    val getCurrentTheme: Flow<Boolean>
}