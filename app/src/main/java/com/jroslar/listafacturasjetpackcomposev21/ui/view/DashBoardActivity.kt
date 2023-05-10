package com.jroslar.listafacturasjetpackcomposev21.ui.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.dashboard.DashBoardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashBoardScreen()
        }
    }
}
