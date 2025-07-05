package com.simochan.cryptostats.crypto.presentation

import androidx.lifecycle.ViewModel
import com.simochan.cryptostats.crypto.presentation.models.CoinUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedCoinViewModel : ViewModel() {

    private val _selectedCoin = MutableStateFlow<CoinUI?>(null)
    val selectedCoin = _selectedCoin.asStateFlow()

    fun onSelectCoin(coin: CoinUI?) {
        _selectedCoin.value = coin
    }
}