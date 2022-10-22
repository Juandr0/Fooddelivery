package fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fooddeliveryproject.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import fragment.user.ProfileFragment
import fragment.user.SignupFragment


class LoginFragment : Fragment()  {

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore


    private lateinit var userEmailEditText : EditText
    private lateinit var userPasswordEditText : EditText
    private lateinit var signInButton : Button
    private lateinit var signUpButton : Button
    private lateinit var signOutButton : Button
    private var userSignupFragment = SignupFragment()

    private val profileFragment = ProfileFragment()
    private var restaurantPageActivity = RestaurantInterfaceActivity()
    private var currentUserType = ""

    // Implementeras när aktiviteten finns
    // private var adminPageActivity = AdminPageActivity()

    private lateinit var restaurantTestButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        //Utnyttjar inflatern till att knyta alla grafiska element som
        //knappar & edittext i onCreateView inflatern för att de annars inte
        //går att nå på ett enkelt sätt.

        //TEST
        restaurantTestButton = v.findViewById(R.id.restaurantTestButton)
        //TEST SLUT

        db = Firebase.firestore
        auth = Firebase.auth

        signInButton = v.findViewById(R.id.loginUserSignInButton)
        signUpButton = v.findViewById(R.id.loginUserSignUpButton)
        userEmailEditText = v.findViewById(R.id.loginUserNameEditText)
        userPasswordEditText = v.findViewById(R.id.loginUserPasswordEditText)
        signOutButton = v.findViewById(R.id.signOut)


        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Onclick listener som försöker att logga in ifall inte edittext för anvnamn eller lösenord är empty

        signInButton.setOnClickListener{

            if (userEmailEditText.text.toString().isEmpty() || userPasswordEditText.text.toString().isEmpty()){
                Log.d("!!!", "Empty")
            } else {
                signIn()
            }

        }


        signOutButton.setOnClickListener{
            if (auth.currentUser != null) {
                auth.signOut()
                Log.d("!!!","Logged out!")
            }
        }

        signUpButton.setOnClickListener{
            auth.signOut()
            setCurrentFragment(userSignupFragment)
        }

        restaurantTestButton.setOnClickListener {
            val intent = Intent(context, RestaurantInterfaceActivity::class.java)
            startActivity(intent)
        }

    }


    private fun startNewActivity(newActivity : AppCompatActivity) {
    val intent = Intent(activity, newActivity::class.java)
        startActivity(intent)
    }
    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun signIn() {
        val user = auth.currentUser
        if (user == null) {
            auth.signInWithEmailAndPassword(userEmailEditText.text.toString(), userPasswordEditText.text.toString())
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Log.d("!!!", "Logged in!")
                        userTypeCheck()
                    } else {
                        Log.d("!!!", "Sign in Fail")
                    }
                }

        } else  {
        return
        }
    }

    // Ska dra ner användaren från databasen och kolla värdet på "type"
    // Skicka användaren till olika fragment / aktiviteter beroende på vilken typ

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

                }
                "restaurant" -> {
                    startNewActivity(restaurantPageActivity)
                }
        }


        }
    }

}