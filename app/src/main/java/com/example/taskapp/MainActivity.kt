package com.example.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.Adapter.TaskAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView=findViewById(R.id.rvTaskList)
        val taskAdapter = TaskAdapter()
        recyclerView.adapter=taskAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)
    }
}