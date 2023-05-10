package com.jroslar.listafacturasjetpackcomposev21.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextDialogFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.subTitleFragment

@Composable
fun DetailFacturasScreen(openDialog: MutableState<Boolean>) {
    val context = LocalContext.current
    if (openDialog.value) {
        Dialog(onDismissRequest =
            { openDialog.value = false },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp)
            ) {
                Column() {
                    Text(
                        text = R.string.tvInformacionDetallesFacturasText.getResourceStringAndroid(context),
                        style = subTitleFragment,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp, bottom = 10.dp)
                    )
                    Text(
                        text = R.string.tvNoFuncionalDetallesFacturasText.getResourceStringAndroid(context),
                        style = normalTextDialogFragment,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(15.dp)
                    )
                    Button(
                        onClick = { openDialog.value = false },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(15.dp),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = R.string.btCerrarPopUpDetallesFacturasText.getResourceStringAndroid(context),
                            style = normalTextDialogFragment,
                            color = Color.White,
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}