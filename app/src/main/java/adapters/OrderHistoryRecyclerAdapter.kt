package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.OrderHistory
import com.example.fooddeliveryproject.R
import com.google.firebase.auth.ktx.actionCodeSettings
import fragment.user.OrderHistoryFragment

class OrderHistoryRecyclerAdapter(val context : OrderHistoryFragment, val orderHistoryList : List<OrderHistory>, )
    : RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder>(){

    lateinit var thisContext : Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        thisContext = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerlayout_orderhistory, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantView.text = orderHistoryList[position].restaurantName
        holder.dateView.text = orderHistoryList[position].dateOfPurchase.toString()
        holder.priceView.text = orderHistoryList[position].price.toString() + ":-"

        initiateOrderItemsRecyclerView(holder.orderItemRecyclerView, orderHistoryList[position].orderItem!!)
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val restaurantView : TextView = itemView.findViewById(R.id.orderHistory_restaurant)
        val dateView : TextView = itemView.findViewById(R.id.orderHistory_date)
        val priceView : TextView = itemView.findViewById(R.id.orderHistory_price)
        var orderItemRecyclerView  : RecyclerView = itemView.findViewById(R.id.orderHistory_orderItemRecyclerView)

        init {
            orderItemRecyclerView = itemView.findViewById(R.id.orderHistory_orderItemRecyclerView)
        }
    }

    private fun initiateOrderItemsRecyclerView(recyclerView: RecyclerView, orderItems : List<String>){
        val recyclerAdapter = OrderHistoryChildRecyclerAdapter(context, orderItems)
        recyclerView.layoutManager = LinearLayoutManager(thisContext)
        recyclerView.adapter = recyclerAdapter
    }

}