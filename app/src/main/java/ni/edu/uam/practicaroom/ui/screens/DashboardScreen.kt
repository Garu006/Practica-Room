package ni.edu.uam.practicaroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ni.edu.uam.practicaroom.ui.viewmodel.InventarioViewModel

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: InventarioViewModel
) {
    val equipos by viewModel.equipos.collectAsState()
    val prestamos by viewModel.prestamos.collectAsState()

    val totalEquipos = equipos.size
    val equiposDisponibles = equipos.count { it.disponible }
    val equiposPrestados = equipos.count { !it.disponible }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Dashboard de Inventario",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total de equipos: $totalEquipos")
                Text("Equipos disponibles: $equiposDisponibles")
                Text("Equipos prestados: $equiposPrestados")
                Text("Préstamos registrados: ${prestamos.size}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("equipos") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar equipos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("prestamos") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar préstamos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("reportes") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver reportes")
        }
    }
}