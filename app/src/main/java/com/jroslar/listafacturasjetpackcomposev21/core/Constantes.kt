package com.jroslar.listafacturasjetpackcomposev21.core

class Constantes {
    companion object {
        const val MAIN_FACTURAS = "mainFacturas"
        enum class DescEstado(val descEstado: String) {
            PedienteDePago("Pendiente de pago"),
            Pagada("Pagada"),
            Anuladas("Anuladas"),
            CuotaFija("Cuota Fija"),
            PlanDePago("Plan de pago")
        }
    }
}