package com.example.fooddeliveryproject

import adapters.RestaurantRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.Restaurant
import com.google.firebase.firestore.FirebaseFirestore


class HamburgersActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hamburgers)


        FirebaseFirestore.getInstance().collection("restaurants")
            .whereArrayContains("category", "hamburgers")
//            .orderBy("rating").limit(3)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val restaurant = documents.toObjects(Restaurant::class.java)
                    //Code for recyclerView
                    var recyclerView = findViewById<RecyclerView>(R.id.restaurantsRecyclerView)
                    //What type of layout the list will have. This makes it a linear list
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    // Created an adapter from our adapter-class and sent in the list of restaurants
                    val adapter = RestaurantRecyclerAdapter(this, restaurant)
                    //Connect our adapter to our recyclerView
                    recyclerView.adapter = adapter
                    //End of recyclerView

                    val intent = Intent(this, UserInterfaceActivity::class.java)
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
            .addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT)
                    .show()
            }



    }






}