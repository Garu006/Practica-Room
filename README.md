#Practica Room

Introducción

Los laboratorios de informática requieren herramientas que permitan administrar equipos tecnológicos, controlar préstamos y mantener actualizado el inventario.

En esta práctica se desarrollará una aplicación Android utilizando Jetpack Compose para la interfaz gráfica y Room Database para la persistencia local de datos.
Objetivos
Objetivo General

Diseñar e implementar una aplicación móvil para la gestión de inventario de equipos tecnológicos utilizando Room y Jetpack Compose.
Objetivos Específicos

    Aplicar arquitectura MVVM.
    Implementar persistencia de datos con Room.
    Gestionar relaciones entre entidades.
    Construir interfaces modernas con Jetpack Compose.
    Desarrollar operaciones CRUD completas.
    Utilizar StateFlow para actualizar la interfaz.

Descripción del Sistema
Gestión de Equipos

    Registrar equipos.
    Modificar información.
    Eliminar registros.
    Consultar inventario.

Gestión de Préstamos

    Registrar préstamos.
    Registrar devoluciones.
    Consultar historial.

Reportes

    Equipos disponibles.
    Equipos prestados.
    Cantidad de equipos por categoría.

Modelo de Datos
Entidad Equipo


@Entity(tableName = "equipos")
data class Equipo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val categoria: String,

    val marca: String,

    val numeroSerie: String,

    val disponible: Boolean = true
)

Entidad Préstamo


@Entity(
    tableName = "prestamos"
)
data class Prestamo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val equipoId: Int,

    val solicitante: String,

    val fechaPrestamo: String,

    val fechaDevolucion: String?
)

Funcionalidades Mínimas
Módulo 1: Gestión de Equipos

    Registro de equipos.
    Edición de equipos.
    Eliminación de registros.
    Consulta general.

Módulo 2: Gestión de Préstamos

    Registro de préstamos.
    Control de disponibilidad.
    Registro de devoluciones.
    Historial de movimientos.

Módulo 3: Dashboard

    Total de equipos.
    Equipos disponibles.
    Equipos prestados.
    Categoría con mayor cantidad de equipos.

Actividades Complementarias

    Implementar búsqueda por nombre o número de serie.
    Agregar filtros por categoría.
    Exportar información a formato CSV.
    Generar estadísticas mediante gráficos.
    Implementar autenticación local de administradores.

Entregables

    Código fuente completo (link del repositorio público).
    Base de datos Room funcional.
    Capturas de pantalla del sistema.
    Informe técnico de 3 a 5 páginas.

