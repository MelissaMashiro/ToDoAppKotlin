package com.melissa.todoapp.addtasks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//interfaz q nospermite hacer las petiiones a la base de datos room
@Dao
interface TaskDao {
    @Query(value = "SELECT * from TaskEntity")
     fun getTasks():Flow<List<TaskEntity>>

     @Insert //la etiqueta hace tdo el trabajo
     suspend fun addTask(item:TaskEntity)

    @Update //la etiqueta hace tdo el trabajo
    suspend fun updateTask(item:TaskEntity)

    @Delete //la etiqueta hace tdo el trabajo
    suspend fun deleteTask(item:TaskEntity)
}