package com.jroslar.listafacturasjetpackcomposev21.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.DescEstado
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.subTitleItem
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.titleFragment

class ListaFacturasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewContainer()
        }
    }
}

@Preview
@Composable
private fun ViewContainer() {
    Scaffold(
        topBar = { Toolbar() },
        content = { Content() }
    )
}

//@Preview
@Composable
private fun Toolbar() {
    TopAppBar(title = { Text(text = "") }, backgroundColor = Color.White)
}

//@Preview
@Composable
private fun Content() {
    val listaPracticas = listOf(
        FacturaModel("Pendiente de pago", 32F, "05/07/2020"),
        FacturaModel("Anulada", 32F, "05/07/2020")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Text(
                text = R.string.tvTitleFacturaListaFacturasText.getResourceStringAndroid(LocalContext.current),
                style = titleFragment,
                modifier = Modifier.padding(20.dp)
            )
        }
        items(listaPracticas.size) { position ->
            ItemFacturas(factura = listaPracticas[position])
        }
    }
}

@Composable
private fun ItemFacturas(factura: FacturaModel) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
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
            color = if (factura.descEstado == DescEstado.PedienteDePago.descEstado) { Color.Red }
                else { Color.Black },
            modifier = Modifier
                .constrainAs(descEstadoFactura) {
                    start.linkTo(guideStart)
                    top.linkTo(fechaFactura.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = factura.importeOrdenacion.toString().plus(R.string.monedaValue.getResourceStringAndroid(LocalContext.current)),
            modifier = Modifier
                .constrainAs(importeFactura) {
                    end.linkTo(arrowFactura.start, margin = 5.dp)
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
                .clickable {

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

