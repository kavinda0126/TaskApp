package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var openbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openbtn=findViewById(R.id.open_btn)
        openbtn.setOnClickListener{

            val intent = Intent(this, GeneralActivity::class.java)
            startActivity(intent)
        }

    }
}