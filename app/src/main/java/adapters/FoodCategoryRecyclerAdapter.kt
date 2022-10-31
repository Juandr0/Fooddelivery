package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import classes.FoodCategory
import com.example.fooddeliveryproject.R
import fragment.user.ExploreFragment


class FoodCategoryRecyclerAdapter(val context: ExploreFragment, val foodCategories: List<FoodCategory>) :
    RecyclerView.Adapter<FoodCategoryRecyclerAdapter.ViewHolder>() {

    //onClickListener setup
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_categories_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Takes the right foodCategory from the list
        val foodCategory = foodCategories[position]
        //Assigns the right image
        holder.categoryImageVIew.setImageResource(foodCategory.image)

    }

    override fun getItemCount(): Int {
        //counts how many categories there are in the list
        return foodCategories.size
    }


    class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //When a viewHolder is created, it finds the ImageView in our itemView
        var categoryImageVIew = itemView.findViewById<ImageView>(R.id.foodCategoryImageView)

        //init of the clicklistener that checks position
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


}