package fragment.user

import adapters.OrderRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import com.example.fooddeliveryproject.R

class CheckoutFragment : Fragment() {

    lateinit var checkout_restaurantHeaderTextView : TextView
    lateinit var checkout_totalPriceTextView : TextView
    lateinit var checkout_orderPriceTotal : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkout_restaurantHeaderTextView = view.findViewById(R.id.checkout_restaurantHeaderTextView)
        checkout_totalPriceTextView = view.findViewById(R.id.checkout_totalPriceTextView)
        checkout_orderPriceTotal = view.findViewById(R.id.checkout_orderPriceTotal)

        val recyclerView = view.findViewById<RecyclerView>(R.id.checkout_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderRecyclerAdapter(CheckoutFragment(), ShoppingCart.currentOrderList)
        recyclerView.adapter = adapter

        var totalprice = calculateTotal()
        checkout_orderPriceTotal.text = totalprice.toString() + ":-"
    }



    private fun calculateTotal() : Int{
        var counter = 0

        var totalPrice = 0
        for (OrderItem in ShoppingCart.currentOrderList){
            totalPrice += ShoppingCart.currentOrderList[counter].price
            counter++
        }

            return totalPrice
    }

}

