package ni.edu.uam.practicaroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ni.edu.uam.practicaroom.ui.viewmodel.InventarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportesScreen(
    navController: NavHostController,
    viewModel: InventarioViewModel
) {
    val equipos by viewModel.equipos.collectAsState()

    val totalEquipos = equipos.size
    val disponibles = equipos.count { it.disponible }
    val prestados = equipos.count { !it.disponible }
    
    val categoriaConMasEquipos = equipos
        .groupBy { it.categoria }
        .maxByOrNull { it.value.size }
        ?.key ?: "N/A"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reportes del Inventario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReportCard(
                label = "Total de Equipos",
                value = totalEquipos.toString(),
                color = MaterialTheme.colorScheme.primaryContainer
            )
            
            ReportCard(
                label = "Equipos Disponibles",
                value = disponibles.toString(),
                color = Color(0xFFC8E6C9) // Verde claro
            )
            
            ReportCard(
                label = "Equipos Prestados",
                value = prestados.toString(),
                color = Color(0xFFFFCDD2) // Rojo claro
            )
            
            ReportCard(
                label = "Categoría con más Equipos",
                value = categoriaConMasEquipos,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}

@Composable
fun ReportCard(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
