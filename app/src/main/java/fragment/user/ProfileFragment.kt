package fragment.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddeliveryproject.R
import fragment.LoginFragment


lateinit var signOutButton : Button
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
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signOutButton.setOnClickListener{
            auth.signOut()
            setCurrentFragment(loginFragment)
        }
    }


    private fun setCurrentFragment(fragment : Fragment){

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}