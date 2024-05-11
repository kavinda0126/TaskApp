package com.example.taskapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task:Task)

    @Query("SELECT * FROM Task")
    suspend fun getAllItems():List<Task>

    @Query("SELECT * FROM Task WHERE id=:id")
    suspend fun getOne(id:Int):Task
}
