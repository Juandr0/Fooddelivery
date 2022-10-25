package fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.fooddeliveryproject.R
import classes.User
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import fragment.LoginFragment


lateinit var greetingsTextView : TextView
lateinit var signOutButton : Button
lateinit var lastOrderRestaurant : TextView
lateinit var lastOrder : TextView

private val loginFragment = LoginFragment()

class ProfileFragment : Fragment() {

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
        signOutButton.setOnClickListener{
            auth.signOut()
            setCurrentFragment(loginFragment)

        }
        val currentUser = auth.currentUser
        val user = User()

        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                greetingsTextView.text = getString(R.string.greetings) + ", ${user!!.name}"
                lastOrderRestaurant.text = "${user!!.lastOrderRestaurant}"
                lastOrder.text = "${user!!.lastOrder}"

            }


    }


    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}