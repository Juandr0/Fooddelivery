package fragment.user

import adapters.restaurantMenuAdapters.AllRestaurantsRecyclerAdapter
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RestaurantFragment : Fragment() {

    lateinit var restaurantNamesList : List<String>

    override fun onResume() {
        super.onResume()
//        getRestaurantNames()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseFirestore.getInstance().collection("restaurants")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val restaurant = documents.toObjects(Restaurant::class.java)
                    //Code for recyclerView
                    var recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = AllRestaurantsRecyclerAdapter(this, restaurant)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView


                    adapter.setOnItemClickListener(object : AllRestaurantsRecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                        }

                    }) // End of click handler

                }

            }
            .addOnFailureListener {
                Toast.makeText(context,"failed", Toast.LENGTH_SHORT)
                    .show()
            }

    }


}