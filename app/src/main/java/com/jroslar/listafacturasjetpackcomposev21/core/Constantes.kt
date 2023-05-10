package com.jroslar.listafacturasjetpackcomposev21.core

class Constantes {
    companion object {
        const val URL_SERVIDOR_FACTURAS = "https://viewnextandroid2.wiremockapi.cloud/"

        const val URL_OBJECT_FACTURAS = "facturas"

        const val URL_SERVIDOR_DETALLES = "https://viewnextandroid2.wiremockapi.cloud/"

        const val URL_OBJECT_DETALLES = "facturas"

        const val MAIN_FACTURAS = "mainFacturas"
        enum class DescEstado(val descEstado: String) {
            PedienteDePago("Pendiente de pago"),
            Pagada("Pagada"),
            Anuladas("Anuladas"),
            CuotaFija("Cuota Fija"),
            PlanDePago("Plan de pago")
        }

        enum class StateServer {
            Retrofit,
            Retromock,
            Ktor
        }
    }
}