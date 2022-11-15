package fragment.restaurantMenu

import adapters.restaurantMenuAdapters.ThaiRungMenuRecyclerAdapter
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
 * Use the [ThaiRungMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThaiRungMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var thairungHeaderImageView: ImageView
    lateinit var thairungNameView: TextView
    lateinit var thairungOpeningTimeView: TextView
    lateinit var thairungDeliveryFeeView: TextView
    lateinit var thairungRatingView: TextView

    lateinit var thaiRungPreviousFragmentImageButton: ImageButton

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
        return inflater.inflate(R.layout.fragment_thai_rung_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thairungNameView = view.findViewById(R.id.thairungNameView)
        thairungOpeningTimeView = view.findViewById(R.id.thairungOpeningTimeView)
        thairungDeliveryFeeView = view.findViewById(R.id.thairungDeliveryFeeView)
        thairungRatingView = view.findViewById(R.id.thairungRatingView)
        thairungHeaderImageView = view.findViewById(R.id.thairungHeaderImageView)

        setMenuInformation()

        thaiRungPreviousFragmentImageButton = view.findViewById(R.id.thaiRungPreviousFragmentImageButton)
        thaiRungPreviousFragmentImageButton.setOnClickListener {
            returnToPreviousFragment()
        }

        FirebaseFirestore.getInstance().collection("restaurants").document("PZBhflO4krmbj8GRNz5q").collection("menu")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val orderItem = documents.toObjects(OrderItem::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.thaiRungMenuItemsRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = ThaiRungMenuRecyclerAdapter(this, orderItem)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView

                    adapter.setOnItemClickListener(object : ThaiRungMenuRecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            //toast to check if clicking works
                            Toast.makeText(context,
                                "you clicked on item no. $position",
                                Toast.LENGTH_SHORT
                            ).show()

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
         * @return A new instance of fragment ThaiRungMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThaiRungMenuFragment().apply {
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
        db.collection("restaurants").document("PZBhflO4krmbj8GRNz5q")
            .get()
            .addOnSuccessListener { document ->
                val restaurantInfo = document.toObject<RestaurantInfo>()

                thairungNameView.text = restaurantInfo?.name
                thairungOpeningTimeView.text = restaurantInfo?.openingTime
                thairungDeliveryFeeView.text = getString(R.string.deliveryFee, "${restaurantInfo?.deliveryFee}")
                thairungRatingView.text = restaurantInfo!!.rating.toString()

                Glide.with(this)
                    .load(restaurantInfo?.image)
                    .into( thairungHeaderImageView)
            }
    }

}