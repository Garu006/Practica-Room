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
import ni.edu.uam.practicaroom.data.local.entity.Equipo
import ni.edu.uam.practicaroom.ui.viewmodel.InventarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreen(
    navController: NavHostController,
    viewModel: InventarioViewModel
) {
    val equipos by viewModel.equipos.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var numeroSerie by remember { mutableStateOf("") }
    var equipoEditando by remember { mutableStateOf<Equipo?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Equipos") },
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
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del equipo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numeroSerie,
                onValueChange = { numeroSerie = it },
                label = { Text("Número de serie") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (
                        nombre.isNotBlank() &&
                        categoria.isNotBlank() &&
                        marca.isNotBlank() &&
                        numeroSerie.isNotBlank()
                    ) {
                        if (equipoEditando == null) {
                            viewModel.insertarEquipo(
                                nombre = nombre,
                                categoria = categoria,
                                marca = marca,
                                numeroSerie = numeroSerie
                            )
                        } else {
                            viewModel.actualizarEquipo(
                                equipoEditando!!.copy(
                                    nombre = nombre,
                                    categoria = categoria,
                                    marca = marca,
                                    numeroSerie = numeroSerie
                                )
                            )
                            equipoEditando = null
                        }

                        nombre = ""
                        categoria = ""
                        marca = ""
                        numeroSerie = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (equipoEditando == null) "Registrar equipo" else "Actualizar equipo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(equipos) { equipo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Nombre: ${equipo.nombre}")
                            Text("Categoría: ${equipo.categoria}")
                            Text("Marca: ${equipo.marca}")
                            Text("Serie: ${equipo.numeroSerie}")
                            Text("Disponible: ${if (equipo.disponible) "Sí" else "No"}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                Button(
                                    onClick = {
                                        equipoEditando = equipo
                                        nombre = equipo.nombre
                                        categoria = equipo.categoria
                                        marca = equipo.marca
                                        numeroSerie = equipo.numeroSerie
                                    }
                                ) {
                                    Text("Editar")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        viewModel.eliminarEquipo(equipo)
                                    }
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
