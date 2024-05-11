package com.example.taskapp.database

class TaskRepository ( private val db : TaskDB)
{
   suspend fun insert(task: Task)=db.getTaskDao().insert(task)
    suspend fun delete(task: Task)=db.getTaskDao().delete(task)
    suspend fun getAllItems():List<Task> = db.getTaskDao().getAllItems()
}