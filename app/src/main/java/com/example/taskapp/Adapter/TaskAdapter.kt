package com.example.taskapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskapp.R
import com.example.taskapp.ViewHolder.TaskVH
import com.example.taskapp.database.Task
import com.example.taskapp.database.TaskRepository
import com.example.taskapp.viewModel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskAdapter(items:List<Task>,repository: TaskRepository,viewModel:MainActivityData):Adapter<TaskVH>() {
    var context:Context?=null
    val items=items
    val repository=repository
    val viewModel=viewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)
        context=parent.context

        return TaskVH(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        holder.titleinput.text=items.get(position).title
        holder.dateinput.text=items.get(position).date
        holder.timeinput.text=items.get(position).time
        holder.statusinput.text=items.get(position).status

        holder.deletebtn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                repository.delete(items.get(position))
                val data=repository.getAllItems()
                withContext(Dispatchers.Main){
                    viewModel.setData(data)
                }
            }
        }
    }
}