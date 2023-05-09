package com.jroslar.listafacturasjetpackcomposev21.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.DETAIL_FACTURAS
import com.jroslar.listafacturasjetpackcomposev21.core.Constantes.Companion.MAIN_FACTURAS
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.DetailFacturasScreen
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.ListaFacturasScreen
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.ListaFacturasJetpackComposeV21Theme

class ListaFacturasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaFacturasJetpackComposeV21Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = MAIN_FACTURAS) {
                    composable(MAIN_FACTURAS) {
                        ListaFacturasScreen(navController = navController)
                    }
                    composable(DETAIL_FACTURAS) {
                        ListaFacturasScreen(navController = navController)
                        DetailFacturasScreen()
                    }
                }
            }
        }
    }
}


