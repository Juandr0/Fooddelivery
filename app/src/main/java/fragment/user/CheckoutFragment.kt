package fragment.user

import adapters.OrderRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import com.example.fooddeliveryproject.R

class CheckoutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.checkout_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderRecyclerAdapter(CheckoutFragment(), ShoppingCart.userItems)
        recyclerView.adapter = adapter

    }




}