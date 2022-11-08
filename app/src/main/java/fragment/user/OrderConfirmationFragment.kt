package fragment.user

import adapters.OrderConfirmationRecyclerAdapter
import adapters.OrderRecyclerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import com.example.fooddeliveryproject.R

class OrderConfirmationFragment : Fragment() {
    var itemsList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleInfo()
        initalizeRecyclerView(view)
        var totalPrice = ShoppingCart.calculateTotalPrice()
    }


    //Gets bundle info of order items and adds it to the itemlist
    private fun getBundleInfo(){
        val args = this.arguments
        val lastOrder = args?.getStringArrayList("bundleList")
        if (lastOrder != null) {
            for (item in lastOrder){
                itemsList.add(item)
            }
        }
    }

    private fun initalizeRecyclerView(view : View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.confirmation_orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderConfirmationRecyclerAdapter(OrderConfirmationFragment(), itemsList)
        recyclerView.adapter = adapter
    }

}