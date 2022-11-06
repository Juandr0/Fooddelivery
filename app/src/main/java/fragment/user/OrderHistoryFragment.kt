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
   // val orderItems = mutableListOf<String>()

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
        val adapter = OrderHistoryRecyclerAdapter(OrderHistoryFragment(), userOrderList)
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
                    var date = document.getDate("purchaseDate").toString()
                    var price = document.getLong("totalPrice")

                    //Själva beställningarna ska läggas in i separat lista som sen ska visas i textview på något sätt
                    //var order = document.getString("orderItems")

                    newOrder.restaurantName = restaurant
                    newOrder.dateOfPurchase = date
                    newOrder.price = price!!.toInt()
                    newOrder.order = "test"

                    userOrderList.add(newOrder)
                    }

                initializeRecyclerView(view)
    }

}
}



