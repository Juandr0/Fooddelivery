package fragment.user

import adapters.FoodCategoryRecyclerAdapter
import adapters.TopRatedRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.FoodCategory
import classes.Restaurant
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.UserInterfaceActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: FoodCategoryRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    private val CategoryHamburgersFragment = CategoryHamburgersFragment()
    private val CategoryPizzaFragment = CategoryPizzaFragment()
    private val CategoryKebabFragment = CategoryKebabFragment()
    private val CategorySushiFragment = CategorySushiFragment()
    private val CategoryIndianFragment = CategoryIndianFragment()


    // List of categories
    var categories = mutableListOf<FoodCategory>(
        FoodCategory(R.drawable.hamburger_category),
        FoodCategory(R.drawable.pizza_category),
        FoodCategory(R.drawable.kebab_category),
        FoodCategory(R.drawable.sushi_category),
        FoodCategory(R.drawable.indian_category),
        FoodCategory(R.drawable.vegetarian_category),
        FoodCategory(R.drawable.italian_category),
        FoodCategory(R.drawable.thai_category),
        FoodCategory(R.drawable.mexican_category)

    )

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
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExploreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Setup of the layoutManager
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.foodCategoryRecyclerView)
        //Makes the layout horizontal
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        adapter = FoodCategoryRecyclerAdapter(this, categories)
        recyclerView.adapter = adapter

        // Handler for the clicks on the items in the list
        adapter.setOnItemClickListener(object: FoodCategoryRecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context, "you clicked on item no. $position", Toast.LENGTH_SHORT).show()

                when(position){
                    0->{
                        setCurrentFragment(CategoryHamburgersFragment)
                    }
                    1->{
                        setCurrentFragment(CategoryPizzaFragment)
                    }
                    2->{
                        setCurrentFragment(CategoryKebabFragment)
                    }
                    3->{
                        setCurrentFragment(CategorySushiFragment)
                    }
                    4->{
                        setCurrentFragment(CategoryIndianFragment)
                    }


                }
            }

        })


        // Top Rated lista
        FirebaseFirestore.getInstance().collection("restaurants")
            .orderBy("rating", Query.Direction.DESCENDING).limit(5)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val restaurant = documents.toObjects(Restaurant::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.topRatedRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = TopRatedRecyclerAdapter(this, restaurant)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView

                    val intent = Intent(context, UserInterfaceActivity::class.java)
                    adapter.setOnItemClickListener(object : TopRatedRecyclerAdapter.onItemClickListener {
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
                Toast.makeText(context,"failed",Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


}