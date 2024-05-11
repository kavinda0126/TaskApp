package com.example.taskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDB:RoomDatabase() {
    abstract fun getTaskDao():TaskDao

    companion object{
        @Volatile
        private var INSTANCE:TaskDB? = null

        fun getInstance(context:Context):TaskDB{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context,
                    TaskDB::class.java,
                    "task_db"
                ).build().also{
                    INSTANCE=it
                }
            }
        }
    }
}