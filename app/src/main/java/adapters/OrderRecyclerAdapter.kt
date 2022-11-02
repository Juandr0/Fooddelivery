package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import classes.OrderItem
import com.example.fooddeliveryproject.R
import fragment.user.CheckoutFragment

class OrderRecyclerAdapter
    (val context : CheckoutFragment,
     val orderItemList : List<OrderItem>
     ) :
    RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_recyclerlayout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderItemList[position]
        holder.order.text = order.orderFromMeny + ":"
        holder.orderPrice.text = order.price.toString()
        holder.deliveryFeePrice.text = order.deliveryFee.toString()

    }

    override fun getItemCount() = orderItemList.size


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val order: TextView = itemView.findViewById(R.id.checkout_menu_itemTextView)
        val orderPrice : TextView = itemView.findViewById(R.id.checkout_order_priceTextView)
        val deliveryFee : ImageView = itemView.findViewById(R.id.checkout_order_deliveryFeeIcon)
        val deliveryFeePrice : TextView =  itemView.findViewById(R.id.checkout_order_deliveryFeePrice)

    }
}