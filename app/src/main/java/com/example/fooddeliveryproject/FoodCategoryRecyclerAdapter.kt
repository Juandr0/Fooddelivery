package com.example.fooddeliveryproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fragment.ExploreFragment


class FoodCategoryRecyclerAdapter(val context: ExploreFragment, val foodCategories: List<FoodCategory>) :
    RecyclerView.Adapter<FoodCategoryRecyclerAdapter.ViewHolder>() {

    //onClickListener setup
    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{

        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


  //  val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_categories_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //när en list_view ska visas så tar vi rätt person från vår lista
        val foodCategory = foodCategories[position]
        //sätter in bild
        holder.categoryImageVIew.setImageResource(foodCategory.image)

    }

    //räknar hur många det finns i listan
    override fun getItemCount(): Int {
        return foodCategories.size
    }


    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        //när en viewholder skapas så letar vi reda på två textviews som finns inne i vår itemview
        // (vår itemview är skapad utifrån vår list_item layout)
        var categoryImageVIew = itemView.findViewById<ImageView>(R.id.foodCategoryImageView)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }



}