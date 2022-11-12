package fragment.restaurant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import classes.Restaurant
import classes.User
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantEditMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantEditMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var restaurantEditMenuGoBackButton: Button
    lateinit var  restaurantSaveMenuButton: Button
    lateinit var restaurantDishNameEditText: EditText
    lateinit var  restaurantDishPriceEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()

        val currentUser = fragment.user.auth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()

        val docRef =db.collection("restaurants").document("${user!!.menuId}")
                docRef.get()
                    .addOnSuccessListener { document ->
                        val restaurant = document.toObject<Restaurant>()

                        restaurantDishNameEditText.hint = "${restaurant!!.deliveryFee}"

                            restaurantEditMenuGoBackButton.setOnClickListener {
                                setCurrentFragmentToRestaurantMenu()
                            }

                        restaurantSaveMenuButton.setOnClickListener {
//                            val newMenuItem = OrderItem(
//                                "${user!!.name}",
//                                restaurantDishNameEditText.text.toString(),
//                                restaurantDishPriceEditText.text.toString().toInt(),
//                                restaurant!!.deliveryFee
//                            )
                            val data = hashMapOf(
                                "restaurantName" to "${user!!.name}",
                                "orderFromMeny" to restaurantDishNameEditText.text.toString(),
                                "deliveryFee" to restaurant!!.deliveryFee,
                                "price" to restaurantDishPriceEditText.text.toString().toInt()

                            )
                            db.collection("restaurants").document("${user!!.menuId}").collection("menu")
                                .add(data)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("!!!", "DocumentSnapshot written with ID: ${documentReference.id}")

                                }
                        }

                    }
    }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_edit_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantEditMenuGoBackButton = view.findViewById(R.id.restaurantEditMenuGoBackButton)
        restaurantSaveMenuButton = view.findViewById(R.id.restaurantSaveMenuButton)
        restaurantDishNameEditText = view.findViewById((R.id.restaurantDishNameEditText))
        restaurantDishPriceEditText = view.findViewById(R.id.restaurantDishPriceEditText)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestaurantEditMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestaurantEditMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setCurrentFragmentToRestaurantMenu(){
        val menuFragment = MenuFragment()
        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.restaurantInterfaceContainer, menuFragment)
        transaction.commit()
    }
}