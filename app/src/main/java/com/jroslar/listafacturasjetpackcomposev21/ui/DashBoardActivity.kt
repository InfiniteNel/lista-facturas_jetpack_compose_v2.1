package com.jroslar.listafacturasjetpackcomposev21.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.data.model.PracticaModel
import com.jroslar.listafacturasjetpackcomposev21.data.model.PracticasName
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.subTitleItem
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.titleFragment

class MainActivity : ComponentActivity() {
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
        PracticaModel("Práctica 1", PracticasName.PRACTICA1),
        PracticaModel("Práctica 2", PracticasName.PRACTICA2)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Text(
                text = R.string.tvTitlePracticasAndroidText.getResourceStringAndroid(LocalContext.current),
                style = titleFragment,
                modifier = Modifier.padding(20.dp)
            )
        }
        items(listaPracticas.size) { position ->
            ItemPractica(practica = listaPracticas[position])
        }
    }
}

@Composable
fun ItemPractica(practica: PracticaModel) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        val (titlePractica, arrowPractica, dividerPractica) = createRefs()
        val context = LocalContext.current

        Text(
            text = practica.name,
            style = subTitleItem,
            textAlign = TextAlign.End,
            modifier = Modifier
                .constrainAs(titlePractica) {
                    start.linkTo(parent.start, margin = 20.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow",
            modifier = Modifier
                .constrainAs(arrowPractica) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    ContextCompat.startActivity(
                        context,
                        Intent(context, ListaFacturasActivity::class.java),
                        null
                    )
                }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(dividerPractica) {
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}
