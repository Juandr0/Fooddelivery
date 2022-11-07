package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import classes.OrderHistory
import com.example.fooddeliveryproject.R
import fragment.user.OrderHistoryFragment

class OrderHistoryRecyclerAdapter(val context : OrderHistoryFragment, val orderHistoryList : List<OrderHistory>)
    : RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerlayout_orderhistory, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantView.text = orderHistoryList[position].restaurantName
        // Kan behöva ändra pga att order är en lista
        holder.dateView.text = orderHistoryList[position].dateOfPurchase.toString()
        holder.priceView.text = orderHistoryList[position].price.toString() + ":-"

    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val restaurantView : TextView = itemView.findViewById(R.id.orderHistory_restaurant)
        //val orderView  : TextView = itemView.findViewById(R.id.orderHistory_order)
        val dateView : TextView = itemView.findViewById(R.id.orderHistory_date)
        val priceView : TextView = itemView.findViewById(R.id.orderHistory_price)
    }


}