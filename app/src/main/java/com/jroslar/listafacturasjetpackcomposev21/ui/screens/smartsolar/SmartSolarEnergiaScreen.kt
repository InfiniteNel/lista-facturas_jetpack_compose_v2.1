package com.jroslar.listafacturasjetpackcomposev21.ui.screens.smartsolar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jroslar.listafacturasjetpackcomposev21.R
import com.jroslar.listafacturasjetpackcomposev21.core.Extensions.Companion.getResourceStringAndroid
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.normalTextFragment

@Composable
fun SmartSolarEnergiaScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.plan_gestiones),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
                .padding(top = 100.dp)
        )
        Text(
            text = R.string.tvInfoTextEnergiaSmartSolarText.getResourceStringAndroid(context),
            style = normalTextFragment,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 80.dp)
        )
    }
}