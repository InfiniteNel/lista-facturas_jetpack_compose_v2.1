package com.jroslar.listafacturasjetpackcomposev21.di

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.URL_SERVIDOR_DETALLES
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.URL_SERVIDOR_FACTURAS
import com.jroslar.listafacturasjetpackcomposev21.data.FacturasRepository
import com.jroslar.listafacturasjetpackcomposev21.domain.GetFacturasFromApiUseCase
import com.jroslar.listafacturasjetpackcomposev21.domain.GetFacturasLocalUseCase
import com.jroslar.listafacturasjetpackcomposev21.data.network.FacturasService
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single(named(Qualifier.FacturasRetrofit)) {
        Retrofit.Builder()
        .baseUrl(URL_SERVIDOR_FACTURAS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    single(named(Qualifier.DetallesRetrofit)) {
        Retrofit.Builder()
            .baseUrl(URL_SERVIDOR_DETALLES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single(named(Qualifier.FacturasRetromock)) {
        Retromock.Builder()
            .retrofit(get(named(Qualifier.FacturasRetrofit)))
            .build()
    }
    single(named(Qualifier.DetallesRetromock)) {
        Retromock.Builder()
            .retrofit(get(named(Qualifier.DetallesRetrofit)))
            .build()
    }
    single {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                serializer = KotlinxSerializer(json)
                acceptContentTypes = acceptContentTypes + ContentType.Any
            }
        }
    }

    factory { FacturasService(get(named(Qualifier.FacturasRetrofit)), get(named(Qualifier.FacturasRetromock)), get()) }
    factoryOf(::FacturasRepository)

    factoryOf(::GetFacturasFromApiUseCase)
    factoryOf(::GetFacturasLocalUseCase)
}

enum class Qualifier {
    FacturasRetrofit,
    DetallesRetrofit,
    DetallesRetromock,
    FacturasRetromock
}