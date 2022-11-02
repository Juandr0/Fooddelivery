package fragment.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import classes.OrderItem
import classes.ShoppingCart
import com.example.fooddeliveryproject.R


class TEMPORARY_MENU_FRAGMENT : Fragment() {
    lateinit var restaurant : TextView
    lateinit var menuItem : TextView
    lateinit var addButton : Button
    lateinit var prisText : TextView
    lateinit var pris : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_t_e_m_p_o_r_a_r_y__m_e_n_u__f_r_a_g_m_e_n_t,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurant = view.findViewById(R.id.order_restaurantNameTextView)
        menuItem = view.findViewById(R.id.order_menyItemTextView)
        addButton = view.findViewById(R.id.order_addToOrderButton)
        prisText = view.findViewById(R.id.order_pris)
        pris = view.findViewById(R.id.order_priceTextView)

        addButton.setOnClickListener {
            val orderRestaurant = restaurant.text.toString()
            val orderMenuItem = menuItem.text.toString()
            val orderPrice = pris.text.toString().toDouble()
            val newOrder = OrderItem(orderRestaurant, orderMenuItem, orderPrice)
            ShoppingCart.addItemToCart(newOrder)
            Log.d("!!!", ShoppingCart.userItems.size.toString())
        }
    }


}