package com.example.fooddeliveryproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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

        val settingsView = itemView.findViewById<TextView>(R.id.settingsTextView)
        var userSettingsView = itemView.findViewById<TextView>(R.id.userSettingsTextview)


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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = settingsList[position]

        holder.settingsView.text = listItem.settings.toString()
        holder.userSettingsView.text = listItem.userSetting.toString()

    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

}