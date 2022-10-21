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
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.RestaurantInterfaceActivity
import com.example.fooddeliveryproject.RestaurantPageActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fragment.user.ProfileFragment
import fragment.user.SignupFragment


class LoginFragment : Fragment()  {

    private val auth = Firebase.auth
    private lateinit var userEmailEditText : EditText
    private lateinit var userPasswordEditText : EditText
    private lateinit var signInButton : Button
    private lateinit var signUpButton : Button
    private lateinit var signOutButton : Button
    private var userSignupFragment = SignupFragment()

    private val profileFragment = ProfileFragment()

    private var restaurantPageActivity = RestaurantPageActivity()

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

        //Ska använda funktionen newActivity och skicka användaren till Admin eller Company-sidorna beroende på
        //om användaren == isCompany eller == isAdmin


        signInButton.setOnClickListener{

            if (userEmailEditText.text.toString().isEmpty() || userPasswordEditText.text.toString().isEmpty()){
                Log.d("!!!", "Empty")
            } else {
                signIn()
            }


            // if user == company
            //startNewActivity(restaurantPageActivity)
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

    fun signIn() {

        val user = fragment.user.auth.currentUser
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
            Log.d("!!!", "redan inloggad")
            //userTypeCheck
        }
    }

    // Ska dra ner användaren från databasen och kolla värdet på "type"
    // Skicka användaren till olika fragment / aktiviteter beroende på vilken typ
    fun userTypeCheck() {
        setCurrentFragment(profileFragment)
    }

}