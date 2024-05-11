package com.example.taskapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.database.Task

class MainActivityData : ViewModel(){

    //editable object
    private val _data = MutableLiveData<List<Task>>()

    val data: LiveData<List<Task>> =_data

    fun setData(data:List<Task>){
        _data.value=data
    }

}