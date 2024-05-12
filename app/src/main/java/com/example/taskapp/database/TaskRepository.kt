package com.example.taskapp.database

class TaskRepository ( private val db : TaskDB)
{
   suspend fun insert(task: Task)=db.getTaskDao().insert(task)
    suspend fun delete(task: Task)=db.getTaskDao().delete(task)
    suspend fun getAllItems():List<Task> = db.getTaskDao().getAllItems()
    suspend fun edit(taskId: Int, newTitle: String, newDate: String, newTime: String, newStatus: String) {
        db.getTaskDao().edit(taskId, newTitle, newDate, newTime, newStatus)



}
}