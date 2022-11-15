package fragment.restaurantMenu

import adapters.restaurantMenuAdapters.BrodernasMenuRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.OrderItem
import classes.RestaurantInfo
import com.bumptech.glide.Glide
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BrodernasMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BrodernasMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var brodernasPreviousFragmentImageButton: ImageButton
    lateinit var brodernasHeaderImageView: ImageView
    lateinit var brodernasNameText: TextView
    lateinit var brodernasOpeningTimeView: TextView
    lateinit var brodernasDeliveryFeeView: TextView
    lateinit var brodernasRatingView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brodernas_menu, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brodernasNameText = view.findViewById(R.id.brodernasNameView)
        brodernasOpeningTimeView = view.findViewById(R.id.brodernasOpeningTimeView)
        brodernasDeliveryFeeView = view.findViewById(R.id.brodernasDeliveryFeeView)
        brodernasRatingView = view.findViewById(R.id.brodernasRatingView)
        brodernasHeaderImageView = view.findViewById(R.id.brodernasHeaderImageView)

        brodernasPreviousFragmentImageButton = view.findViewById(R.id.brodernasPreviousFragmentImageButton)
        brodernasPreviousFragmentImageButton.setOnClickListener {
            returnToPreviousFragment()
        }


        setMenuInformation()

        FirebaseFirestore.getInstance().collection("restaurants").document("wGNxzHJszl1DLjs1Sp61").collection("menu")
            // för att sortera utefter kategori av maträtt. Funktion för att kunna göra flera recyclerviews med olika maträtter och olika rubriker
//            .whereArrayContains("cateogry","hamburger")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val orderItem = documents.toObjects(OrderItem::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.brodernasMenuItemsRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = BrodernasMenuRecyclerAdapter(this, orderItem)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView

                    adapter.setOnItemClickListener(object : BrodernasMenuRecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            when (position) {
                                0 -> {


                                }
                                1 -> {
                                }
                                2 -> {

                                }
                            }
                        }

                    }) // End of click handler


                }
            }
            .addOnFailureListener {
                Toast.makeText(context,"failed", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BrodernasMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BrodernasMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun returnToPreviousFragment(){
       if(parentFragmentManager.backStackEntryCount > 0){
           parentFragmentManager.popBackStack()
       }
    }

fun setMenuInformation(){
    db.collection("restaurants").document("wGNxzHJszl1DLjs1Sp61")
        .get()
        .addOnSuccessListener { document ->
            val restaurantInfo = document.toObject<RestaurantInfo>()

            brodernasNameText.text = restaurantInfo?.name
            brodernasOpeningTimeView.text = restaurantInfo?.openingTime
            brodernasDeliveryFeeView.text = getString(R.string.deliveryFee, "${restaurantInfo?.deliveryFee}")
            brodernasRatingView.text = restaurantInfo!!.rating.toString()

            Glide.with(this)
                .load(restaurantInfo?.image)
                .into(brodernasHeaderImageView)
        }
}

}