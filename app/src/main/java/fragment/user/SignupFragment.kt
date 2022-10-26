package fragment.user

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var passwordHiderImg : ImageView


    private var isShowing = false
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
        passwordHiderImg = v.findViewById(R.id.signUp_showHidePass)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        newUserSignupButton.setOnClickListener {
            createNewUser()
        }

        passwordHiderImg.setOnClickListener{
            isShowing = !isShowing
            togglePassword(isShowing)
        }
    }


    private fun createNewUser(){

        val email = newUserEmailEditText.text.toString()
        val password = newUserPasswordEditText.text.toString()

        if (auth.currentUser != null || email.isEmpty() || password.isEmpty()){
            Log.d("!!!", "empty mail or pass")
            Toast.makeText(getActivity(),getString(R.string.error_reqinfo),Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.d("!!!", "user created to AUTHlogin")
                    createUserToDBCatalougeFromSignupFieldHelperFunction()
                }.addOnFailureListener {  e ->
                    Toast.makeText(getActivity(),"$e",Toast.LENGTH_SHORT).show()
                    Log.d("!!!", "No user created error: + $e" )
                }
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
        val password = newUserPasswordEditText.text
        val user = auth.currentUser


        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(),getString(R.string.error_reqinfo),Toast.LENGTH_SHORT).show()
        } else {

            val newUser = User(
                name = name,
                email = email,
                address = address,
                phoneNumber = phoneNumber.toString().toInt(),
                uID = user?.uid
            )


            if (user != null) {
                db.collection("users").document(user.uid).set(newUser)
                    .addOnCompleteListener {
                        Log.d("!!!", "user created to DB")
                        setCurrentFragment(profileFragment)
                    }
                    .addOnFailureListener {e ->
                        Log.d("!!!", "No user created to DB")
                        Toast.makeText(getActivity(),"$e",Toast.LENGTH_SHORT).show()
                    }
            }
        }


    }

    private fun togglePassword(isShowing : Boolean) {
        if (isShowing){
            newUserPasswordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordHiderImg.setImageResource(R.drawable.ic_hidepassword)

        } else {
            newUserPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordHiderImg.setImageResource(R.drawable.ic_showpassword)
        }
    }


}

