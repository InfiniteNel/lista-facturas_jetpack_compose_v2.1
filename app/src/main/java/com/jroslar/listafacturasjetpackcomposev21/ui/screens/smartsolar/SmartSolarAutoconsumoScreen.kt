package com.jroslar.listafacturasjetpackcomposev21.ui.screens.smartsolar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextDialogFragment
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.subTitleFragment

@Composable
fun SmartSolarAutoconsumoScreen(openDialog: MutableState<Boolean>) {
    val context = LocalContext.current
    if (openDialog.value) {
        Dialog(onDismissRequest =
        { openDialog.value = false },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface {
                ConstraintLayout {
                    val (tvEstadoSolicitud, tvTiempoEstimado, btAceptar) = createRefs()
                    val guideStart = createGuidelineFromStart(40.dp)
                    val guideEnd = createGuidelineFromEnd(40.dp)

                    Text(
                        text = R.string.tvEstadoSolicitudSmartSolarText.getResourceStringAndroid(context),
                        style = subTitleFragment,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .constrainAs(tvEstadoSolicitud) {
                                top.linkTo(parent.top, margin = 24.dp)
                                start.linkTo(guideStart)
                                end.linkTo(guideEnd)
                            }
                    )
                    Text(
                        text = R.string.tvTiempoEstimadoSmartSolarText.getResourceStringAndroid(context),
                        style = normalTextDialogFragment,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .constrainAs(tvTiempoEstimado) {
                                top.linkTo(tvEstadoSolicitud.bottom, margin = 24.dp)
                                start.linkTo(guideStart)
                                end.linkTo(guideEnd)
                            }
                    )
                    Button(
                        onClick = { openDialog.value = false },
                        modifier = Modifier
                            .constrainAs(btAceptar) {
                                top.linkTo(tvTiempoEstimado.bottom, margin = 24.dp)
                                start.linkTo(guideStart)
                                end.linkTo(guideEnd)
                                bottom.linkTo(parent.bottom, margin = 40.dp)
                                width = Dimension.fillToConstraints
                            },
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = R.string.btAceptarSmartSolarText.getResourceStringAndroid(context),
                            style = normalTextDialogFragment,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterVertically).padding(vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}