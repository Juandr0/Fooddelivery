package com.example.fooddeliveryproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragment.ExploreFragment
import fragment.ProfileFragment
import fragment.RestaurantsFragment
import fragment.SearchFragment

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
                R.id.ic_profile -> setCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment : Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()

    }
}