package com.amos_tech_code.travelapp.di

import com.amos_tech_code.travelapp.cache.TravelAppSession
import com.amos_tech_code.travelapp.cache.dataStoreImpl
import com.amos_tech_code.travelapp.data.NetworkService
import com.amos_tech_code.travelapp.ui.feature.login.LoginViewModel
import com.amos_tech_code.travelapp.ui.feature.register.RegisterViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object: Logger {
                    override fun log(message: String) {
                        println("Backend Handler: $message")
                    }
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000 // 30 seconds
                connectTimeoutMillis = 15_000 // 15 seconds
                socketTimeoutMillis = 30_000 // 30 seconds
            }

            engine {
                requestTimeout = 30_000 // Applies to CIO engine requests
            }

        }
    }


    single { NetworkService(get()) }

    single { dataStoreImpl(get()) }

    single { TravelAppSession(get()) }

    viewModel { RegisterViewModel(get(), get()) }

    viewModel { LoginViewModel(get(), get()) }

}