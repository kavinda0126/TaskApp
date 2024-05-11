package com.example.taskapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskapp.R
import com.example.taskapp.ViewHolder.TaskVH

class TaskAdapter:Adapter<TaskVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)

        return TaskVH(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        holder.titleinput.text="Sample Title"

    }
}