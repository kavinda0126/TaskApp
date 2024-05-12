package com.example.taskapp.Adapter

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R
import com.example.taskapp.ViewHolder.TaskVH
import com.example.taskapp.database.Task
import com.example.taskapp.database.TaskRepository
import com.example.taskapp.viewModel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TaskAdapter(private val items: List<Task>, private val repository: TaskRepository, private val viewModel: MainActivityData) :
    RecyclerView.Adapter<TaskVH>() {

    private var context: Context? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        context = parent.context

        return TaskVH(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        val currentTask = items[position]
        holder.titleinput.text = currentTask.title
        holder.dateinput.text = currentTask.date
        holder.timeinput.text = currentTask.time
        holder.statusinput.text = currentTask.status

        holder.deletebtn.setOnClickListener {
            // Create and show an AlertDialog for confirmation
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // User confirmed deletion, proceed with deletion
                        coroutineScope.launch(Dispatchers.IO) {
                            repository.delete(currentTask)
                            val data = repository.getAllItems()
                            withContext(Dispatchers.Main) {
                                viewModel.setData(data)
                            }
                        }
                    }
                    .setNegativeButton("No", null) // Do nothing if user cancels
                    .show()
            }
        }


        holder.editbtn.setOnClickListener {
            showEditModal(currentTask)
        }
    }

    private fun showEditModal(task: Task) {
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.modal_edit_task, null)
        val titleEditText: EditText = dialogView.findViewById(R.id.editTitleEditText)
        val dateEditText: EditText = dialogView.findViewById(R.id.editDateEditText)
        val timeEditText: EditText = dialogView.findViewById(R.id.editTimeEditText)
        val descEditText:EditText=dialogView.findViewById(R.id.editDescEditText)

        // Pre-fill input fields with existing task values
        titleEditText.setText(task.title)
        dateEditText.setText(task.date)
        timeEditText.setText(task.time)
        descEditText.setText(task.status)

        // Date picker dialog
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(context!!, { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                dateEditText.setText(selectedDate)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        // Time picker dialog
        timeEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(context!!, { _, hourOfDay, minute ->
                val selectedTime = "$hourOfDay:$minute"
                timeEditText.setText(selectedTime)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
            timePickerDialog.show()
        }

        val alertDialogBuilder = AlertDialog.Builder(context!!)
        alertDialogBuilder.setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        // Save button
        val saveButton: Button = dialogView.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val newTitle = titleEditText.text.toString()
            val newDate = dateEditText.text.toString()
            val newTime = timeEditText.text.toString()
            val status = descEditText.text.toString()

            coroutineScope.launch(Dispatchers.IO) {
                repository.edit(task.id!!.toInt(), newTitle, newDate, newTime, status)
                val data = repository.getAllItems()
                withContext(Dispatchers.Main) {
                    viewModel.setData(data)
                }
            }

            // Dismiss the dialog after saving
            alertDialog.dismiss()
        }

        // Cancel button
        val cancelButton: Button = dialogView.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}
