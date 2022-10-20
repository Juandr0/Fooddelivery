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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

val auth = Firebase.auth


class SignupFragment : Fragment() {

    private lateinit var woopTV1 : TextView
    private lateinit var woopTV2 : TextView
    private lateinit var registerTextView: TextView
    private lateinit var newUsernameEditText : EditText
    private lateinit var newUserAddressEditText : EditText
    private lateinit var newUserPhoneNumberEditText : EditText
    private lateinit var newUserPasswordEditText : EditText
    private lateinit var newUserSignupButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_user_signup, container, false)

        woopTV1 = v.findViewById(R.id.WoopTV1)
        woopTV2 = v.findViewById(R.id.WoopTV2)

        registerTextView = v.findViewById(R.id.registerTextView)
        newUsernameEditText = v.findViewById(R.id.newUsernameEditText)
        newUserAddressEditText = v.findViewById(R.id.newUserAddressEditText)
        newUserPhoneNumberEditText = v.findViewById(R.id.newUserPhoneNumberEditText)
        newUserPasswordEditText = v.findViewById(R.id.newUserPasswordEditText)
        newUserSignupButton = v.findViewById(R.id.newUserSignupButton)

        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        newUserSignupButton.setOnClickListener{
            onClick()
        }

    }
    private fun onClick() {
        Log.d("!!!","Klick")
    }

}