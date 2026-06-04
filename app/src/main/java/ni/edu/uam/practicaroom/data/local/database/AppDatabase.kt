package ni.edu.uam.practicaroom.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ni.edu.uam.practicaroom.data.local.dao.EquipoDao
import ni.edu.uam.practicaroom.data.local.dao.PrestamoDao
import ni.edu.uam.practicaroom.data.local.entity.Equipo
import ni.edu.uam.practicaroom.data.local.entity.Prestamo

@Database(
    entities = [Equipo::class, Prestamo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao
    abstract fun prestamoDao(): PrestamoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inventario_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}