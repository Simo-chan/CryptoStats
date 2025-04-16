package com.example.cryptostats.di

import com.example.cryptostats.core.data.data_store.DataStore
import com.example.cryptostats.core.data.networking.ktor.HttpClientFactory
import com.example.cryptostats.crypto.data.CoinRepoImpl
import com.example.cryptostats.crypto.domain.CoinRepo
import com.example.cryptostats.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { DataStore(androidContext()) }
    singleOf(::CoinRepoImpl).bind<CoinRepo>()

    viewModelOf(::CoinListViewModel)
}