package com.jroslar.listafacturasjetpackcomposev21.ui.view.listafacturas


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jroslar.listafacturasjetpackcomposev21.data.model.FacturaModel
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.listafacturas.FiltrarFacturasScreen
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.listafacturas.ListaFacturasScreen

sealed class Screen(val route: String) {
    object ListaFacturas: Screen("lista_facturas")
    object FiltrarFacturas: Screen("filtrar_facturas")
}

enum class NavArgs(val key: String) {
    ItemListaFacturas("item_lista_facturas")
}

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ListaFacturas.route) {
        composable(Screen.ListaFacturas.route) {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<List<FacturaModel>>(NavArgs.ItemListaFacturas.key)
            ListaFacturasScreen(onFiltroClick =  {
                navController.navigate(Screen.FiltrarFacturas.route)
            }, result)
        }
        composable(
            route = Screen.FiltrarFacturas.route
        ) {

            FiltrarFacturasScreen(
                onCloseClick = {
                    navController.navigateUp()
                },
                onAplicarClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        NavArgs.ItemListaFacturas.key,
                        it
                    )
                    navController.navigateUp()
                }
            )
        }
    }
}