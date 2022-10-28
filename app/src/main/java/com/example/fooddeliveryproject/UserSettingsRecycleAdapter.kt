package com.example.fooddeliveryproject

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.red
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fragment.user.ProfileFragment

class UserSettingsRecycleAdapter
    (val context : ProfileFragment,
     val settingsList : List<UserSettings>
     ) :
    RecyclerView.Adapter<UserSettingsRecycleAdapter.ViewHolder>(){

    //onClickListener setup
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
     mListener = listener
    }

    inner class ViewHolder(itemView : View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val settingsView: TextView = itemView.findViewById(R.id.settingsTextView)
        var userSettingsView : TextView = itemView.findViewById(R.id.userSettingsTextview)
        val recyclerCard : ConstraintLayout = itemView.findViewById(R.id.usersettings_constraint)


        init {

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.usersettings_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    //If statement looks for the text view-text, and if it's equal to "Sign out" it changes the background color to red
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = settingsList[position]

        holder.settingsView.text = listItem.settings.toString()
        holder.userSettingsView.text = listItem.userSetting.toString()

        if (holder.settingsView.text == "Sign out"){
            holder.recyclerCard.setBackgroundColor(Color.parseColor("#FFBCA5"))
        }

    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

}