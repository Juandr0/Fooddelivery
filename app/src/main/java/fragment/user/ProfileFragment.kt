package fragment.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.User
import com.example.fooddeliveryproject.UserSettings
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import fragment.LoginFragment


class ProfileFragment : Fragment() {

    lateinit var greetingsTextView : TextView
    lateinit var signOutButton : Button
    lateinit var lastOrderRestaurant : TextView
    lateinit var lastOrder : TextView

    private val settingsList = mutableListOf<UserSettings>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_profile, container, false)
        signOutButton = v.findViewById(R.id.profileSignOutButton)
        greetingsTextView = v.findViewById(R.id.greetingsTextView)
        lastOrderRestaurant = v.findViewById(R.id.lastOrderRestaurantNameTextView)
        lastOrder = v.findViewById(R.id.lastOrderTextView)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentUser = auth.currentUser
        val user = User()




        signOutButton.setOnClickListener{
            auth.signOut()
            val loginFragment = LoginFragment()
            setCurrentFragment(loginFragment)

        }


        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                greetingsTextView.text = getString(R.string.greetings) + ", ${user!!.name}"
                lastOrderRestaurant.text = "${user!!.lastOrderRestaurant}"
                lastOrder.text = "${user!!.lastOrder}"

                settingsList.add(UserSettings(getString(R.string.name), "${user.name}"))
                settingsList.add(UserSettings(getString(R.string.email), "${user.email}"))
                settingsList.add(UserSettings(getString(R.string.address), "${user.address}"))
                settingsList.add(UserSettings(getString(R.string.phonenumer), "${user.phoneNumber}"))


            }


    }


    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}