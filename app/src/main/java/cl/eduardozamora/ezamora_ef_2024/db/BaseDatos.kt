package cl.eduardozamora.ezamora_ef_2024.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.eduardozamora.ezamora_ef_2024.entity.Registro


@Database(entities = [Registro::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun registroDao(): RegistroDao

}