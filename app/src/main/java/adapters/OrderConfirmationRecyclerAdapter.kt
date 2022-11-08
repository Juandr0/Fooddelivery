package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryproject.R
import fragment.user.OrderConfirmationFragment

class OrderConfirmationRecyclerAdapter (val context : OrderConfirmationFragment, val itemsList: List<String>)
    : RecyclerView.Adapter<OrderConfirmationRecyclerAdapter.ViewHolder>() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater.from(parent.context)
        .inflate(R.layout.recyclerview_orderitems, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList[position]
        holder.orderitem.text = item

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderitem = itemView.findViewById<TextView>(R.id.recycler_confirmation_orderitemsTextView)
    }
}