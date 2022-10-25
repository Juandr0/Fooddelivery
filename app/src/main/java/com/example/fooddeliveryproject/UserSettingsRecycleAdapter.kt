package com.example.fooddeliveryproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fragment.user.ProfileFragment

class UserSettingsRecycleAdapter
    (val context : ProfileFragment,
     val settingsList : List<UserSettings>
     ) :
    RecyclerView.Adapter<UserSettingsRecycleAdapter.ViewHolder>(){

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val settingsView = itemView.findViewById<TextView>(R.id.settingsTextView)
        val userSettingsView = itemView.findViewById<TextView>(R.id.userSettingsTextview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.usersettings_list, parent, false)
        return ViewHolder(itemView)
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