package ni.edu.uam.practicaroom.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.practicaroom.data.local.dao.EquipoDao
import ni.edu.uam.practicaroom.data.local.dao.PrestamoDao
import ni.edu.uam.practicaroom.data.local.entity.Equipo
import ni.edu.uam.practicaroom.data.local.entity.Prestamo

class InventarioRepository(
    private val equipoDao: EquipoDao,
    private val prestamoDao: PrestamoDao
) {
    val equipos: Flow<List<Equipo>> = equipoDao.getAllEquipos()
    val prestamos: Flow<List<Prestamo>> = prestamoDao.getAllPrestamos()

    fun buscarEquipos(texto: String): Flow<List<Equipo>> {
        return equipoDao.buscarEquipos(texto)
    }

    fun equiposDisponibles(): Flow<List<Equipo>> {
        return equipoDao.getEquiposDisponibles()
    }

    suspend fun insertarEquipo(equipo: Equipo) {
        equipoDao.insertEquipo(equipo)
    }

    suspend fun actualizarEquipo(equipo: Equipo) {
        equipoDao.updateEquipo(equipo)
    }

    suspend fun eliminarEquipo(equipo: Equipo) {
        equipoDao.deleteEquipo(equipo)
    }

    suspend fun obtenerEquipoPorId(id: Int): Equipo? {
        return equipoDao.getEquipoById(id)
    }

    suspend fun insertarPrestamo(prestamo: Prestamo) {
        prestamoDao.insertPrestamo(prestamo)

        val equipo = equipoDao.getEquipoById(prestamo.equipoId)
        if (equipo != null) {
            equipoDao.updateEquipo(equipo.copy(disponible = false))
        }
    }

    suspend fun devolverEquipo(prestamo: Prestamo, fechaDevolucion: String) {
        prestamoDao.updatePrestamo(
            prestamo.copy(fechaDevolucion = fechaDevolucion)
        )

        val equipo = equipoDao.getEquipoById(prestamo.equipoId)
        if (equipo != null) {
            equipoDao.updateEquipo(equipo.copy(disponible = true))
        }
    }

    suspend fun eliminarPrestamo(prestamo: Prestamo) {
        prestamoDao.deletePrestamo(prestamo)
    }
}