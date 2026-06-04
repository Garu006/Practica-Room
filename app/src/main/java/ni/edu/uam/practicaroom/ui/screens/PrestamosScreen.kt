package ni.edu.uam.practicaroom.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ni.edu.uam.practicaroom.ui.viewmodel.InventarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamosScreen(
    navController: NavHostController,
    viewModel: InventarioViewModel
) {
    val equipos by viewModel.equipos.collectAsState()
    val prestamos by viewModel.prestamos.collectAsState()

    var equipoIdTexto by remember { mutableStateOf("") }
    var solicitante by remember { mutableStateOf("") }
    var fechaPrestamo by remember { mutableStateOf("") }
    var fechaDevolucion by remember { mutableStateOf("") }

    val equiposDisponibles = equipos.filter { it.disponible }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Préstamos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            Text("Equipos disponibles:")
            equiposDisponibles.forEach { equipo ->
                Text("ID ${equipo.id}: ${equipo.nombre} - ${equipo.numeroSerie}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = equipoIdTexto,
                onValueChange = { equipoIdTexto = it },
                label = { Text("ID del equipo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = solicitante,
                onValueChange = { solicitante = it },
                label = { Text("Nombre del solicitante") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaPrestamo,
                onValueChange = { fechaPrestamo = it },
                label = { Text("Fecha de préstamo") },
                placeholder = { Text("Ej: 03/06/2026") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val equipoId = equipoIdTexto.toIntOrNull()

                    if (
                        equipoId != null &&
                        solicitante.isNotBlank() &&
                        fechaPrestamo.isNotBlank()
                    ) {
                        viewModel.registrarPrestamo(
                            equipoId = equipoId,
                            solicitante = solicitante,
                            fechaPrestamo = fechaPrestamo
                        )

                        equipoIdTexto = ""
                        solicitante = ""
                        fechaPrestamo = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar préstamo")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Historial de préstamos",
                style = MaterialTheme.typography.titleMedium
            )

            LazyColumn {
                items(prestamos) { prestamo ->
                    val equipo = equipos.find { it.id == prestamo.equipoId }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Equipo: ${equipo?.nombre ?: "Equipo no encontrado"}")
                            Text("Solicitante: ${prestamo.solicitante}")
                            Text("Fecha préstamo: ${prestamo.fechaPrestamo}")
                            Text("Fecha devolución: ${prestamo.fechaDevolucion ?: "Pendiente"}")

                            Spacer(modifier = Modifier.height(8.dp))

                            if (prestamo.fechaDevolucion == null) {
                                OutlinedTextField(
                                    value = fechaDevolucion,
                                    onValueChange = { fechaDevolucion = it },
                                    label = { Text("Fecha de devolución") },
                                    placeholder = { Text("Ej: 05/06/2026") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        if (fechaDevolucion.isNotBlank()) {
                                            viewModel.devolverEquipo(prestamo, fechaDevolucion)
                                            fechaDevolucion = ""
                                        }
                                    }
                                ) {
                                    Text("Registrar devolución")
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                Button(
                                    onClick = {
                                        viewModel.eliminarPrestamo(prestamo)
                                    }
                                ) {
                                    Text("Eliminar préstamo")
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
