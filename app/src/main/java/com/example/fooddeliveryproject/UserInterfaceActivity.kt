package com.example.fooddeliveryproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import classes.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fragment.LoginFragment
import fragment.user.ExploreFragment
import fragment.user.ProfileFragment
import fragment.user.RestaurantsFragment
import fragment.user.SearchFragment

//Testat och kopplingen fungerar
val db = Firebase.firestore
val auth = Firebase.auth
val storage = Firebase.storage

class UserInterfaceActivity : AppCompatActivity() {

    private val exploreFragment = ExploreFragment()
    private val restaurantsFragment = RestaurantsFragment()
    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()
    private val loginFragment = LoginFragment()
    private val RestaurantInterfaceActivity = RestaurantInterfaceActivity()
    private val adminPageActivity = AdminPageActivity()

    private var currentUserType = ""

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

                    val isUserAlreadyLoggedIn = isLoggedInCheck()
                    if (!isUserAlreadyLoggedIn) {
                        setCurrentFragment(loginFragment)
                    } else {
                        userTypeCheck()
                    }
                }
            }
            true
        }




    }


// Add a new document with a generated ID


    private fun isLoggedInCheck() : Boolean{
        val currentUser = auth.currentUser
        return currentUser != null
        }



    private fun setCurrentFragment(fragment : Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()

    }

    open fun userTypeCheck() {

        var type : String
        val currentUser = auth.currentUser

        if (currentUser == null){
            return
        }

        val docRef = db.collection("users").document(currentUser.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                if (user != null){
                    type = user.type.toString()
                    currentUserType = type
                    activateCorrectProfile(type)
                }
            }
    }

    open fun activateCorrectProfile(userType : String){

        if (currentUserType == ""){
            return
        } else {
            when (currentUserType){
                "user" -> {
                    setCurrentFragment(profileFragment)
                }
                "admin" -> {
                    startNewActivity(adminPageActivity)
                }
                "restaurant" -> {
                    startNewActivity(RestaurantInterfaceActivity)
                }
            }


        }
    }

    private fun startNewActivity(newActivity : AppCompatActivity) {
        val intent = Intent(this, newActivity::class.java)
        startActivity(intent)
    }

}
