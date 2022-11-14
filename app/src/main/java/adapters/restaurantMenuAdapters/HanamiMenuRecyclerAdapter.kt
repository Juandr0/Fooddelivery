package adapters.restaurantMenuAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import classes.OrderItem
import classes.ShoppingCart
import com.example.fooddeliveryproject.R
import fragment.restaurantMenu.HanamiMenuFragment

class HanamiMenuRecyclerAdapter (val context: HanamiMenuFragment, val orderItems: List<OrderItem>) :
    RecyclerView.Adapter<HanamiMenuRecyclerAdapter.ViewHolder>() {


    //onClickListener setup
    private lateinit var mListener: onItemClickListener
    private lateinit var thisContext : Context

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        thisContext = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_menu_list_item, parent, false)
        return ViewHolder(itemView, mListener)


    }//End of clickListener


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Takes the right restaurant from the list
        val orderItem = orderItems[position]
//        val orderRestaurantName = orderItem.restaurantName
//        val orderDeliveryFee = orderItem.deliveryFee
        //Assigns the right information to the restaurant
        holder.orderItemNameView.text = orderItem.orderFromMeny
        holder.orderItemNPriceView.text = "${orderItem.price} kr"

        holder.orderRestaurantName = orderItem.restaurantName
        holder.orderDeliveryFee = orderItem.deliveryFee
        holder.orderItemPrice = orderItem.price
        holder.orderItemName = orderItem.orderFromMeny
        holder.orderItemID = orderItem.itemID




//        //Glide for image
//        Glide.with(context)
//            .load(restaurant.image)
//            .into(holder.restaurantHeaderImageView)

    }

    //counts how many restaurants there are in the list
    override fun getItemCount(): Int {
        return orderItems.size
    }


    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //When a viewHolder is created, it finds the ImageView and textViews in our itemView
        var orderItemNameView = itemView.findViewById<TextView>(R.id.orderItemNameView)
        var orderItemNPriceView = itemView.findViewById<TextView>(R.id.orderItemPriceView)

        var orderRestaurantName = ""
        var orderDeliveryFee = 0
        var orderItemPrice = 0
        var orderItemName = ""
        var orderItemID = ""

        var orderButton = itemView.findViewById<Button>(R.id.addToOrderButton).setOnClickListener{
            val orderRestaurant = orderRestaurantName
            val orderMenuItem = orderItemName
            val orderPrice = orderItemPrice
            val deliveryFee = orderDeliveryFee
            val orderID = orderItemID
            val newOrder = OrderItem(orderRestaurant, orderMenuItem, orderID, orderPrice, deliveryFee)
            ShoppingCart.addItemToCart(newOrder, thisContext)
        }

        //init of the clicklistener that checks position
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }



    }

}