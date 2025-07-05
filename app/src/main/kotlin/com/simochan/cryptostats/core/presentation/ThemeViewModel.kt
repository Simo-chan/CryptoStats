package com.simochan.cryptostats.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simochan.cryptostats.core.domain.UserPreferencesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val dataStore: UserPreferencesRepo,
) : ViewModel() {

    private val _themeState = MutableStateFlow(ThemeState())
    val themeState = _themeState.asStateFlow()

    init {
        getCurrentTheme()
    }

    fun onAction(action: ThemeAction) {
        when (action) {
            is ThemeAction.OnSetNewTheme -> {
                setNewTheme()
            }
        }
    }

    private fun setNewTheme() = viewModelScope.launch {
        val currentState = themeState.value
        if (!currentState.isLoading) {
            dataStore.setNewTheme(isDarkTheme = !currentState.isDarkTheme)
        }
    }

    private fun getCurrentTheme() = viewModelScope.launch {
        _themeState.update { it.copy(isLoading = true) }

        dataStore.getCurrentTheme.collectLatest { theme ->
            _themeState.update { it.copy(isLoading = false, isDarkTheme = theme) }
        }
    }
}