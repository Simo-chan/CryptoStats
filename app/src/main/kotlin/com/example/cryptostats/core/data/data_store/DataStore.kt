package com.example.cryptostats.core.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    suspend fun saveCurrentTheme(isDarkTheme: Boolean = true) {
        context.dataStore.edit { settings ->
            settings[IS_DARK_THEME_KEY] = isDarkTheme
        }
    }

    val currentTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_THEME_KEY] == true
    }

    private companion object {
        const val DATASTORE_NAME = "user_settings"
        val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    }
}
