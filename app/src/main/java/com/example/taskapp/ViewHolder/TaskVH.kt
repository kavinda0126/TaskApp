package com.example.taskapp.ViewHolder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskapp.R

class TaskVH(view: View):ViewHolder(view) {

    val titleinput:TextView=view.findViewById(R.id.titleinput)
    val dateinput:TextView=view.findViewById(R.id.dateinput)
    val timeinput:TextView=view.findViewById(R.id.timeinput)
    val statusinput:TextView=view.findViewById(R.id.statusinput)
    val editbtn:Button=view.findViewById(R.id.editbtn)
    val deletebtn:Button=view.findViewById(R.id.deletebtn)

}