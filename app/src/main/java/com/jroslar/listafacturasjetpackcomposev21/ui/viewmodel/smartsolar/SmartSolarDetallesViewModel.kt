package com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.smartsolar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasjetpackcomposev21.data.model.DetallesModel
import com.jroslar.listafacturasjetpackcomposev21.domain.GetDetallesFromApiUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SmartSolarDetallesViewModel : ViewModel() {
    var _data by mutableStateOf(DetallesModel("","","","",""))
    var _state by mutableStateOf(SmartSolarDetallesResult.LOADING)

    private object Injection: KoinComponent {
        val getDetallesFromApiUseCase by inject<GetDetallesFromApiUseCase>()
    }
    private val getDetallesFromApiUseCase = Injection.getDetallesFromApiUseCase

    init {
        viewModelScope.launch {
            _state = (SmartSolarDetallesResult.LOADING)
            val data = getDetallesFromApiUseCase()
            _data = data
            _state = (SmartSolarDetallesResult.DATA)
        }
    }

    enum class SmartSolarDetallesResult {
        LOADING,
        DATA
    }
}