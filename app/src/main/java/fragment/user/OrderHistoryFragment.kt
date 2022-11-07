package fragment.user

import adapters.OrderHistoryRecyclerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.OrderHistory
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.getField

class OrderHistoryFragment : Fragment() {

    val userOrderList = mutableListOf<OrderHistory>()
    //var orderItemList = mutableListOf<List<String>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrderHistory(view)
    }


    private fun initializeRecyclerView(view : View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.orderhistory_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderHistoryRecyclerAdapter(OrderHistoryFragment(), userOrderList, /*orderItemList*/)
        recyclerView.adapter = adapter
    }

    private fun getOrderHistory(view : View) {
        userOrderList.clear()
        val currentUser = auth.currentUser
        val docRef = db.collection("orders").document(currentUser!!.uid).collection("order")
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                for (document in documentSnapshot.documents){
                    var newOrder = OrderHistory()


                    var restaurant = document.getString("restaurant")
                    var date = document.getDate("purchaseDate")
                    var price = document.getLong("totalPrice")
                    var tempItemList = document.get("orderItems") as List<String>


                    newOrder.orderItem = tempItemList
                    newOrder.restaurantName = restaurant
                    newOrder.dateOfPurchase = date
                    newOrder.price = price!!.toInt()


                    userOrderList.add(newOrder)
                    }

                //Sorts list by date, then reverses the list to have the last order on the top of the list
                userOrderList.sortBy{it.dateOfPurchase}
                userOrderList.reverse()
                initializeRecyclerView(view)
    }

}
}



