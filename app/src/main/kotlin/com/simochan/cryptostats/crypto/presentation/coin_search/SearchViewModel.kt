package com.simochan.cryptostats.crypto.presentation.coin_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simochan.cryptostats.core.domain.util.onError
import com.simochan.cryptostats.core.domain.util.onSuccess
import com.simochan.cryptostats.crypto.domain.CoinRepo
import com.simochan.cryptostats.crypto.presentation.models.toCoinUI
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

class SearchViewModel(
    private val coinRepo: CoinRepo,
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        observeSearchQuery()
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }

            else -> Unit
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
                        _state.update {
                            it.copy(
                                searchError = null,
                                searchResults = emptyList(),
                                isSearchResultEmpty = false
                            )
                        }

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
                searchError = null,
                isSearchResultEmpty = false
            )
        }

        coinRepo
            .searchCoins(query)
            .onSuccess { coins ->
                if (coins.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            isSearching = false,
                            searchResults = coins.map { it.toCoinUI() }
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isSearching = false,
                            isSearchResultEmpty = true,
                            searchResults = emptyList()
                        )
                    }
                }

            }
            .onError { error ->
                _state.update {
                    it.copy(isSearching = false, searchError = error)
                }
            }
    }
}