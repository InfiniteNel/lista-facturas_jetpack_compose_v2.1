package com.jroslar.listafacturasjetpackcomposev21.ui.view.smartsolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.smartsolar.SmartSolarScreen
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.ListaFacturasJetpackComposeV21Theme

class SmartSolarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaFacturasJetpackComposeV21Theme {
                SmartSolarScreen()
            }
        }
    }
}