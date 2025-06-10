package com.example.cryptostats.crypto.presentation.coin_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cryptostats.core.domain.util.onError
import com.example.cryptostats.core.domain.util.onSuccess
import com.example.cryptostats.core.navigation.Route
import com.example.cryptostats.crypto.domain.CoinRepo
import com.example.cryptostats.crypto.presentation.coin_details.custom_graph.DataPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinDetailViewModel(
    private val coinRepo: CoinRepo,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val coinId = savedStateHandle.toRoute<Route.CoinDetails>().id

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    init {
        observeFavoriteStatus()
        getPriceHistory()
    }

    fun onAction(action: CoinDetailAction) {
        when (action) {
            is CoinDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        coinRepo.deleteFavoriteCoin(id = coinId)
                    } else {
                        coinRepo.saveFavoriteCoin(id = coinId)
                    }
                }
            }

            is CoinDetailAction.OnSelectedCoinChange -> {
                if (state.value.coin?.coinPriceHistory == null)
                _state.update { it.copy(coin = action.coin) }
            }

            is CoinDetailAction.OnChipSelectionChange -> {
                if (state.value.selectedChip != action.chip) {
                    _state.update { it.copy(selectedChip = action.chip) }
                    getPriceHistory()
                }
            }

            is CoinDetailAction.OnRefresh -> {
                getPriceHistory()
            }

            else -> Unit
        }
    }

    private fun getPriceHistory() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        delay(200)
        val interval = state.value.selectedChip.value
        coinRepo
            .getCoinHistory(
                coinId,
                startTime = ZonedDateTime.now().minusDays(10),
                endTime = ZonedDateTime.now(),
                interval = interval
            )
            .onSuccess { history ->
                val dataPoints = history
                    .sortedBy { it.time }
                    .map {
                        DataPoint(
                            x = it.time.hour.toFloat(),
                            y = it.priceUsd.toFloat(),
                            xLabel = DateTimeFormatter
                                .ofPattern("kk:mm\nd/M")
                                .format(it.time)
                        )
                    }

                _state.update {
                    it.copy(
                        coin = it.coin?.copy(
                            coinPriceHistory = dataPoints
                        ),
                        isLoading = false
                    )
                }
            }
            .onError { error -> _state.update { it.copy(isLoading = false, errorMessage = error) } }
    }

    private fun observeFavoriteStatus() {
        coinRepo.isCoinFavorite(coinId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}