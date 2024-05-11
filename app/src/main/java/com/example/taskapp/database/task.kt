package com.example.taskapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    var title: String?,
    var date: String?,
    var time: String?,
    var status: String?,
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
