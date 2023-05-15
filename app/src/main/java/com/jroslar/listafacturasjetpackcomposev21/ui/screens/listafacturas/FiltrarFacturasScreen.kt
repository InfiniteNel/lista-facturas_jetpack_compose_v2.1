package com.jroslar.listafacturasjetpackcomposev21.ui.screens.listafacturas

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.castStringToDate
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextDialogFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.titleFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.view.listafacturas.NavArgs
import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas.FiltrarFacturasViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FiltrarFacturasScreen(
    onCloseClick: () -> Unit,
    onAplicarClick: (List<FacturaModel>) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = { Toolbar(onCloseClick) },
        content = { Content(onAplicarClick, navController) }
    )
}

//@Preview
@Composable
private fun Toolbar(onCloseClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "") },
        backgroundColor = Color.White,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = {
                onCloseClick()
            }) {
                Icon(painterResource(id = R.drawable.close_icon), "")
            }
        }
    )
}

@Composable
private fun Content(onAplicarClick: (List<FacturaModel>) -> Unit, navController: NavHostController, viewModel: FiltrarFacturasViewModel = koinViewModel()) {
    viewModel.getList()

    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (lazyColumFiltro, columFiltro) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .constrainAs(lazyColumFiltro) {
                    top.linkTo(parent.top)
                    bottom.linkTo(columFiltro.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(20.dp)
        ) {
            item {
                Text(
                    text = R.string.tvTitleFiltrarFacturaText.getResourceStringAndroid(
                        context
                    ),
                    style = titleFragment
                )
                FiltrarPorFecha(viewModel)
                FiltrarPorImporte(viewModel, navController)
                FiltrarPorEstado(viewModel)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(columFiltro) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.White)
        ) {
            Button(
                onClick = {
                    viewModel.aplicarFiltros()
                    onAplicarClick(viewModel._state.value?: emptyList())
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp, top = 10.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = R.string.btAplicarFiltroFiltrarFacturaText.getResourceStringAndroid(context),
                    style = normalTextDialogFragment,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 80.dp, vertical = 5.dp)
                )
            }
            Button(
                onClick = {  },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    focusedElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = R.string.btEliminarFiltroFiltrarFacturaText.getResourceStringAndroid(context),
                    style = normalTextDialogFragment,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(horizontal = 80.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun FiltrarPorEstado(viewModel: FiltrarFacturasViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = R.string.tvTitleEstadoFiltrarFacturaText.getResourceStringAndroid(context),
            style = normalTextFragment,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
        CheckBoxStander(
            R.string.chPagadoFiltrarFacturaText.getResourceStringAndroid(context),
            Constantes.Companion.DescEstado.Pagada,
            viewModel
        )
        CheckBoxStander(
            R.string.chAnuladasFiltrarFacturaText.getResourceStringAndroid(context),
            Constantes.Companion.DescEstado.Anuladas,
            viewModel
        )
        CheckBoxStander(
            R.string.chCuotaFijaFiltrarFacturaText.getResourceStringAndroid(context),
            Constantes.Companion.DescEstado.CuotaFija,
            viewModel
        )
        CheckBoxStander(
            R.string.chPedientesDePagoFiltrarFacturaText.getResourceStringAndroid(context),
            Constantes.Companion.DescEstado.PedienteDePago,
            viewModel
        )
        CheckBoxStander(
            R.string.chPlanDePagoFiltrarFacturaText.getResourceStringAndroid(context),
            Constantes.Companion.DescEstado.PlanDePago,
            viewModel
        )
    }
}

@Composable
private fun CheckBoxStander(text: String, type: Constantes.Companion.DescEstado, viewModel: FiltrarFacturasViewModel) {
    var checkBoxValue by remember { mutableStateOf(false) }
    Row() {
        Checkbox(
            checked = checkBoxValue,
            onCheckedChange = {
                checkBoxValue = it
                if (checkBoxValue) {
                    viewModel._filtroEstado.value!!.add(type.descEstado)
                } else {
                    viewModel._filtroEstado.value!!.remove(type.descEstado)
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                checkmarkColor = MaterialTheme.colors.onPrimary,
                uncheckedColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
            )
        )
        Text(
            text = text,
            style = normalTextFragment,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun FiltrarPorImporte(viewModel: FiltrarFacturasViewModel, navController: NavHostController) {
    val context = LocalContext.current
    var sliderPosition by remember { mutableStateOf(0f) }
    val maxValue by remember { mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<Int>(NavArgs.ItemMaxValueListaFacturas.key)!! + 1) }
    val minValue = 0

    sliderPosition = maxValue.toFloat()
    viewModel._filtroImporte.value = maxValue

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (tvTitleImporte, slImporte, tvMaxValue, tvMinValue, tvRangeValue, dv) = createRefs()

        Text(
            text = R.string.tvTitleImporteFiltrarFacturaText.getResourceStringAndroid(context),
            style = normalTextFragment,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(tvTitleImporte) {
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = "$minValue${R.string.monedaValue.getResourceStringAndroid(context)}",
            style = normalTextFragment,
            modifier = Modifier.constrainAs(tvMinValue) {
                top.linkTo(tvTitleImporte.bottom, margin = 10.dp)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = "$minValue${R.string.monedaValue.getResourceStringAndroid(context)} - ${sliderPosition.toInt()}${R.string.monedaValue.getResourceStringAndroid(context)}",
            style = normalTextFragment,
            modifier = Modifier.constrainAs(tvRangeValue) {
                top.linkTo(tvTitleImporte.bottom, margin = 6.dp)
                end.linkTo(tvMaxValue.start)
                start.linkTo(tvMinValue.end)
            }
        )
        Text(
            text = "$maxValue${R.string.monedaValue.getResourceStringAndroid(context)}",
            style = normalTextFragment,
            modifier = Modifier.constrainAs(tvMaxValue) {
                top.linkTo(tvTitleImporte.bottom, margin = 10.dp)
                end.linkTo(parent.end)
            }
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it;
                viewModel._filtroImporte.value = it.toInt()
            },
            valueRange = minValue.toFloat()..maxValue.toFloat(),
            modifier = Modifier
                .constrainAs(slImporte) {
                    top.linkTo(tvMinValue.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Divider(
            modifier = Modifier
                .constrainAs(dv) {
                    start.linkTo(parent.start)
                    top.linkTo(slImporte.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(vertical = 15.dp)
        )
    }
}

@Composable
private fun FiltrarPorFecha(viewModel: FiltrarFacturasViewModel) {
    val context = LocalContext.current
    val mDateDesde = remember { mutableStateOf(R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context)) }
    val mDateHasta = remember { mutableStateOf(R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context)) }

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (tvTitleFecha, tvTextFechaDesde, tvTextFechaHasta, btFechaDesde, btFechaHasta, dv) = createRefs()

        Text(
            text = R.string.tvTitleFechaFiltrarFacturaText.getResourceStringAndroid(context),
            style = normalTextFragment,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(tvTitleFecha) {
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = R.string.tvFechaDesdeFiltrarFacturaText.getResourceStringAndroid(context),
            style = normalTextFragment,
            color = Color.Gray,
            modifier = Modifier.constrainAs(tvTextFechaDesde) {
                top.linkTo(tvTitleFecha.bottom, margin = 15.dp)
                start.linkTo(parent.start)
            }
        )
        Button(
            onClick = {
                showDatePicker(mDateDesde, context, viewModel._filtroFechaDesde)
            },
            modifier = Modifier.constrainAs(btFechaDesde) {
                top.linkTo(tvTextFechaDesde.bottom, margin = 5.dp)
                start.linkTo(parent.start)
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(
                text = mDateDesde.value,
                style = normalTextFragment
            )
        }
        Text(
            text = R.string.tvFechaDesdeFiltrarFacturaText.getResourceStringAndroid(context),
            style = normalTextFragment,
            color = Color.Gray,
            modifier = Modifier.constrainAs(tvTextFechaHasta) {
                top.linkTo(tvTitleFecha.bottom, margin = 15.dp)
                start.linkTo(btFechaDesde.end, margin = 20.dp)
            }
        )
        Button(
            onClick = {
                showDatePicker(mDateHasta, context, viewModel._filtroFechaHasta)
            },
            modifier = Modifier.constrainAs(btFechaHasta) {
                top.linkTo(tvTextFechaHasta.bottom, margin = 5.dp)
                start.linkTo(btFechaDesde.end, margin = 20.dp)
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(
                text = mDateHasta.value,
                style = normalTextFragment
            )
        }
        Divider(
            modifier = Modifier
                .constrainAs(dv) {
                    start.linkTo(parent.start)
                    top.linkTo(btFechaDesde.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(vertical = 15.dp)
        )
    }
}

private fun showDatePicker(
    date: MutableState<String>,
    context: Context,
    viewModelData: MutableLiveData<String>
) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpdFecha = DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val newdf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
            date.value = "$dayOfMonth/${monthOfYear+1}/$year".castStringToDate().format(newdf)
            viewModelData.value = date.value
        }
    }, year, month, day)
    dpdFecha.datePicker.maxDate = Date().time
    dpdFecha.show()
}