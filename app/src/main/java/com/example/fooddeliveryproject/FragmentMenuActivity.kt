package com.example.fooddeliveryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fragment.ExploreFragment
import fragment.ProfileFragment
import fragment.RestaurantsFragment
import fragment.SearchFragment

//Testat och kopplingen fungerar
val db = Firebase.firestore

class FragmentMenuActivity : AppCompatActivity() {

    private val exploreFragment = ExploreFragment()
    private val restaurantsFragment = RestaurantsFragment()
    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var navigationMenu : BottomNavigationView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_menu)
        navigationMenu = findViewById(R.id.navigationbar)

        setCurrentFragment(exploreFragment)

        navigationMenu.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.ic_explore -> setCurrentFragment(exploreFragment)
                R.id.ic_restaurants -> setCurrentFragment(restaurantsFragment)
                R.id.ic_search -> setCurrentFragment(searchFragment)
                R.id.ic_profile -> {
                    // if-sats som kollar ifall användaren är inloggad -> om false skicka anv till
                    // login-fragment


                    // if-sats som kollar ifall användaren är en admin eller ett företag ->
                    // skicka användaren till admin/företag aktivitet med rätt funtkionalitet
                    // annars skicka användaren till sin profil

                setCurrentFragment(profileFragment)
                }
            }
            true
        }




    }


// Add a new document with a generated ID



    private fun setCurrentFragment(fragment : Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()

    }
}