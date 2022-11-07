package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryproject.R
import fragment.user.OrderHistoryFragment

class OrderHistoryChildRecyclerAdapter (val context : OrderHistoryFragment, val orderList : List<String>) :
    RecyclerView.Adapter<OrderHistoryChildRecyclerAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_childrecycler_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orderList[position]
        holder.orderItem.text = item

    }

    override fun getItemCount() = orderList.size


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val orderItem = itemView.findViewById<TextView>(R.id.childRecycler_OrderItem)

    }

}





