package com.example.cryptostats.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptostats.core.domain.util.onError
import com.example.cryptostats.core.domain.util.onSuccess
import com.example.cryptostats.crypto.domain.CoinRepo
import com.example.cryptostats.crypto.presentation.coin_details.custom_graph.DataPoint
import com.example.cryptostats.crypto.presentation.models.CoinUI
import com.example.cryptostats.crypto.presentation.models.toCoinUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinRepo: CoinRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    init {
        getCoins()
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUI)
            }

            is CoinListAction.OnRefresh -> {
                getCoins()
            }
        }
    }

    private fun selectCoin(coinUI: CoinUI) = viewModelScope.launch {
        _state.update { it.copy(selectedCoin = coinUI, isError = false) }
        coinRepo
            .getCoinHistory(
                coinUI.id,
                startTime = ZonedDateTime.now().minusDays(5),
                endTime = ZonedDateTime.now()
            )
            .onSuccess { history ->
                val dataPoints = history
                    .sortedBy { it.time }
                    .map {
                        DataPoint(
                            x = it.time.hour.toFloat(),
                            y = it.priceUsd.toFloat(),
                            xLabel = DateTimeFormatter
                                .ofPattern("ha\nM/d")
                                .format(it.time)
                        )
                    }

                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = dataPoints
                        )
                    )
                }
            }
            .onError { error -> _state.update { it.copy(isError = true, errorMessage = error) } }
    }


    private fun getCoins() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                isError = false
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
                    it.copy(isLoading = false, isError = true, errorMessage = error )
                }
            }
    }
}
