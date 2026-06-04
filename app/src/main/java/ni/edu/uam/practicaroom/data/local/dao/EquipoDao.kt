package ni.edu.uam.practicaroom.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.practicaroom.data.local.entity.Equipo

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos ORDER BY id DESC")
    fun getAllEquipos(): Flow<List<Equipo>>

    @Insert
    suspend fun insertEquipo(equipo: Equipo)

    @Update
    suspend fun updateEquipo(equipo: Equipo)

    @Delete
    suspend fun deleteEquipo(equipo: Equipo)

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getEquipoById(id: Int): Equipo?

    @Query("SELECT * FROM equipos WHERE disponible = 1")
    fun getEquiposDisponibles(): Flow<List<Equipo>>

    @Query("SELECT * FROM equipos WHERE nombre LIKE '%' || :texto || '%' OR numeroSerie LIKE '%' || :texto || '%'")
    fun buscarEquipos(texto: String): Flow<List<Equipo>>
}