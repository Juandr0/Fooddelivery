package com.example.fooddeliveryproject

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fragment.user.ProfileFragment

class UserSettingsRecycleAdapter
    (val context : ProfileFragment,
     val settingsList : List<UserSettings>
     ) :
    RecyclerView.Adapter<UserSettingsRecycleAdapter.ViewHolder>(){

    var backgroundColorPicker : Boolean = true

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

        val settingsView: TextView = itemView.findViewById(R.id.userAttributeToChangeTextView)
        var userSettingsView : TextView = itemView.findViewById(R.id.userAttributeToChangeEditText)
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
    //Otherwise it turns the background of the recyclerview to
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = settingsList[position]
        val backgroundColor = backgroundColorChanger()
        holder.settingsView.text = listItem.settings.toString()
        holder.userSettingsView.text = listItem.userSetting.toString()


        if (holder.settingsView.text == "Sign out"){
            holder.recyclerCard.setBackgroundColor(Color.parseColor("#FFBCA5"))
        } else {
            holder.recyclerCard.setBackgroundColor(Color.parseColor(backgroundColor))
        }

    }

    fun backgroundColorChanger() : String{
        val colorLighterGrey = "#FFFFFF"
        val colorDarkerGrey = "#F3F3F3"
        backgroundColorPicker = !backgroundColorPicker

        if (backgroundColorPicker) return colorLighterGrey
        else return colorDarkerGrey

    }


    override fun getItemCount(): Int {
        return settingsList.size
    }

}