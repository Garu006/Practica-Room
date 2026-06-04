package ni.edu.uam.practicaroom.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prestamos",
    foreignKeys = [
        ForeignKey(
            entity = Equipo::class,
            parentColumns = ["id"],
            childColumns = ["equipoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val equipoId: Int,
    val solicitante: String,
    val fechaPrestamo: String,
    val fechaDevolucion: String? = null
)