package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.fooddeliveryproject.R
import fragment.user.ProfileFragment

class LastOrderRecyclerAdapter (var context : ProfileFragment, var orderItemList : List<String>)
    :RecyclerView.Adapter<LastOrderRecyclerAdapter.ViewHolder>()
{




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_childrecycler_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = orderItemList[position]
        holder.orderItem.text = item
    }

    override fun getItemCount(): Int {
        return orderItemList.size
    }




    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val orderItem = itemView.findViewById<TextView>(R.id.orderChildTextView)
    }
}