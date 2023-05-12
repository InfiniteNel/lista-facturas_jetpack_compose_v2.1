package com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.domain.GetFacturasLocalUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FiltrarFacturasViewModel : ViewModel() {
    var _state: MutableLiveData<List<FacturaModel>> = MutableLiveData()
    var _filtroEstado: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    var _filtroImporte: MutableLiveData<Int> = MutableLiveData()
    var _filtroFechaDesde: MutableLiveData<String> = MutableLiveData()
    var _filtroFechaHasta: MutableLiveData<String> = MutableLiveData()

    private object Injection: KoinComponent {
        val getFacturasLocalUseCase by inject<GetFacturasLocalUseCase>()
    }
    private val getFacturasLocalUseCase = Injection.getFacturasLocalUseCase

    fun getList() {
        viewModelScope.launch {
            val data: List<FacturaModel> = getFacturasLocalUseCase()
            if (data.isNullOrEmpty()) {
                _state.value = emptyList()
            } else {
                _state.value = data
            }
        }
    }

    private fun filterListByCheckBox(value: List<String>) {
        if (!value.isNullOrEmpty()) { _state.value = _state.value?.filter { value.contains(it.descEstado) } }
    }

    private fun filterListByImporte(value: Int) {
        _state.value = _state.value?.filter { it.importeOrdenacion < value }
    }

    private fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isBefore(it.fecha.castStringToDate())}
        }
    }

    private fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isAfter(it.fecha.castStringToDate())}
        }
    }

    private fun comprobarFechas() {
        var text = _filtroFechaDesde.value
        val regex = "\\d{1,2} [A-Z a-z]{3} \\d{4}".toRegex()
        if (text != null && regex.matches(text)) {
            filterlistByFechaDesde(text.toString())
        }
        text = _filtroFechaHasta.value
        if (text != null && regex.matches(text)) {
            filterlistByFechaHasta(text.toString())
        }
    }

    fun aplicarFiltros() {
        filterListByCheckBox(_filtroEstado.value!!)
        filterListByImporte(_filtroImporte.value!!)
        comprobarFechas()
    }
}