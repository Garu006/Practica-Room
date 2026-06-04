package ni.edu.uam.practicaroom.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.practicaroom.data.local.database.AppDatabase
import ni.edu.uam.practicaroom.data.local.entity.Equipo
import ni.edu.uam.practicaroom.data.local.entity.Prestamo
import ni.edu.uam.practicaroom.data.repository.InventarioRepository

class InventarioViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)

    private val repository = InventarioRepository(
        equipoDao = database.equipoDao(),
        prestamoDao = database.prestamoDao()
    )

    val equipos: StateFlow<List<Equipo>> = repository.equipos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val prestamos: StateFlow<List<Prestamo>> = repository.prestamos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertarEquipo(
        nombre: String,
        categoria: String,
        marca: String,
        numeroSerie: String
    ) {
        viewModelScope.launch {
            repository.insertarEquipo(
                Equipo(
                    nombre = nombre,
                    categoria = categoria,
                    marca = marca,
                    numeroSerie = numeroSerie
                )
            )
        }
    }

    fun actualizarEquipo(equipo: Equipo) {
        viewModelScope.launch {
            repository.actualizarEquipo(equipo)
        }
    }

    fun eliminarEquipo(equipo: Equipo) {
        viewModelScope.launch {
            repository.eliminarEquipo(equipo)
        }
    }

    fun registrarPrestamo(
        equipoId: Int,
        solicitante: String,
        fechaPrestamo: String
    ) {
        viewModelScope.launch {
            repository.insertarPrestamo(
                Prestamo(
                    equipoId = equipoId,
                    solicitante = solicitante,
                    fechaPrestamo = fechaPrestamo,
                    fechaDevolucion = null
                )
            )
        }
    }

    fun devolverEquipo(prestamo: Prestamo, fechaDevolucion: String) {
        viewModelScope.launch {
            repository.devolverEquipo(prestamo, fechaDevolucion)
        }
    }

    fun eliminarPrestamo(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.eliminarPrestamo(prestamo)
        }
    }
}