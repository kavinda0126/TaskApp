package com.example.taskapp

import AddFragment
import MainFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

class GeneralActivity : AppCompatActivity() {

    val addFragment=AddFragment()
    val mainFragment=MainFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        val all_btn:Button=findViewById(R.id.all_task_btn)
        val add_btn:Button=findViewById(R.id.add_task_btn)

        loadAllTasks()

        all_btn.setOnClickListener{
            loadAllTasks()
        }


        add_btn.setOnClickListener{
            loadAddTask()
        }


    }

    private fun loadAllTasks(){
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if(fragment==null){
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,mainFragment).commit()
        }
        else{
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,mainFragment).commit()
        }

    }

    private fun loadAddTask(){
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if(fragment==null){
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,addFragment).commit()
        }
        else{
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,addFragment).commit()
        }

    }
}