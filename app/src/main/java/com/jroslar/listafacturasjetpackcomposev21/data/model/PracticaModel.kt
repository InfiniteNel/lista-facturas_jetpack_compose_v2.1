package com.jroslar.listafacturasjetpackcomposev21.data.model

data class PracticaModel(
    val name: String,
    val tipo: PracticasName
)

enum class PracticasName {
    PRACTICA1,
    PRACTICA2
}