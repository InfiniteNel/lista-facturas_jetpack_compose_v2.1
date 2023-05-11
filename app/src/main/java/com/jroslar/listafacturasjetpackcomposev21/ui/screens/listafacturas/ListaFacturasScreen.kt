package com.jroslar.listafacturasjetpackcomposev21.ui.screens.listafacturas

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.subTitleItem
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.titleFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas.ListaFacturasViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListaFacturasScreen(onFiltroClick: () -> Unit, updateData: List<FacturaModel>?) {
    Scaffold(
        topBar = { Toolbar(onFiltroClick) },
        content = { Content(updateData) }
    )
}

//@Preview
@Composable
private fun Toolbar(onFiltroClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "") },
        backgroundColor = Color.White,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = { onFiltroClick() }) {
                Icon(painterResource(id = R.drawable.filtericon_3x), "")
            }
        }
    )
}

//@Preview
@Composable
private fun Content(updateData: List<FacturaModel>?, viewModel: ListaFacturasViewModel = koinViewModel()) {
    val openDialog = remember { mutableStateOf(false) }
    DetailFacturasScreen(openDialog)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Text(
                text = R.string.tvTitleFacturaListaFacturasText.getResourceStringAndroid(
                    LocalContext.current),
                style = titleFragment,
                modifier = Modifier.padding(bottom = 20.dp, start = 20.dp)
            )
        }
        when (viewModel._state) {
            ListaFacturasViewModel.ListaFacturasResult.DATA -> {
                items(viewModel._data) {
                    ItemFacturas(factura = it, openDialog)
                }
            }
            ListaFacturasViewModel.ListaFacturasResult.LOADING -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(15.dp)
                        )
                    }
                }
            }
            else -> {
                if (updateData != null ){
                    viewModel.getList(updateData)
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = R.string.tvTitleNoDataListaFacturasText.getResourceStringAndroid(
                                LocalContext.current),
                            style = normalTextFragment,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(15.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemFacturas(
    factura: FacturaModel,
    openDialog: MutableState<Boolean>
) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                openDialog.value = true
            }
    ) {
        val (fechaFactura, descEstadoFactura, importeFactura, arrowFactura, dividerFactura) = createRefs()
        val guideStart = createGuidelineFromStart(20.dp)

        createVerticalChain(fechaFactura, descEstadoFactura, chainStyle = ChainStyle.Packed)

        Text(
            text = factura.fecha,
            style = subTitleItem,
            textAlign = TextAlign.End,
            modifier = Modifier
                .constrainAs(fechaFactura) {
                    start.linkTo(guideStart)
                    top.linkTo(parent.top)
                    bottom.linkTo(descEstadoFactura.top)
                }
        )
        Text(
            text = factura.descEstado,
            color = if (factura.descEstado == Constantes.Companion.DescEstado.PedienteDePago.descEstado) { Color.Red }
            else { Color.Black },
            modifier = Modifier
                .constrainAs(descEstadoFactura) {
                    start.linkTo(guideStart)
                    top.linkTo(fechaFactura.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = factura.importeOrdenacion.toString().plus(
                R.string.monedaValue.getResourceStringAndroid(
                    LocalContext.current)),
            style = subTitleItem,
            modifier = Modifier
                .constrainAs(importeFactura) {
                    end.linkTo(arrowFactura.start, margin = 10.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow",
            modifier = Modifier
                .constrainAs(arrowFactura) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(dividerFactura) {
                    start.linkTo(guideStart)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}