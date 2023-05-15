package com.jroslar.listafacturasjetpackcomposev21.di

import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas.FiltrarFacturasViewModel
import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas.ListaFacturasViewModel
import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.smartsolar.SmartSolarDetallesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::FiltrarFacturasViewModel)
    viewModelOf(::ListaFacturasViewModel)
    viewModelOf(::SmartSolarDetallesViewModel)
}