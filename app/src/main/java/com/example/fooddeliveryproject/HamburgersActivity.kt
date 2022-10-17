package com.example.fooddeliveryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HamburgersActivity : AppCompatActivity() {

    // list of restaurants for the recyclerView
    var restaurants = mutableListOf<Restaurant>(
        Restaurant(R.drawable.exampleheader, "Max Liljeholmen", 4.9, 29),
        Restaurant(R.drawable.exampleheader, "Max Liljeholmen", 4.9, 29),
        Restaurant(R.drawable.exampleheader, "Max Liljeholmen", 4.9, 29),
        Restaurant(R.drawable.exampleheader, "Max Liljeholmen", 4.9, 29),
        Restaurant(R.drawable.exampleheader, "Max Liljeholmen", 4.9, 29)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hamburgers)


        //Code for recyclerView
        var recyclerView = findViewById<RecyclerView>(R.id.restaurantsRecyclerView)
        //What type of layout the list will have. This makes it a linear list
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Created an adapter from our adapter-class and sent in the list of restaurants
        val adapter = RestaurantRecyclerAdapter(this, restaurants)
        //Connect our adapter to our recyclerView
        recyclerView.adapter = adapter
        //End of recyclerView

        // Handler for the clicks on the items in the list
        //Need to find a better way to assign actions to different positions!! has to be automatic
        val intent = Intent(this, FragmentMenuActivity::class.java)
        adapter.setOnItemClickListener(object : RestaurantRecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                //toast to check if clicking works
                Toast.makeText(
                    this@HamburgersActivity,
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