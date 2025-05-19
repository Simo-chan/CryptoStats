package com.example.cryptostats.crypto.presentation.coin_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptostats.crypto.domain.CoinRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val coinRepo: CoinRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: CoinDetailAction) {
        when (action) {
            is CoinDetailAction.OnBackClick -> {}

            is CoinDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        coinRepo.deleteFavoriteCoin(id =TODO())
                    } else {
                        coinRepo.saveFavoriteCoin(id = TODO())
                    }
                }
            }
        }
    }
}