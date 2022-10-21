package fragment.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.User
import com.example.fooddeliveryproject.db
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

val auth = Firebase.auth


class SignupFragment : Fragment() {

    private lateinit var woopTV1: TextView
    private lateinit var woopTV2: TextView
    private lateinit var registerTextView: TextView
    private lateinit var newUserNameEditText: EditText
    private lateinit var newUserEmailEditText: EditText
    private lateinit var newUserAddressEditText: EditText
    private lateinit var newUserPhoneNumberEditText: EditText
    private lateinit var newUserPasswordEditText: EditText
    private lateinit var newUserSignupButton: Button

    private var profileFragment = ProfileFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_user_signup, container, false)

        woopTV1 = v.findViewById(R.id.WoopTV1)
        woopTV2 = v.findViewById(R.id.WoopTV2)

        registerTextView = v.findViewById(R.id.registerTextView)

        newUserNameEditText = v.findViewById(R.id.newUserNameEditText)
        newUserEmailEditText = v.findViewById(R.id.newUserEmailEditText)
        newUserAddressEditText = v.findViewById(R.id.newUserAddressEditText)
        newUserPhoneNumberEditText = v.findViewById(R.id.newUserPhoneNumberEditText)

        newUserPasswordEditText = v.findViewById(R.id.newUserPasswordEditText)
        newUserSignupButton = v.findViewById(R.id.newUserSignupButton)

        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        newUserSignupButton.setOnClickListener {
            createNewUser()
        }

    }
    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


    private fun createUserToDBCatalougeFromSignupFieldHelperFunction() {

        val name = newUserNameEditText.text.toString()
        val email = newUserEmailEditText.text.toString()
        val address = newUserAddressEditText.text.toString()
        val phoneNumber = newUserPhoneNumberEditText.text
        val user = auth.currentUser



        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
            Log.d("!!!", "empty fields, no user created")
        } else {

            val newUser = User(
                name = name,
                email = email,
                address = address,
                phoneNumber = phoneNumber.toString().toInt(),
                uID = user?.uid
            )


            if (user != null) {
                db.collection("users").document(user.uid).collection("info").add(newUser)
                    .addOnCompleteListener {
                        Log.d("!!!", "user created to DB")
                        setCurrentFragment(profileFragment)
                    }
                    .addOnFailureListener {
                        Log.d("!!!", "No user created to DB")
                    }
            }
        }


    }

    private fun createNewUser(){
        auth.createUserWithEmailAndPassword(
            newUserEmailEditText.text.toString(),
            newUserPasswordEditText.text.toString()
        )
            .addOnSuccessListener {
                Log.d("!!!", "user created to AUTHlogin")
                createUserToDBCatalougeFromSignupFieldHelperFunction()
            }.addOnFailureListener {
                Log.d("!!!", "No user created to AUTHlogin")
            }

    }


    fun signIn() {

        val user = auth.currentUser
        if (user == null) {
            Log.d("!!!", "Inte inloggad")
            return
        } else {

            auth.signInWithEmailAndPassword(
                newUserEmailEditText.text.toString(),
                newUserPasswordEditText.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("!!!", "Logged in!")
                    } else {
                        Log.d("!!!", "Sign in Fail")
                    }
                }
        }
    }


}

