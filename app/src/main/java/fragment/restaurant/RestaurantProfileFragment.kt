package fragment.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.auth
import kotlin.math.sign

lateinit var signOutButton : Button

class RestaurantProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_restaurant_profile, container, false)
        signOutButton = v.findViewById(R.id.restaurantprofile_signOutButton)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signOutButton.setOnClickListener{
            auth.signOut()
            activity?.finish()
        }
    }

}