package fragment.user

import adapters.RecyclerAdapterRestaurantFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db

class RestaurantFragment : Fragment() {

    lateinit var restaurantNamesList : List<String>

    override fun onResume() {
        super.onResume()
        getRestaurantNames()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    private fun initializeRecyclerView(view : View){
        var recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = RecyclerAdapterRestaurantFragment(this, restaurantNamesList)
        recyclerView.adapter = adapter
    }



    private fun getRestaurantNames(){
        var restaurantList = mutableListOf<String>()
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    restaurantList.add(document["name"].toString())
                }
                restaurantList.sortWith(String.CASE_INSENSITIVE_ORDER)
                restaurantNamesList = restaurantList
                initializeRecyclerView(requireView())
            }

    }

}