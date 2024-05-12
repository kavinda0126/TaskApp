package com.example.taskapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
        val currentTask=items[position]
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

        holder.editbtn.setOnClickListener {
            showEditDialog(currentTask)
        }
    }

    private fun showEditDialog(task: Task) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_task, null)
        val dialogBuilder = AlertDialog.Builder(context!!)
            .setView(dialogView)
            .setTitle("Edit Task")

        val titleEditText: EditText = dialogView.findViewById(R.id.editTitleEditText)
        val dateEditText: EditText = dialogView.findViewById(R.id.editDateEditText)
        val timeEditText: EditText = dialogView.findViewById(R.id.editTimeEditText)

        // Pre-fill input fields with existing task values
        titleEditText.setText(task.title)
        dateEditText.setText(task.date)
        timeEditText.setText(task.time)

        dialogBuilder.setPositiveButton("Save") { _, _ ->
            val newTitle = titleEditText.text.toString()
            val newDate = dateEditText.text.toString()
            val newTime = timeEditText.text.toString()
            val status= "completed"

            // Update task with new values
            val updatedTask = Task(newTitle,newDate,newTime,status)
            CoroutineScope(Dispatchers.IO).launch {
                task.id?.let { repository.edit(it.toInt(),newTitle,newDate,newTime,status) }
                val data = repository.getAllItems()
                withContext(Dispatchers.Main) {
                    viewModel.setData(data)
                }
            }
        }

        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}
