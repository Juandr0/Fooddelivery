package fragment.user

import adapters.OrderConfirmationRecyclerAdapter
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import classes.User
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import com.google.type.Date
import java.lang.String.format
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class OrderConfirmationFragment : Fragment() {

    var itemsList = mutableListOf<String>()

    lateinit var orderNumber : TextView
    lateinit var orderPrice : TextView
    lateinit var orderAddress : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderNumber = view.findViewById(R.id.confirmation_OrderNumber)
        orderPrice = view.findViewById(R.id.confirmation_totalPriceTextView)
        orderAddress = view.findViewById(R.id.confirmation_deliveryAddressTextview)

        initiateBundleToViews()
        initalizeRecyclerView(view)
        setTextViews()

        ShoppingCart.clearItemsFromCart()
    }

    //takes local time and adds 30 minutes
    private fun getTimePlusThrityMinutes() : String{
        val currentTime = LocalTime.now().plus(Duration.of(30, ChronoUnit.MINUTES))
            .format((DateTimeFormatter.ofPattern("HH:mm")))
        return currentTime.toString()
    }


    private fun setTextViews(){
        val price = ShoppingCart.calculateTotalPrice()
        orderPrice.text = price.toString() + ":-"


        //Gets the current time and the user address and displays it in textview
        val currentTime = getTimePlusThrityMinutes()
        val currentUser = auth.currentUser!!
        var user = User()
        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { document ->
                user = document.toObject<User>()!!
                orderAddress.text = getString(R.string.delivery_information) + " " + user.address + " " + "at $currentTime"
            }

    }




    //Gets bundle info of order items and adds it to the itemlist
    private fun initiateBundleToViews(){
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