package com.jroslar.listafacturasjetpackcomposev21.ui.screens.smartsolar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextFragment

@Composable
fun SmartSolarMiInstalacionScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = R.string.tvInfoTextMiInstalacionSmartSolarText.getResourceStringAndroid(context),
            style = normalTextFragment
        )
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 40.dp)
        ) {
            Text(
                text = R.string.tvAutoconsumoMiInstalacionSmartSolarText.getResourceStringAndroid(context),
                style = normalTextFragment,
                color = Color.Gray
            )
            Text(
                text = R.string.tvAutoConsumoValueMiInstalacionSmartSolarText.getResourceStringAndroid(context),
                style = normalTextFragment,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(id = R.drawable.grafico1),
            contentDescription = "",
            modifier = Modifier.padding(top = 120.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = 3.5F
                    scaleY = 3.5F
                }
        )
    }
}