package com.jroslar.listafacturasjetpackcomposev21.data.network.model

import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import kotlinx.serialization.Serializable

@Serializable
data class FacturaResponse (
    val descEstado: String,
    val importeOrdenacion: Float,
    val fecha: String
)

fun FacturaResponse.toModelFactura(): FacturaModel = FacturaModel(descEstado, importeOrdenacion, fecha)