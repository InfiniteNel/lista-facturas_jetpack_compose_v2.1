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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.jroslar.listafacturasjetpackcomposev21.R
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
                    onAplicarClick(viewModel._state)
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
                onClick = {
                          viewModel.eliminarFiltros()
                },
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
            viewModel._filtroEstadoPagada
        ) { viewModel._filtroEstadoPagada = it }
        CheckBoxStander(
            R.string.chAnuladasFiltrarFacturaText.getResourceStringAndroid(context),
            viewModel._filtroEstadoAnuladas
        ) { viewModel._filtroEstadoAnuladas = it }
        CheckBoxStander(
            R.string.chCuotaFijaFiltrarFacturaText.getResourceStringAndroid(context),
            viewModel._filtroEstadoCuotaFija
        ) { viewModel._filtroEstadoCuotaFija = it }
        CheckBoxStander(
            R.string.chPedientesDePagoFiltrarFacturaText.getResourceStringAndroid(context),
            viewModel._filtroEstadoPedienteDePago
        ) { viewModel._filtroEstadoPedienteDePago = it }
        CheckBoxStander(
            R.string.chPlanDePagoFiltrarFacturaText.getResourceStringAndroid(context),
            viewModel._filtroEstadoPlanDePago
        ) { viewModel._filtroEstadoPlanDePago = it }
    }
}

@Composable
private fun CheckBoxStander(text: String, filtro: Boolean, onCheckBoxChange:(Boolean) -> Unit) {
    Row {
        Checkbox(
            checked = filtro,
            onCheckedChange = {
                onCheckBoxChange(it)
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
    val maxValue by rememberSaveable { mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<Int>(NavArgs.ItemMaxValueListaFacturas.key)!! + 1) }
    val minValue = 0

    viewModel._filtroImporteMaxValue = maxValue
    viewModel._filtroImporte = maxValue.toFloat()

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
            text = "$minValue${R.string.monedaValue.getResourceStringAndroid(context)} - ${viewModel._filtroImporte.toInt()}${R.string.monedaValue.getResourceStringAndroid(context)}",
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
            value = viewModel._filtroImporte,
            onValueChange = {
                viewModel._filtroImporte = it
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
    viewModel._filtroFechaDefaultValue = R.string.btFechaFiltrarFacturaText.getResourceStringAndroid(context)
    viewModel._filtroFechaDesde = viewModel._filtroFechaDefaultValue
    viewModel._filtroFechaHasta = viewModel._filtroFechaDefaultValue

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
                showDatePicker({
                    viewModel._filtroFechaDesde = it
                }, context)
            },
            modifier = Modifier.constrainAs(btFechaDesde) {
                top.linkTo(tvTextFechaDesde.bottom, margin = 5.dp)
                start.linkTo(parent.start)
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(
                text = viewModel._filtroFechaDesde,
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
                showDatePicker({
                               viewModel._filtroFechaHasta = it
                }, context)
            },
            modifier = Modifier.constrainAs(btFechaHasta) {
                top.linkTo(tvTextFechaHasta.bottom, margin = 5.dp)
                start.linkTo(btFechaDesde.end, margin = 20.dp)
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(
                text = viewModel._filtroFechaHasta,
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
    onFechaGetValue:(String) -> Unit,
    context: Context
) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpdFecha = DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val newdf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
            onFechaGetValue("$dayOfMonth/${monthOfYear+1}/$year".castStringToDate().format(newdf))
        }
    }, year, month, day)
    dpdFecha.datePicker.maxDate = Date().time
    dpdFecha.show()
}