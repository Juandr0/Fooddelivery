package fragment.user

import adapters.OrderHistoryRecyclerAdapter
import adapters.UserSettingsRecycleAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.OrderHistory
import classes.User
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firestore.v1.StructuredQuery.Order

class OrderHistoryFragment : Fragment() {
    val userOrderList = mutableListOf<OrderHistory>()

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

        var order = OrderHistory()
        val currentUser = auth.currentUser
        val docRef = db.collection("orders").document(currentUser!!.uid).collection("order")
        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result){
                    val dbOrder = document.toObject<OrderHistory>()
                    if (dbOrder != null){
                        //Fixa till klassen så den kan ta in alla värden som finns i databasen
                        order.restaurantName = dbOrder.restaurantName
                        order.order = "test"
                        order.dateOfPurchase = dbOrder.dateOfPurchase
                        userOrderList.add(order)
                    }
                }
                initializeRecyclerView(view)
                }




            }


    }
