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
    object FiltrarFacturas : Screen("filtrar_facturas/")
}

enum class NavArgs(val key: String) {
    ItemMaxValueListaFacturas("item_max_value_lista_facturas")
}

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    val viewModel: ListaFacturasViewModel = koinViewModel()
    NavHost(navController = navController, startDestination = Screen.ListaFacturas.route) {
        composable(Screen.ListaFacturas.route) {

            ListaFacturasScreen(
                onFiltroClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(NavArgs.ItemMaxValueListaFacturas.key, viewModel._maxValueImporte.value?.toInt() ?: 0)
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
                onAplicarClick = {facturas ->
                    viewModel.getList(facturas)
                    navController.navigateUp()
                },
                navController
            )
        }
    }
}