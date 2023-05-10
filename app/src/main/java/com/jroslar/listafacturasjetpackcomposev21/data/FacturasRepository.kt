package com.jroslar.listafacturasjetpackcomposev21.data

import android.content.Context
import com.jroslar.listafacturasjetpackcomposev21.data.database.FacturasDatabase
import com.jroslar.listafacturasjetpackcomposev21.data.database.model.toModelFactura
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.data.model.toFacturaEntity
import com.jroslar.listafacturasjetpackcomposev21.data.network.FacturasService
import com.jroslar.listafacturasjetpackcomposev21.data.network.model.toModelFactura
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FacturasRepository constructor(private val facturasService: FacturasService, private val context: Context) {
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    suspend fun getAllFacturasFromApi(): List<FacturaModel> {
        return withContext(dispatcherIO) {
            val result = facturasService.getFacturas()

            result.facturas.map { it.toModelFactura() }
        }
    }

    suspend fun clearFacturas() {
        return withContext(dispatcherIO) {
            FacturasDatabase.getInstance(context).facturaDao().clearDataBase()
        }
    }

    suspend fun insertFacturas(facturas: List<FacturaModel>) {
        return withContext(dispatcherIO) {
            FacturasDatabase.getInstance(context).facturaDao().insertFacturas(facturas.map { it.toFacturaEntity() })
        }
    }

    suspend fun getAllFacturasLocal(): List<FacturaModel> {
        return  withContext(dispatcherIO) {
            FacturasDatabase.getInstance(context).facturaDao().getFacturas().map { it.toModelFactura() }
        }
    }
}