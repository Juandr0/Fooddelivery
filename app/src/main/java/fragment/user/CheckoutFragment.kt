package fragment.user

import adapters.OrderRecyclerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import com.example.fooddeliveryproject.R

class CheckoutFragment : Fragment() {

    lateinit var restaurantHeaderTextView : TextView
    lateinit var totalPriceTextView : TextView
    lateinit var orderPriceTotal : TextView
    lateinit var orderMoreBtn : Button
    lateinit var placeOrderBtn : Button
    lateinit var deliveryFeePrice : TextView
    lateinit var vespaIcon : ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantHeaderTextView = view.findViewById(R.id.checkout_restaurantHeaderTextView)
        totalPriceTextView = view.findViewById(R.id.checkout_totalPriceTextView)
        orderPriceTotal = view.findViewById(R.id.checkout_orderPriceTotal)
        orderMoreBtn = view.findViewById(R.id.checkout_orderMoreBtn)
        placeOrderBtn = view.findViewById(R.id.checkout_placeOrderBtn)
        deliveryFeePrice = view.findViewById(R.id.checkout_deliveryFeePrice)
        vespaIcon = view.findViewById(R.id.checkout_icon_delivery)
        //Lägg till fraktkostnad image + textview som visar pris
        hideShowDeliveryFee()

        val recyclerView = view.findViewById<RecyclerView>(R.id.checkout_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderRecyclerAdapter(CheckoutFragment(), ShoppingCart.currentOrderList)
        recyclerView.adapter = adapter

        //initializes the total price
        orderPriceTotal()


        //sets the fragment to explore
        orderMoreBtn.setOnClickListener {
            setCurrentFragment(ExploreFragment(), null)
            hideShowDeliveryFee()
        }

        //Sends the user to the confirmation fragment after sending info to DB
        placeOrderBtn.setOnClickListener {
            //setCurrentFragment(orderConfirmationFragment(), null)
        }

        // Calls on adapter function that removes the item from the recycler list
        // Then it updates the price-total by calling on orderPriceTotal function
        adapter.setOnItemClickListener(object : OrderRecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                adapter.removeItemFromReyclerView(position)
                orderPriceTotal()
                hideShowDeliveryFee()
            }
        })

        //Gets the restaurant name for the header
        setTextHeader()
    }

    fun setTextHeader(){

        if (ShoppingCart.getRestaurantName() == ""){
            restaurantHeaderTextView.text = getString(R.string.empty_orderlist)
            } else {
            restaurantHeaderTextView.text = ShoppingCart.getRestaurantName()
        }
    }

    fun hideShowDeliveryFee(){
        if (ShoppingCart.calculateTotalPrice() == 0){
            this.deliveryFeePrice.visibility=(View.INVISIBLE)
            Log.d("!!!", "0")
        } else {
            this.deliveryFeePrice.visibility=(View.VISIBLE)
        }
    }

    fun setCurrentFragment(fragment : Fragment, bundle: Bundle?){

        val fragmentManager = parentFragmentManager
        fragment.arguments = bundle
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    fun orderPriceTotal(){
        var totalprice = ShoppingCart.calculateTotalPrice()
        orderPriceTotal.text = totalprice.toString() + ":-"
    }
}

