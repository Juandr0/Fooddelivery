package com.example.fooddeliveryproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import classes.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fragment.LoginFragment
import fragment.user.*

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
    private val loadingScreenFragment = LoadingScreenFragment()


    private var currentUserType = ""

    private lateinit var navigationMenu : BottomNavigationView
    private lateinit var loadingScreenFragmentContainer : FrameLayout
    private lateinit var itemCartImageView : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_userinterface)
        navigationMenu = findViewById(R.id.navigationbar)
        loadingScreenFragmentContainer = findViewById(R.id.loadingPageFragmentContainer)

        isCompanySignedIn()
        setCurrentFragment(exploreFragment)

        //Loading screen
        //Navigation menu is invisible so it doesnt show
        //Made visible after the timer runs out
        activateLoadingFragment(loadingScreenFragment)
        navigationMenu.isVisible = false

        Handler().postDelayed({
            disableLoadingFragment(loadingScreenFragment)
        }, 1500)


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

        itemCartImageView = findViewById(R.id.itemCartImageView)
        itemCartImageView.setOnClickListener {
            setCurrentFragment(CheckoutFragment())
        }

    }



// Add a new document with a generated ID


    private fun isLoggedInCheck() : Boolean{
        val currentUser = auth.currentUser
        return currentUser != null
        }

    private fun activateLoadingFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.loadingPageFragmentContainer, fragment)
        transaction.commit()

    }

    private fun disableLoadingFragment(fragment : Fragment){
        // clickable and focusable remove the ability to click buttons behind the fragment.
        // Need to be disabled so that they are clickable again when the fragment is gone
        loadingScreenFragmentContainer.isClickable = false
        loadingScreenFragmentContainer.isFocusable = false
        navigationMenu.isVisible = true
        val transaction = supportFragmentManager.beginTransaction()
        transaction.remove(loadingScreenFragment)
        transaction.commit()
    }



    private fun setCurrentFragment(fragment : Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
    }

    private fun isCompanySignedIn(){

            var type : String = ""
            val currentUser = auth.currentUser ?: return

        val docRef = db.collection("users").document(currentUser.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    val user = document.toObject<User>()
                    if (user != null){
                        type = user.type.toString()
                    }
                    if (type == "restaurant"){
                        startNewActivity(RestaurantInterfaceActivity)
                    }
                }

    }

    private fun userTypeCheck() {

        var type : String
        val currentUser = auth.currentUser ?: return

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

    private fun activateCorrectProfile(userType : String){

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
