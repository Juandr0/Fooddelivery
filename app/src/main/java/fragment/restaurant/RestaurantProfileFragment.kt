package fragment.restaurant

import adapters.RestaurantSettingsRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.User
import classes.UserSettings
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.auth
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject

lateinit var signOutButton : Button

class RestaurantProfileFragment : Fragment() {

    lateinit var restaurantGreetingsTextView : TextView

    lateinit var recyclerView : RecyclerView
    private val settingsList = mutableListOf<UserSettings>()
    private val restaurantEditSettingsFragment = RestaurantEditSettingsFragment()

    override fun onResume() {
        super.onResume()
        settingsList.clear()
        //setting & userSetting
        val currentUser = fragment.user.auth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                restaurantGreetingsTextView.text =
                    getString(R.string.greetings) + ", ${user!!.name}"


                //If-statement that fills the recyclerView with user settings, if it's not already filled

                initializeSettingsWithRecyclerView(requireView())
                settingsList.add(UserSettings(getString(R.string.name), "${user.name}"))
                settingsList.add(UserSettings(getString(R.string.email), "${user.email}"))
                settingsList.add(UserSettings(getString(R.string.address), "${user.address}"))
                settingsList.add(UserSettings(getString(R.string.phonenumber), "${user.phoneNumber}"))
                settingsList.add(UserSettings(getString(R.string.sign_out)), )

            }
            .addOnFailureListener {
                settingsList.add(UserSettings(getString(R.string.name)))
                settingsList.add(UserSettings(getString(R.string.email)))
                settingsList.add(UserSettings(getString(R.string.address)))
                settingsList.add(UserSettings(getString(R.string.phonenumber)))
                settingsList.add(UserSettings(getString(R.string.order_history)))
            }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_restaurant_profile, container, false)
        signOutButton = v.findViewById(R.id.restaurantprofile_signOutButton)
        restaurantGreetingsTextView = v.findViewById(R.id.restaurantGreetingsTextView)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signOutButton.setOnClickListener{
            auth.signOut()
            activity?.finish()
        }
    }

    //Initializes the recyclerview so it displays the user settings
    private fun initializeSettingsWithRecyclerView(view : View) {

        recyclerView = view.findViewById(R.id.restaurantSettingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = RestaurantSettingsRecyclerAdapter(RestaurantProfileFragment(), settingsList)
        recyclerView.adapter = adapter


        //Onclicklistener for the entire recyclerview. Will change fragment on click.
        //When statement looks at what the user clicked on and matches it with a corresponding action.
        //I.E Sign out signs out the user and sends them to the login page, and "name" sends the user to an edit-tab
        //where they can change their name
        adapter.setOnItemClickListener(object : RestaurantSettingsRecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val settingToChange = settingsList[position].settings

                when (settingToChange){
                    "Email" -> { return }
                    "Sign out" -> {
                        auth.signOut()
                        activity?.finish()
                    }
                    else -> {
                        val currentUser = fragment.user.auth.currentUser?.uid.toString()
                        val userSetting = settingsList[position].userSetting.toString()

                        val bundle = Bundle()

                        bundle.putString("settingToChange", settingToChange)
                        bundle.putString("editSetting", userSetting)
                        bundle.putString("userId", currentUser)
                        arguments = bundle
                        setCurrentFragment(restaurantEditSettingsFragment, bundle)


                    }
                }


            }
        })
    }

    open fun setCurrentFragment(fragment : Fragment, bundle: Bundle?){
        val fragmentManager = parentFragmentManager
        fragment.arguments = bundle
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.restaurantInterfaceContainer, fragment)
        transaction.commit()
    }

}