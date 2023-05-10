package com.jroslar.listafacturasjetpackcomposev21.data.network

import co.infinum.retromock.Retromock
import com.jroslar.listafacturasjetpackcomposev21.data.network.retrofit.FacturasApiClient
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.URL_SERVIDOR_FACTURAS
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.StateServer
import com.jroslar.listafacturasjetpackcomposev21.data.network.model.FacturasResponse
import io.ktor.client.*
import io.ktor.client.request.*
import retrofit2.Retrofit

class FacturasService constructor(private val retrofit: Retrofit, private val retromock: Retromock, private val client: HttpClient) {
    companion object {
        var stateServer: StateServer = StateServer.Retrofit
    }

    suspend fun getFacturas(): FacturasResponse {
        return when(stateServer) {
            StateServer.Retrofit -> {
                val response = retrofit.create(FacturasApiClient::class.java).getAllFacturas()
                response.body()?: noData()
            }
            StateServer.Ktor -> {
                try {
                    client.get { url(URL_SERVIDOR_FACTURAS)}
                } catch (e: Exception) {
                    noData()
                }
            }
            else -> {
                val response = retromock.create(FacturasApiClient::class.java).getAllFacturas()
                response.body()?: noData()
            }
        }
    }

    private fun noData(): FacturasResponse {
        return FacturasResponse(0, emptyList())
    }
}