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

    fun filterListByCheckBox(value: List<String>) {
        if (!value.isNullOrEmpty()) { _state.value = _state.value?.filter { value.contains(it.descEstado) } }
    }

    fun filterListByImporte(value: Int) {
        _state.value = _state.value?.filter { it.importeOrdenacion < value }
    }

    fun filterlistByFechaDesde(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isBefore(it.fecha.castStringToDate())}
        }
    }

    fun filterlistByFechaHasta(value: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _state.value = _state.value?.filter { value.castStringToDate().isAfter(it.fecha.castStringToDate())}
        }
    }
}