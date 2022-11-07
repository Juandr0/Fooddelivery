package fragment.user

import adapters.CategoryHamburgersRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.Restaurant
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.UserInterfaceActivity
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryHamburgersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryHamburgersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_category_hamburgers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseFirestore.getInstance().collection("restaurants")
            .whereArrayContains("category", "hamburgers")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val restaurant = documents.toObjects(Restaurant::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.hamburgersRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = CategoryHamburgersRecyclerAdapter(this, restaurant)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView

                    val intent = Intent(context, UserInterfaceActivity::class.java)
                    adapter.setOnItemClickListener(object : CategoryHamburgersRecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            //toast to check if clicking works
                            Toast.makeText(context,
                                "you clicked on item no. $position",
                                Toast.LENGTH_SHORT
                            ).show()

                            when (position) {
                                0 -> {
                                    startActivity(intent)
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
         * @return A new instance of fragment CategoryHamburgersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryHamburgersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}