package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import classes.OrderItem
import classes.ShoppingCart
import com.example.fooddeliveryproject.R
import fragment.user.CheckoutFragment

class OrderRecyclerAdapter
    (val context : CheckoutFragment,
     val orderItemList : List<OrderItem>
     ) :
    RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_recyclerlayout, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderItemList[position]

        holder.order.text = order.orderFromMeny
        holder.orderPrice.text = order.price.toString() + ":-"
    }

    override fun getItemCount() = orderItemList.size


    inner class ViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val order: TextView = itemView.findViewById(R.id.checkout_menu_itemTextView)
        val orderPrice : TextView = itemView.findViewById(R.id.checkout_order_priceTextView)

        val trashCan : ImageView = itemView.findViewById(R.id.checkout_trashCanImageView)


        init {
            trashCan.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }


    }

    //Removes item from reyclerview list and updates the recyclerview
    fun removeItemFromReyclerView(positon : Int){
        ShoppingCart.removeItemFromCart(positon)
        notifyDataSetChanged()
    }


}