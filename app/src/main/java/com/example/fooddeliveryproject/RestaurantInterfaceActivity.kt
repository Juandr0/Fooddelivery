package com.example.fooddeliveryproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragment.restaurant.ImagesFragment
import fragment.restaurant.MenuFragment
import fragment.restaurant.RestaurantProfileFragment





class RestaurantInterfaceActivity : AppCompatActivity() {

    private val restaurantMenuFragment = MenuFragment()
    private val restaurantProfileFragment = RestaurantProfileFragment()
    private val restaurantImagesFragment = ImagesFragment()

    private lateinit var restaurantNavigationMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_interface)

        restaurantNavigationMenu = findViewById(R.id.restaurantNavigationView)

        setCurrentFragment(restaurantProfileFragment)

        restaurantNavigationMenu.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.ic_menu -> setCurrentFragment(restaurantMenuFragment)
                R.id.ic_images -> setCurrentFragment(restaurantImagesFragment)
                R.id.ic_profile -> setCurrentFragment(restaurantProfileFragment)
            }
            true
        }


    }

    private fun setCurrentFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.restaurantInterfaceContainer, fragment)
        transaction.commit()

    }


}


