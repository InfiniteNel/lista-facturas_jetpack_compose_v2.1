package com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas

import android.content.Context
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FiltrarFacturasViewModel constructor(private val context: Context): ViewModel() {
    var _state by mutableStateOf<List<FacturaModel>>(emptyList())
    var _filtroEstadoPagada by mutableStateOf(false)
    var _filtroEstadoPedienteDePago by mutableStateOf(false)
    var _filtroEstadoAnuladas by mutableStateOf(false)
    var _filtroEstadoCuotaFija by mutableStateOf(false)
    var _filtroEstadoPlanDePago by mutableStateOf(false)
    var _filtroImporte by mutableStateOf(0F)
    var _filtroImporteMaxValue by mutableStateOf(0)
    var _filtroFechaDesde by mutableStateOf(R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context))
    var _filtroFechaHasta by mutableStateOf(R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context))

    private object Injection: KoinComponent {
        val getFacturasLocalUseCase by inject<GetFacturasLocalUseCase>()
    }
    private val getFacturasLocalUseCase = Injection.getFacturasLocalUseCase

    fun getList() {
        viewModelScope.launch {
            val data: List<FacturaModel> = getFacturasLocalUseCase()
            if (data.isNotEmpty()) {
                _state = data
            }
        }
    }

    private fun filterListByCheckBox(value: List<String>) {
        if (value.isNotEmpty()) { _state = _state.filter { value.contains(it.descEstado) } }
    }

    private fun filterListByImporte(value: Int) {
        _state = _state.filter { it.importeOrdenacion < value }
    }

    private fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state = _state.filter { value.castStringToDate().isBefore(it.fecha.castStringToDate())}
        }
    }

    private fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state = _state.filter { value.castStringToDate().isAfter(it.fecha.castStringToDate())}
        }
    }

    private fun comprobarFechas() {
        var text = _filtroFechaDesde
        val regex = "\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex()
        if (regex.matches(text)) {
            filterlistByFechaDesde(text)
        }
        text = _filtroFechaHasta
        if (regex.matches(text)) {
            filterlistByFechaHasta(text)
        }
    }

    private fun comprobarCheckBox(): List<String> {
        val checks = mutableListOf<String>()
        if (_filtroEstadoPagada) {
            checks.add(Constantes.Companion.DescEstado.Pagada.descEstado)
        }
        if (_filtroEstadoAnuladas) {
            checks.add(Constantes.Companion.DescEstado.Anuladas.descEstado)
        }
        if (_filtroEstadoCuotaFija) {
            checks.add(Constantes.Companion.DescEstado.CuotaFija.descEstado)
        }
        if (_filtroEstadoPlanDePago) {
            checks.add(Constantes.Companion.DescEstado.PlanDePago.descEstado)
        }
        if (_filtroEstadoPedienteDePago) {
            checks.add(Constantes.Companion.DescEstado.PedienteDePago.descEstado)
        }

        return checks
    }

    fun aplicarFiltros() {
        filterListByCheckBox(comprobarCheckBox())
        filterListByImporte(_filtroImporte.toInt())
        comprobarFechas()
    }

    fun eliminarFiltros() {
        _filtroEstadoPagada = false
        _filtroEstadoPedienteDePago = false
        _filtroEstadoAnuladas = false
        _filtroEstadoCuotaFija = false
        _filtroEstadoPlanDePago = false
        _filtroImporte = _filtroImporteMaxValue.toFloat()
        _filtroFechaDesde = R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context)
        _filtroFechaHasta = R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context)
    }
}