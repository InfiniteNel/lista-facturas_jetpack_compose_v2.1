package com.jroslar.listafacturasjetpackcomposev21.ui.screens.smartsolar

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.smartsolar.SmartSolarDetallesViewModel
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun SmartSolarDetallesScreen(viewModel: SmartSolarDetallesViewModel = koinViewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (viewModel._state == SmartSolarDetallesViewModel.SmartSolarDetallesResult.LOADING) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp)
            )
        } else {
            TextFieldDetallesScreen(R.string.tietCAUSmaerSolarHint.getResourceStringAndroid(context), viewModel._data.cau)
            TextFieldDetallesScreen(
                R.string.tietEstadoAutoconsumidorSmartSolarHint.getResourceStringAndroid(context),
                viewModel._data.estadoSolicitudAutoConsumidor,
                context,
                R.drawable.selector_tooltip_azul_estado
            )
            TextFieldDetallesScreen(R.string.tietTipoConsumoSmartSolarHint.getResourceStringAndroid(context), viewModel._data.tipoAutoConsumo)
            TextFieldDetallesScreen(R.string.tietCompensacionSmartSolarHint.getResourceStringAndroid(context), viewModel._data.compensacionExcedentes)
            TextFieldDetallesScreen(R.string.tietPotenciaInstalacionSmartSolarHint.getResourceStringAndroid(context), viewModel._data.potenciaInstalacion)

        }
    }
}

@Composable
private fun TextFieldDetallesScreen(textLabel: String, textViewModel: String) {
    var text by rememberSaveable { mutableStateOf(textViewModel) }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = {
            Text(text = textLabel)
        },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Black
        ),
        textStyle = normalTextFragment
    )
}

@Composable
private fun TextFieldDetallesScreen(textLabel: String, textViewModel: String, context: Context, idIcon: Int) {
    var text by rememberSaveable { mutableStateOf(textViewModel) }
    val openDialog = rememberSaveable { mutableStateOf(false) }

    SmartSolarAutoconsumoScreen(openDialog)

    TextField(
        value = text,
        onValueChange = { text = it },
        label = {
            Text(text = textLabel)
        },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Black
        ),
        textStyle = normalTextFragment,
        trailingIcon = {
            IconButton(onClick = { openDialog.value = true }) {
                Image(
                    rememberAsyncImagePainter(ContextCompat.getDrawable(context, idIcon)),
                    contentDescription = ""
                )
            }
        }
    )
}