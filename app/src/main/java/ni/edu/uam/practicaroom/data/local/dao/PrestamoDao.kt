package ni.edu.uam.practicaroom.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.practicaroom.data.local.entity.Prestamo

@Dao
interface PrestamoDao {

    @Query("SELECT * FROM prestamos ORDER BY id DESC")
    fun getAllPrestamos(): Flow<List<Prestamo>>

    @Insert
    suspend fun insertPrestamo(prestamo: Prestamo)

    @Update
    suspend fun updatePrestamo(prestamo: Prestamo)

    @Delete
    suspend fun deletePrestamo(prestamo: Prestamo)

    @Query("SELECT * FROM prestamos WHERE equipoId = :equipoId")
    fun getPrestamosByEquipo(equipoId: Int): Flow<List<Prestamo>>
}