package com.jroslar.listafacturasjetpackcomposev21.ui.view.listafacturas


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.listafacturas.FiltrarFacturasScreen
import com.jroslar.listafacturasjetpackcomposev21.ui.screens.listafacturas.ListaFacturasScreen
import com.jroslar.listafacturasjetpackcomposev21.ui.viewmodel.listafacturas.ListaFacturasViewModel
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object ListaFacturas : Screen("lista_facturas")
    object FiltrarFacturas : Screen("filtrar_facturas")
}

enum class NavArgs(val key: String) {
    ItemListaFacturas("item_lista_facturas")
}

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    val viewModel: ListaFacturasViewModel = koinViewModel()
    NavHost(navController = navController, startDestination = Screen.ListaFacturas.route) {
        composable(Screen.ListaFacturas.route) {

            ListaFacturasScreen(
                onFiltroClick = {
                    navController.navigate(Screen.FiltrarFacturas.route)
                }, viewModel)
        }
        composable(
            route = Screen.FiltrarFacturas.route
        ) {

            FiltrarFacturasScreen(
                onCloseClick = {
                    navController.navigateUp()
                },
                onAplicarClick = {
                    viewModel.getList(it)
                    navController.navigateUp()
                }
            )
        }
    }
}