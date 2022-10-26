package fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.User
import com.example.fooddeliveryproject.*
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import fragment.LoginFragment


class ProfileFragment : Fragment() {

    lateinit var greetingsTextView : TextView
    lateinit var signOutButton : Button
    lateinit var lastOrderRestaurant : TextView
    lateinit var lastOrder : TextView
    lateinit var recyclerView : RecyclerView

    private val settingsList = mutableListOf<UserSettings>()
    private val userEditSettingsFragment = UserEditSettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {

        super.onResume()
        settingsList.clear()
        //setting & userSetting
        val currentUser = auth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                greetingsTextView.text = getString(R.string.greetings) + ", ${user!!.name}"
                lastOrderRestaurant.text = "${user.lastOrderRestaurant}"
                lastOrder.text = "${user.lastOrder}"
                //If-statement that fills the recyclerView with user settings, if it's not already filled

                initializeSettingsWithRecyclerView(requireView())
                settingsList.add(UserSettings(getString(R.string.name), "${user.name}"))
                settingsList.add(UserSettings(getString(R.string.email), "${user.email}"))
                settingsList.add(UserSettings(getString(R.string.address), "${user.address}"))
                settingsList.add(UserSettings(getString(R.string.phonenumber), "${user.phoneNumber}"))
                settingsList.add(UserSettings(getString(R.string.order_history), ))
                settingsList.add(UserSettings())
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
            val loginFragment = LoginFragment()
            setCurrentFragment(loginFragment, null )
        }


    }

    //Initializes the recyclerview so it displays the user settings
    private fun initializeSettingsWithRecyclerView(view : View) {

        recyclerView = view.findViewById(R.id.settingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = UserSettingsRecycleAdapter(ProfileFragment(), settingsList)
        recyclerView.adapter = adapter


        //Onclicklistener for the entire recyclerview. Will change fragment on click.
        adapter.setOnItemClickListener(object : UserSettingsRecycleAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val currentUser = auth.currentUser?.uid.toString()
                val settingToChange = settingsList[position].settings
                val userSetting = settingsList[position].userSetting.toString()
                var setting = ""

                when (settingToChange) {
                    "Name" -> { setting = "name" }
                    "Email" -> { setting = "email"}
                    "Address" -> {setting = "address"}
                    "Phone number" -> {setting = "phoneNumber"}
                    "Order history" -> {setting = "orderHistory"
                    }
                }
                val bundle = Bundle()


                bundle.putString("settingToChange", setting)
                bundle.putString("editSetting", userSetting)
                bundle.putString("userId", currentUser)
                arguments = bundle
                setCurrentFragment(userEditSettingsFragment, bundle)
            }
        })
    }



    open fun setCurrentFragment(fragment : Fragment, bundle: Bundle?){

        val fragmentManager = parentFragmentManager
        fragment.arguments = bundle
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


}