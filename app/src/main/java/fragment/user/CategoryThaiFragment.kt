package fragment.user

import adapters.CategoryThaiRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.Restaurant
import com.example.fooddeliveryproject.R
import com.google.firebase.firestore.FirebaseFirestore
import fragment.restaurantMenu.ThaiRungMenuFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryThaiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryThaiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var categoryThaiPreviousFragmentImageButton: ImageButton

    private val ThaiRungMenuFragment = ThaiRungMenuFragment()


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
        return inflater.inflate(R.layout.fragment_category_thai, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryThaiPreviousFragmentImageButton = view.findViewById(R.id.categoryThaiPreviousFragmentImageButton)
        categoryThaiPreviousFragmentImageButton.setOnClickListener {
            returnToPreviousFragment()
        }

        FirebaseFirestore.getInstance().collection("restaurants")
            .whereArrayContains("category", "thai")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val restaurant = documents.toObjects(Restaurant::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.thaiRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = CategoryThaiRecyclerAdapter(this, restaurant)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView


                    adapter.setOnItemClickListener(object : CategoryThaiRecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            when (position) {
                                0 -> {
                                    setCurrentFragment(ThaiRungMenuFragment)
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
         * @return A new instance of fragment CategoryThaiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryThaiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction().addToBackStack("CategoryThaiFragment")
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun returnToPreviousFragment(){
        if(parentFragmentManager.backStackEntryCount > 0){
            parentFragmentManager.popBackStack()
        }
    }
}