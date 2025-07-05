package com.simochan.cryptostats.core.data.networking.ktor

import com.simochan.cryptostats.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        val apiKey = BuildConfig.API_KEY
        return HttpClient(engine) {
            install(Logging.Companion) {
                level = LogLevel.ALL
                logger = Logger.Companion.ANDROID
            }
            install(ContentNegotiation.Plugin) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
            }
        }
    }
}