package com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.domain.GetFacturasFromApiUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListaFacturasViewModel constructor(private val context: Context): ViewModel() {
    var _data by mutableStateOf(listOf<FacturaModel>())
    var _state by mutableStateOf(ListaFacturasResult.LOADING)
    var _maxValueImporte: MutableLiveData<Float> = MutableLiveData()

    private object Injection: KoinComponent {
        val getFacturasUseCase by inject<GetFacturasFromApiUseCase>()
    }
    private val getFacturasUseCase = Injection.getFacturasUseCase

    init {
        loadingData()
    }

    private fun loadingData() {
        viewModelScope.launch {
            _state = ListaFacturasResult.LOADING
            _data = emptyList()
            if (checkForInternet(context)) {
                val data: List<FacturaModel> = getFacturasUseCase()
                if (data.isNullOrEmpty()) {
                    _state = ListaFacturasResult.NO_DATA
                    _maxValueImporte.postValue(0F)
                } else {
                    _data = data
                    _state = ListaFacturasResult.DATA
                    _maxValueImporte.postValue(data.sortedBy { it.importeOrdenacion }[data.size - 1].importeOrdenacion)
                }
            } else _state = ListaFacturasResult.NO_DATA
        }
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun getList(data: List<FacturaModel>) {
        _state = ListaFacturasResult.LOADING
        if (data.isNullOrEmpty()) {
            _data = emptyList()
            _state = ListaFacturasResult.NO_DATA
        } else {
            _data = data
            _state = ListaFacturasResult.DATA
        }
    }

    enum class ListaFacturasResult {
        LOADING,
        NO_DATA,
        DATA
    }
}