package com.example.cryptostats.di

import androidx.room.Room
import com.example.cryptostats.core.data.data_store.UserPreferencesRepoImpl
import com.example.cryptostats.core.data.networking.ktor.HttpClientFactory
import com.example.cryptostats.core.domain.UserPreferencesRepo
import com.example.cryptostats.core.presentation.ThemeViewModel
import com.example.cryptostats.crypto.data.CoinRepoImpl
import com.example.cryptostats.crypto.data.local.CoinDB
import com.example.cryptostats.crypto.domain.CoinRepo
import com.example.cryptostats.crypto.presentation.SelectedCoinViewModel
import com.example.cryptostats.crypto.presentation.coin_details.CoinDetailViewModel
import com.example.cryptostats.crypto.presentation.coin_list.CoinListViewModel
import com.example.cryptostats.crypto.presentation.coin_search.SearchViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { Room.databaseBuilder(androidContext(), CoinDB::class.java, "coin_db").build() }
    single { get<CoinDB>().getCoinDao() }

    singleOf(::UserPreferencesRepoImpl).bind<UserPreferencesRepo>()
    singleOf(::CoinRepoImpl).bind<CoinRepo>()

    viewModelOf(::CoinListViewModel)
    viewModelOf(::CoinDetailViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::SelectedCoinViewModel)
}