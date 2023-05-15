package com.jroslar.listafacturasjetpackcomposev21.data.network.model

import com.jroslar.listafacturasjetpackcomposev21.data.model.DetallesModel
import kotlinx.serialization.Serializable

@Serializable
data class DetallesResponse(
    val cau:String,
    val estadoSolicitudAutoConsumidor:String,
    val tipoAutoConsumo:String,
    val compensacionExcedentes:String,
    val potenciaInstalacion:String
)

fun DetallesResponse.toModelDetalles(): DetallesModel = DetallesModel(cau, estadoSolicitudAutoConsumidor, tipoAutoConsumo, compensacionExcedentes, potenciaInstalacion)
