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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import classes.User
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import java.util.*
import kotlin.collections.ArrayList

class CheckoutFragment : Fragment() {

    lateinit var restaurantHeaderTextView : TextView
    lateinit var totalPriceTextView : TextView
    lateinit var orderPriceTotal : TextView
    lateinit var orderMoreBtn : Button
    lateinit var placeOrderBtn : Button
    lateinit var deliveryFeePrice : TextView
    lateinit var vespaIcon : ImageView


    val newOrderItemList = mutableListOf<String>()
    var userAddress : String = ""
    var orderNumber = 0
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

        hideShowDeliveryFee()
        orderPriceTotal.text = ShoppingCart.calculateTotalPrice().toString() +":-"

        val recyclerView = view.findViewById<RecyclerView>(R.id.checkout_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderRecyclerAdapter(CheckoutFragment(), ShoppingCart.currentOrderList)
        recyclerView.adapter = adapter

        //Gets ordernr & calculate total price

        setDeliveryFee()
        setOrderNr()

        //sets the fragment to explore
        orderMoreBtn.setOnClickListener {
            setCurrentFragment(ExploreFragment(), null)
            hideShowDeliveryFee()
        }

        //Sends the user to the confirmation fragment after sending info to DB
        placeOrderBtn.setOnClickListener {
            initiateOrderList()
            val currentUser = auth.currentUser
            if (currentUser != null){
                var bundle = initiateBundle()
                if (ShoppingCart.currentOrderList.isNotEmpty()){
                    sendOrderToDb()
                    setCurrentFragment(OrderConfirmationFragment(), bundle)
                } else {
                    Toast.makeText(activity, getString(R.string.empty_shoppingcart), Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(activity, getString(R.string.sign_in_to_order), Toast.LENGTH_SHORT).show()
            }

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

    fun setDeliveryFee() {
        var deliveryFee = ShoppingCart.getDeliveryFee()
        deliveryFeePrice.text = deliveryFee.toString()
    }
    fun setOrderNr() {

        db.collection("orders").document("orderNumber").get()
            .addOnSuccessListener { document ->
                if (document != null){
                    orderNumber = document.get("orderNumber").toString().toInt()
                }
                orderNumber++
                val nr = mapOf(
                    "orderNumber" to orderNumber
                )
                db.collection("orders").document("orderNumber").set(nr)
            }
    }
    //Sends info to checkout page
    fun initiateBundle() : Bundle{

        val bundle = Bundle()
        var bundleList = ArrayList<String>()

        for (item in newOrderItemList){
            bundleList.add(item)
            Log.d("!!!", "bundle item")
        }

        bundle.putStringArrayList("bundleList", bundleList)

        bundle.putInt("price", ShoppingCart.calculateTotalPrice())
        bundle.putInt("orderNumber", orderNumber)
        bundle.putString("restaurantName", restaurantHeaderTextView.text.toString())
        return bundle
    }

    fun initiateOrderList(){
        //For loop that saves the order items to currentORderList, then makes a map of it
        //-> Sends all info to the db
        var index = 0
        for (item in ShoppingCart.currentOrderList){
            val orderItem = ShoppingCart.currentOrderList[index].orderFromMeny
            newOrderItemList.add(orderItem)
            index ++
        }
    }
    //fetches order from ShoppingCart and adds it into separate list which will be sent to DB
    fun sendOrderToDb() {


        //Create random order number here or return the autogenerated id for the DB-collection

        val restaurant = ShoppingCart.currentOrderList[0].restaurantName
        val totalPrice = ShoppingCart.calculateTotalPrice()


        var user = User()
        //Gör om till ny lista



        val currentUser = db.collection("users").document(auth.currentUser!!.uid)
        currentUser.get()
            .addOnSuccessListener { document ->
                user = document.toObject<User>()!!
                var date = Calendar.getInstance().time
                //Adress skickas med bundle som går till orderconfirmation
                userAddress = user.address.toString()


                val dataToBeSent = hashMapOf(
                    "restaurant" to  restaurant ,
                    "totalPrice" to totalPrice,
                    "orderItems" to newOrderItemList,
                    "user" to user,
                    "purchaseDate" to date,
                    "orderNumber" to orderNumber
                )


                val docRef = db.collection("orders").document(auth.currentUser!!.uid).collection("order")
                    docRef.add(dataToBeSent)
                    .addOnSuccessListener {
                        updateUserLastOrder()
                    }
                    .addOnFailureListener{ e ->
                        Log.d("!!!","Failed to send order" + e)
                    }

            }

    }


    fun updateUserLastOrder() {

        val currentUser = auth.currentUser!!.uid
        val docRef = db.collection("users").document(currentUser)

        val updateMap = mapOf(
            "lastOrderRestaurant" to  restaurantHeaderTextView.text.toString(),
            "lastOrder" to newOrderItemList
        )
        docRef.update(updateMap)

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

