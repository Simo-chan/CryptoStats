package com.example.cryptostats.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptostats.core.domain.util.onError
import com.example.cryptostats.core.domain.util.onSuccess
import com.example.cryptostats.crypto.domain.CoinRepo
import com.example.cryptostats.crypto.presentation.coin_details.custom_graph.DataPoint
import com.example.cryptostats.crypto.presentation.models.CoinUI
import com.example.cryptostats.crypto.presentation.models.toCoinUI
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinRepo: CoinRepo,
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    init {
        getCoins()
        observeSearchQuery()
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUI)
            }

            is CoinListAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }

            is CoinListAction.OnRefresh -> {
                getCoins()
            }
        }
    }

    private fun selectCoin(coinUI: CoinUI) = viewModelScope.launch {
        _state.update { it.copy(selectedCoin = coinUI, errorMessage = null) }
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
            .onError { error -> _state.update { it.copy(errorMessage = error) } }
    }


    private fun getCoins() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
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
                    it.copy(isLoading = false, errorMessage = error)
                }
            }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() ->
                        _state.update { it.copy(searchError = null, searchResults = emptyList()) }

                    query.isNotEmpty() -> {
                        searchJob?.cancel()
                        searchJob = searchCoins(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchCoins(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isSearching = true,
                searchError = null
            )
        }

        coinRepo
            .searchCoins(query)
            .onSuccess { coins ->
                _state.update {
                    it.copy(
                        isSearching = false,
                        searchResults = coins.map { it.toCoinUI() }
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(isSearching = false, searchError = error)
                }
            }
    }
}
