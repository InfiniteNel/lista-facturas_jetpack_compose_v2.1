package com.jroslar.listafacturasjetpackcomposev21.domain

import com.jroslar.listafacturasjetpackcomposev21.data.DetallesRepository
import com.jroslar.listafacturasjetpackcomposev21.data.model.DetallesModel

class GetDetallesFromApiUseCase constructor(
    private val repository: DetallesRepository
) {
    suspend operator fun invoke(): DetallesModel {
        return repository.getDetallesFromApi()
    }
}