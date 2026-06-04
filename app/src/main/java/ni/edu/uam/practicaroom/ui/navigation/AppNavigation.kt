package ni.edu.uam.practicaroom.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.practicaroom.ui.screens.DashboardScreen
import ni.edu.uam.practicaroom.ui.screens.EquiposScreen
import ni.edu.uam.practicaroom.ui.screens.PrestamosScreen
import ni.edu.uam.practicaroom.ui.screens.ReportesScreen
import ni.edu.uam.practicaroom.ui.viewmodel.InventarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: InventarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable("dashboard") {
            DashboardScreen(navController, viewModel)
        }

        composable("equipos") {
            EquiposScreen(navController, viewModel)
        }

        composable("prestamos") {
            PrestamosScreen(navController, viewModel)
        }

        composable("reportes") {
            ReportesScreen(navController, viewModel)
        }
    }
}