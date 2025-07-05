package com.simochan.cryptostats.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simochan.cryptostats.core.domain.util.onError
import com.simochan.cryptostats.core.domain.util.onSuccess
import com.simochan.cryptostats.crypto.domain.CoinRepo
import com.simochan.cryptostats.crypto.presentation.models.toCoinUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinRepo: CoinRepo,
) : ViewModel() {

    private var observeFavoriteCoinsJob: Job? = null

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    init {
        getCoins()
        observeFavoriteCoins()
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnRefresh -> {
                getCoins()
            }

            else -> Unit
        }
    }

    private fun getCoins() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        delay(200)

        coinRepo
            .getCoins()
            .onSuccess { coins ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        coins = coins.map { it.toCoinUI() }
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(isLoading = false, errorMessage = error)
                }
            }
    }

    private fun observeFavoriteCoins() {
        observeFavoriteCoinsJob?.cancel()
        observeFavoriteCoinsJob =
            combine(state, coinRepo.getFavoriteCoins()) { coinListState, favoriteCoinsIds ->
                val allCoins = coinListState.coins
                val favoriteCoins = allCoins.filter { coin ->
                    favoriteCoinsIds.any { favoriteId ->
                        coin.id == favoriteId
                    }
                }
                _state.update { it.copy(favoriteCoins = favoriteCoins) }
            }.launchIn(viewModelScope)
    }
}
