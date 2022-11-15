package fragment.user

import adapters.OrderConfirmationRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.ShoppingCart
import classes.User
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class OrderConfirmationFragment : Fragment() {

    var itemsList = mutableListOf<String>()

    lateinit var orderNumber : TextView
    lateinit var orderPrice : TextView
    lateinit var orderAddress : TextView
    lateinit var salesTax : TextView

    //Restaurant rating
    lateinit var imageStar1 : ImageView
    lateinit var imageStar2 : ImageView
    lateinit var imageStar3 : ImageView
    lateinit var imageStar4 : ImageView
    lateinit var imageStar5 : ImageView

    lateinit var imageStarText1 : TextView
    lateinit var imageStarText2 : TextView
    lateinit var imageStarText3 : TextView
    lateinit var imageStarText4 : TextView
    lateinit var imageStarText5 : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageStar1 = view.findViewById(R.id.confirmation_star1)
        imageStar2 = view.findViewById(R.id.confirmation_star2)
        imageStar3 = view.findViewById(R.id.confirmation_star3)
        imageStar4 = view.findViewById(R.id.confirmation_star4)
        imageStar5 = view.findViewById(R.id.confirmation_star5)

        imageStarText1 = view.findViewById(R.id.confirmation_star1TextView)
        imageStarText2 = view.findViewById(R.id.confirmation_star2TextView)
        imageStarText3 = view.findViewById(R.id.confirmation_star3TextView)
        imageStarText4 = view.findViewById(R.id.confirmation_star4TextView)
        imageStarText5 = view.findViewById(R.id.confirmation_star5TextView)

        orderNumber = view.findViewById(R.id.confirmation_OrderNumber)
        orderPrice = view.findViewById(R.id.confirmation_totalPriceTextView)
        orderAddress = view.findViewById(R.id.confirmation_deliveryAddressTextview)
        salesTax = view.findViewById(R.id.confirmation_salesTaxTextView)

        activateRatingClickListener()
        initiateBundleToViews()
        setTextViews()
        initalizeRecyclerView(view)

        //Hämta restaurangnamn

        ShoppingCart.clearItemsFromCart()
    }

    private fun activateRatingClickListener(){
        imageStar1.setOnClickListener { updateDbRating(1) }
        imageStarText1.setOnClickListener{ updateDbRating(1) }

        imageStar2.setOnClickListener { updateDbRating(2) }
        imageStarText2.setOnClickListener{ updateDbRating(2) }

        imageStar3.setOnClickListener { updateDbRating(3) }
        imageStarText3.setOnClickListener{ updateDbRating(3) }

        imageStar4.setOnClickListener { updateDbRating(4) }
        imageStarText4.setOnClickListener{ updateDbRating(4) }

        imageStar5.setOnClickListener { updateDbRating(5) }
        imageStarText5.setOnClickListener{ updateDbRating(5) }
    }

    private fun updateDbRating(rating : Int){
        //Göm bilderna, visa textview som säger "Thank you for your feedback"
    }

    //takes local time and adds 30 minutes
    private fun getTimePlusThrityMinutes() : String{
        val currentTime = LocalTime.now().plus(Duration.of(30, ChronoUnit.MINUTES))
            .format((DateTimeFormatter.ofPattern("HH:mm")))
        return currentTime.toString()
    }


    private fun setTextViews(){
        val price = ShoppingCart.calculateTotalPrice()
        orderPrice.text =getString(R.string.order_total) + " " + price.toString() + ":-"

        var taxPercentile = 0.12
        salesTax.text = getString(R.string.sales_tax) + " " +  price * taxPercentile + ":-"


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
        val orderNumberBundle = args?.getInt("orderNumber")
        val lastOrder = args?.getStringArrayList("bundleList")

        if (lastOrder != null) {
            for (item in lastOrder){
                itemsList.add(item)
            }
        }
        orderNumber.text = getString(R.string.ordernumber) + " " + orderNumberBundle.toString()

    }

    private fun initalizeRecyclerView(view : View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.confirmation_orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderConfirmationRecyclerAdapter(OrderConfirmationFragment(), itemsList)
        recyclerView.adapter = adapter
    }

}