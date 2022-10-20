package com.example.fooddeliveryproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fragment.LoginFragment
import fragment.user.*

//Testat och kopplingen fungerar
val db = Firebase.firestore
val auth = Firebase.auth

class FragmentMenuActivity : AppCompatActivity() {

    private val exploreFragment = ExploreFragment()
    private val restaurantsFragment = RestaurantsFragment()
    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()
    private val loginFragment = LoginFragment()

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

                    /*
                    val userLoggedIn = isLoggedInCheck()
                    if (!userLoggedIn) {
                        setCurrentFragment(loginFragment)
                    } else if(){
                        // if-sats eller when-sats som kollar ifall användaren är en admin eller ett företag ->
                        // skicka användaren till admin/företag aktivitet med rätt funtkionalitet
                        // annars skicka användaren till sin profil

                    } else {
                        setCurrentFragment(profileFragment)
                    }
                    */

                    //Kommenterar ut för att arbeta med andra fragments, som login.
                //setCurrentFragment(profileFragment)

                    setCurrentFragment(loginFragment)
                }
            }
            true
        }




    }


// Add a new document with a generated ID



    private fun isLoggedInCheck() : Boolean{
        val currentUser = auth.currentUser
        return (currentUser != null)
        }



    private fun setCurrentFragment(fragment : Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()

    }
}