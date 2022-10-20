package fragment.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.fooddeliveryproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class LoginFragment : Fragment()  {

    private val auth = Firebase.auth
    private lateinit var userEmailEditText : EditText
    private lateinit var userPasswordEditText : EditText
    private lateinit var signInButton : Button
    private lateinit var signUpButton : Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        //Utnyttjar inflatern till att knyta alla grafiska element som
        //knappar & edittext i onCreateView inflatern för att de annars inte
        //går att nå på ett enkelt sätt.


        signInButton = v.findViewById(R.id.loginUserSignInButton)
        signUpButton = v.findViewById(R.id.loginUserSignUpButton)
        userEmailEditText = v.findViewById(R.id.loginUserNameEditText)
        userPasswordEditText = v.findViewById(R.id.loginUserPasswordEditText)

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


        signUpButton.setOnClickListener{
            logOnClick()
        }

    }

    fun logOnClick() {
        Log.d("!!!", "Klick")
    }

    fun signIn() {
    auth.signInWithEmailAndPassword(userEmailEditText.text.toString(), userPasswordEditText.text.toString())
        .addOnCompleteListener{ task ->
            if (task.isSuccessful){
                Log.d("!!!", "Logged in!")
            } else {
                Log.d("!!!", "Sign in Fail")
            }

        }

    }

}