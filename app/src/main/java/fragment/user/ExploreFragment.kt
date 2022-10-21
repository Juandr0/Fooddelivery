package fragment.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryproject.FoodCategory
import com.example.fooddeliveryproject.FoodCategoryRecyclerAdapter
import com.example.fooddeliveryproject.HamburgersActivity
import com.example.fooddeliveryproject.R

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
        val intent = Intent(context, HamburgersActivity::class.java)
        adapter.setOnItemClickListener(object: FoodCategoryRecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context, "you clicked on item no. $position", Toast.LENGTH_SHORT).show()

                when(position){
                    0->{
                        startActivity(intent)
                    }
                    1->{
                    }
                    2->{
                    }
                }
            }

        })
    }




}