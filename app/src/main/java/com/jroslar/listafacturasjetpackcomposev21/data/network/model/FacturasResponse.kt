package com.jroslar.listafacturasjetpackcomposev21.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class FacturasResponse (
    val numFacturas: Int,
    val facturas: List<FacturaResponse>
)