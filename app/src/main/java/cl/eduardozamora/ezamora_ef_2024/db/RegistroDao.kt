package cl.eduardozamora.ezamora_ef_2024.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cl.eduardozamora.ezamora_ef_2024.entity.Registro


@Dao
interface RegistroDao {
    @Query("SELECT * FROM Registro")
    suspend fun getAll(): List<Registro>

    @Insert
    suspend fun insertAll(vararg registro: Registro)

    @Delete
    suspend fun delete(registro: Registro)
}