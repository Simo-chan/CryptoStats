package com.example.cryptostats.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptostats.core.domain.util.onError
import com.example.cryptostats.core.domain.util.onSuccess
import com.example.cryptostats.crypto.domain.CoinRepo
import com.example.cryptostats.crypto.presentation.models.toCoinUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class CoinListViewModel(
    private val coinRepo: CoinRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    init {
        getCoins()
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> TODO()
            is CoinListAction.OnRefresh -> {
                getCoins()
            }
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

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
                        it.copy(isLoading = false)
                    }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}