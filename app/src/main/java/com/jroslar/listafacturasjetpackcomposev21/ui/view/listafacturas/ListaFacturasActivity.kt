package com.jroslar.listafacturasjetpackcomposev21.ui.view.listafacturas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jroslar.listafacturasjetpackcomposev21.ui.theme.ListaFacturasJetpackComposeV21Theme

class ListaFacturasActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaFacturasJetpackComposeV21Theme {
                navController = rememberNavController()
                SetUpNavGraph(navController)
            }
        }
    }
}

