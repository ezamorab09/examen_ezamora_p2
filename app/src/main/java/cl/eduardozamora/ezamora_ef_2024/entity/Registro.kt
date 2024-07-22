package cl.eduardozamora.ezamora_ef_2024.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Registro (
    @PrimaryKey val medidor: Int, // Clave primaria
    @ColumnInfo(name = "tipo") val tipo: String, // "agua", "luz" o "gas"
    @ColumnInfo(name = "fecha") val fecha: Long, // Usar Long para almacenar la fecha como timestamp
    @ColumnInfo(name = "icono") val icono: String
)