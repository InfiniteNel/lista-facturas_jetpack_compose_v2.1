package com.jroslar.listafacturasjetpackcomposev21.domain

import com.jroslar.listafacturasjetpackcomposev21.data.FacturasRepository
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel

class GetFacturasLocalUseCase constructor(
    private val repository: FacturasRepository
) {
    suspend operator fun invoke(): List<FacturaModel> {
        val facturas = repository.getAllFacturasLocal()

        return facturas.ifEmpty { emptyList() }
    }
}