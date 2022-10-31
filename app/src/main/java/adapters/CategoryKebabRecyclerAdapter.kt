package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import classes.Restaurant
import com.bumptech.glide.Glide
import com.example.fooddeliveryproject.R
import fragment.user.CategoryKebabFragment

class CategoryKebabRecyclerAdapter(val context: CategoryKebabFragment, val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<CategoryKebabRecyclerAdapter.ViewHolder>() {


    //onClickListener setup
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_restaurants_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }//End of clickListener



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Takes the right restaurant from the list
        val restaurant = restaurants[position]
        //Assigns the right information to the restaurant
//        holder.restaurantHeaderImageView.setImageResource(restaurant.image)
        holder.restaurantNameView.text = restaurant.name
        holder.restaurantRatingView.text = restaurant.rating.toString()
        holder.restaurantDeliveryFeeView.text = "${restaurant.deliveryFee} kr"

        //Glide for image
        Glide.with(context)
            .load(restaurant.image)
            .into(holder.restaurantHeaderImageView)

    }

    //counts how many restaurants there are in the list
    override fun getItemCount(): Int {
        return restaurants.size
    }


    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //When a viewHolder is created, it finds the ImageView and textViews in our itemView
        var restaurantHeaderImageView = itemView.findViewById<ImageView>(R.id.restaurantHeaderImageVIew)
        var restaurantNameView = itemView.findViewById<TextView>(R.id.restaurantNameView)
        var restaurantRatingView = itemView.findViewById<TextView>(R.id.restaurantRatingView)
        var restaurantDeliveryFeeView = itemView.findViewById<TextView>(R.id.restaurantDeliveryFeeView)

        //init of the clicklistener that checks position
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }


}