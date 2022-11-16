package fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import classes.User
import com.example.fooddeliveryproject.AdminPageActivity
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.RestaurantInterfaceActivity
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
    private lateinit var passwordHiderImg : ImageView

    private var isPassShowing = false
    private var userSignupFragment = SignupFragment()
    private val profileFragment = ProfileFragment()
    private val restaurantPageActivity = RestaurantInterfaceActivity()
    private val adminPageActivity = AdminPageActivity()

    private var currentUserType = ""



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        //Utnyttjar inflatern till att knyta alla grafiska element som
        //knappar & edittext i onCreateView inflatern för att de annars inte
        //går att nå på ett enkelt sätt.



        db = Firebase.firestore
        auth = Firebase.auth

        signInButton = v.findViewById(R.id.loginUserSignInButton)
        signUpButton = v.findViewById(R.id.loginUserSignUpButton)
        userEmailEditText = v.findViewById(R.id.loginUserNameEditText)
        userPasswordEditText = v.findViewById(R.id.loginUserPasswordEditText)
        passwordHiderImg = v.findViewById(R.id.signIn_showHidePass)



        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Onclick listener som försöker att logga in ifall inte edittext för anvnamn eller lösenord är empty

        passwordHiderImg.setOnClickListener {
            isPassShowing = !isPassShowing
            togglePassword(isPassShowing)
        }

        signInButton.setOnClickListener {
            val email = "email"
            val password = "password"


            turnEmptyFieldRedOrWhite()

            if (userEmailEditText.text.isEmpty() || userPasswordEditText.text.isEmpty()) {
                if (userEmailEditText.text.toString().isEmpty()) {
                    Toast.makeText(
                        context, getString(R.string.no_empty_fields, email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (userPasswordEditText.text.toString().isEmpty()) {
                    Toast.makeText(
                        context, getString(R.string.no_empty_fields, password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                signIn()
            }
        }



        signUpButton.setOnClickListener{
            auth.signOut()
            setCurrentFragment(userSignupFragment)
        }

    }

    private fun togglePassword(isShowing : Boolean) {
        if (isShowing){
            userPasswordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordHiderImg.setImageResource(R.drawable.ic_hidepassword)

        } else {
            userPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordHiderImg.setImageResource(R.drawable.ic_showpassword)
        }
    }

    private fun turnEmptyFieldRedOrWhite(){
        var redColor = "#FFBCA5"
        var whiteColor = "#FFFFFF"


        if (userEmailEditText.text.isEmpty()){
            userEmailEditText.setBackgroundColor(Color.parseColor(redColor))
        } else {
            userEmailEditText.setBackgroundColor(Color.parseColor(whiteColor))
        }

        if (userPasswordEditText.text.isEmpty()){
            userPasswordEditText.setBackgroundColor(Color.parseColor(redColor))
        } else {
            userPasswordEditText.setBackgroundColor(Color.parseColor(whiteColor))
        }


    }

    @SuppressLint("SuspiciousIndentation")
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
                        userTypeCheck()
                    } else {
                        Toast.makeText(
                            context, getString(R.string.incorrect_login),
                            Toast.LENGTH_SHORT
                        ).show()
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
                    startNewActivity(adminPageActivity)
                }
                "restaurant" -> {
                    startNewActivity(restaurantPageActivity)
                }
        }


        }
    }

}