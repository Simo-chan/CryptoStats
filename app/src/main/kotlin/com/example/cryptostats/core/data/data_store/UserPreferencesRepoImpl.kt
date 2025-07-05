package com.example.cryptostats.core.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.cryptostats.core.domain.UserPreferencesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepoImpl(private val context: Context) : UserPreferencesRepo {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    override suspend fun setNewTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_DARK_THEME_KEY] = isDarkTheme
        }
    }

    override val getCurrentTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_THEME_KEY] ?: true
    }

    private companion object {
        const val DATASTORE_NAME = "user_settings"
        val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    }
}
