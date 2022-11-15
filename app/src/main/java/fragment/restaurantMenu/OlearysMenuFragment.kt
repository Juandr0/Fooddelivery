package fragment.restaurantMenu

import adapters.restaurantMenuAdapters.OlearysMenuRecyclerAdapter
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
 * Use the [OlearysMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OlearysMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var olearysHeaderImageView: ImageView
    lateinit var olearysNameView: TextView
    lateinit var olearysOpeningTimeView: TextView
    lateinit var olearysDeliveryFeeView: TextView
    lateinit var olearysRatingView: TextView

    lateinit var olearysPreviousFragmentImageButton: ImageButton

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
        return inflater.inflate(R.layout.fragment_olearys_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        olearysNameView = view.findViewById(R.id.olearysNameView)
        olearysOpeningTimeView = view.findViewById(R.id.olearysOpeningTimeView)
        olearysDeliveryFeeView = view.findViewById(R.id.olearysDeliveryFeeView)
        olearysRatingView = view.findViewById(R.id.olearysRatingView)
        olearysHeaderImageView = view.findViewById(R.id.olearysHeaderImageView)

        setMenuInformation()

        olearysPreviousFragmentImageButton = view.findViewById(R.id.olearysPreviousFragmentImageButton)
        olearysPreviousFragmentImageButton.setOnClickListener {
            returnToPreviousFragment()
        }

        FirebaseFirestore.getInstance().collection("restaurants").document("QpWMx7nDGyzD6Zz4AlKt").collection("menu")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val orderItem = documents.toObjects(OrderItem::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.olearysMenuItemsRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = OlearysMenuRecyclerAdapter(this, orderItem)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView

                    adapter.setOnItemClickListener(object : OlearysMenuRecyclerAdapter.onItemClickListener {
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
         * @return A new instance of fragment OlearysMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OlearysMenuFragment().apply {
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
        db.collection("restaurants").document("QpWMx7nDGyzD6Zz4AlKt")
            .get()
            .addOnSuccessListener { document ->
                val restaurantInfo = document.toObject<RestaurantInfo>()

                olearysNameView.text = restaurantInfo?.name
                olearysOpeningTimeView.text = restaurantInfo?.openingTime
                olearysDeliveryFeeView.text = getString(R.string.deliveryFee, "${restaurantInfo?.deliveryFee}")
                olearysRatingView.text = restaurantInfo!!.rating.toString()

                Glide.with(this)
                    .load(restaurantInfo?.image)
                    .into( olearysHeaderImageView)
            }
    }
}