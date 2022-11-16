package fragment.user

import adapters.OrderConfirmationRecyclerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.Restaurant
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

    lateinit var ratingTextView : TextView
    //Restaurant rating END

    lateinit var restaurantName : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Rating
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

        ratingTextView = view.findViewById(R.id.confirmation_rateTextView)
        //Rating ENDS


        orderNumber = view.findViewById(R.id.confirmation_OrderNumber)
        orderPrice = view.findViewById(R.id.confirmation_totalPriceTextView)
        orderAddress = view.findViewById(R.id.confirmation_deliveryAddressTextview)
        salesTax = view.findViewById(R.id.confirmation_salesTaxTextView)


        initiateBundleToViews()
        activateRatingClickListener()
        setTextViews()
        initalizeRecyclerView(view)

        //Hämta restaurangnamn

        ShoppingCart.clearItemsFromCart()
    }


    //Deactivates clicklisteners, pass rating to DB, update textview
    private fun updateRating(rating : Int){
        deactivateClickListeners()
        updateDb(rating)
        ratingTextView.text = getString(R.string.rate_feedback)
    }


    //Fetches restaurant pathway from user collections
    //Fetches restaurant info from restaurant collection
    //Calculates new rating
    //Updates rating

    private fun updateDb(rating : Int){
    var currentRestaurant : User
    var restaurantId = ""
        val docRef = db.collection("users").whereEqualTo("name", restaurantName)
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    currentRestaurant = document.toObject()
                    restaurantId = currentRestaurant.menuId.toString()
                }

                //Fetch restaurant rating data, and update it
                var restaurantInfo : Restaurant
                val restaurantRef = db.collection("restaurants").document(restaurantId)
                restaurantRef.get()
                    .addOnSuccessListener { document ->
                        restaurantInfo = document.toObject()!!

                        var totalRating : Double
                        var totalVotes : Int


                        totalRating = restaurantInfo.totalRating + rating
                        totalVotes = restaurantInfo.totalVotes
                        totalVotes++

                        if (totalVotes == 0) {
                            totalVotes = 1
                        }

                        val newRating = (totalRating/totalVotes)
                        val newRatingWithTwoDecimals : Double = String.format("%2f", newRating).toDouble()

                        val mapUpdate = mapOf(
                            "rating" to newRatingWithTwoDecimals,
                            "totalRating" to totalRating,
                            "totalVotes" to totalVotes
                        )

                        restaurantRef.update(mapUpdate)
                    }

            }
    }

    private fun deactivateClickListeners(){
        imageStar1.setOnClickListener (null)
        imageStarText1.setOnClickListener (null)

        imageStar2.setOnClickListener (null)
        imageStarText2.setOnClickListener (null)

        imageStar3.setOnClickListener (null)
        imageStarText3.setOnClickListener (null)

        imageStar4.setOnClickListener (null)
        imageStarText4.setOnClickListener (null)

        imageStar5.setOnClickListener (null)
        imageStarText5.setOnClickListener (null)
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

        //calculates the VAT and rounds it to 2 decimals before displaying it, so that the user do not see numbers like "19.9999999999999998"
        var taxPercentile = 0.12
        val totalTax = price * taxPercentile
        val taxPercentile2digits = String.format("%.2f", totalTax).toDouble()

        salesTax.text = getString(R.string.sales_tax) + " " + taxPercentile2digits + ":-"


        ratingTextView.text = getString(R.string.rate_restaurant, restaurantName)

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
        restaurantName = args?.getString("restaurantName")!!

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


    //  Activates clicklistener and passes the equivalent response to update rating function
    private fun activateRatingClickListener(){

        //  Sets textview to default text if user already have rated a restaurant
        ratingTextView.text = getString(R.string.rate_restaurant)
        var ratingNumber : Int
        // If user press star number one, or the textview below it
        imageStar1.setOnClickListener {
            ratingNumber = 1
            updateRating(ratingNumber)}
        imageStarText1.setOnClickListener{
            ratingNumber = 1
            updateRating(ratingNumber)
        }


        // If user press star number two, or the textview below it
        imageStar2.setOnClickListener {
            ratingNumber = 2
            updateRating(ratingNumber)
        }
        imageStarText2.setOnClickListener{
            ratingNumber = 2
            updateRating(ratingNumber)
        }


        // If user press star number three, or the textview below it
        imageStar3.setOnClickListener  {
            ratingNumber = 3
            updateRating(ratingNumber)
        }
        imageStarText3.setOnClickListener{
            ratingNumber = 3
            updateRating(ratingNumber)
        }

        // If user press star number four, or the textview below it
        imageStar4.setOnClickListener {
            ratingNumber = 4
            updateRating(ratingNumber)
        }
        imageStarText4.setOnClickListener{
            ratingNumber = 4
            updateRating(ratingNumber)
        }


        // If user press star number five, or the textview below it
        imageStar5.setOnClickListener {
            ratingNumber = 5
            updateRating(ratingNumber)
        }
        imageStarText5.setOnClickListener{
            ratingNumber = 5
            updateRating(ratingNumber)
        }
    }

}