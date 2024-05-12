package com.example.taskapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.Adapter.TaskAdapter

import com.example.taskapp.database.Task
import com.example.taskapp.database.TaskDB
import com.example.taskapp.database.TaskRepository
import com.example.taskapp.viewModel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var adapter:TaskAdapter
    private lateinit var viewModel:MainActivityData
   // private lateinit var repository:TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView=findViewById(R.id.rvTaskList)
        val addBtn:Button=findViewById(R.id.btn_addtask)
        val repository= TaskRepository(TaskDB.getInstance(this))
        viewModel=ViewModelProvider(this)[MainActivityData::class.java]



        viewModel.data.observe(this){
           adapter = TaskAdapter(it,repository,viewModel)
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(this)
        }
        CoroutineScope(Dispatchers.IO).launch{
            val data=repository.getAllItems()

           runOnUiThread(){
               viewModel.setData(data)
           }
        }

    addBtn.setOnClickListener{
        displayAlert(repository)
    }


    }

    private fun displayAlert(repository:TaskRepository){
        val builder=AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_dialog_layout, null)

        val titleEditText = dialogView.findViewById<EditText>(R.id.titleEditText)
        val dateEditText = dialogView.findViewById<EditText>(R.id.dateEditText)
        val timeEditText = dialogView.findViewById<EditText>(R.id.timeEditText)

        builder.setView(dialogView)
        builder.setTitle(getText(R.string.alert_title))
        builder.setMessage("Enter your details")

        builder.setPositiveButton("OK") { dialog, _ ->
            val title = titleEditText.text.toString()
            val date = dateEditText.text.toString()
            val time = timeEditText.text.toString()
            val status= "inprogress"
            // Handle the data as needed
            val formData=Task(title,date,time,status)

            CoroutineScope(Dispatchers.IO).launch{
                repository.insert(formData)
                val data = repository.getAllItems()

                runOnUiThread(){
                    viewModel.setData(data)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog=builder.create()
        alertDialog.show()
    }

}