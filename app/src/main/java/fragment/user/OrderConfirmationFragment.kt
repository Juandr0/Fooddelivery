package fragment.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import classes.ShoppingCart
import com.example.fooddeliveryproject.R

class OrderConfirmationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments

        val lastOrder = args?.getStringArrayList("bundleList")
        if (lastOrder != null) {
            for (item in lastOrder){
                Log.d("!!!",item)
            }
        }

        var totalPrice = ShoppingCart.calculateTotalPrice()
    }

}