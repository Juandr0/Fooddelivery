package fragment.user

import adapters.UserSettingsRecycleAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import classes.User
import classes.UserSettings
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import com.google.firebase.firestore.ktx.toObject
import fragment.LoginFragment


class ProfileFragment : Fragment() {

    lateinit var greetingsTextView : TextView
    lateinit var lastOrderRestaurant : TextView
    lateinit var lastOrder : TextView
    lateinit var completeOrderViewLayout : ConstraintLayout
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
        greetingsTextView = v.findViewById(R.id.greetingsTextView)
        lastOrderRestaurant = v.findViewById(R.id.lastOrderRestaurantNameTextView)
        lastOrder = v.findViewById(R.id.lastOrderTextView)
        completeOrderViewLayout = v.findViewById(R.id.completeOrderViewLayout)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    //Initializes the recyclerview so it displays the user settings
    private fun initializeSettingsWithRecyclerView(view : View) {

        recyclerView = view.findViewById(R.id.settingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = UserSettingsRecycleAdapter(ProfileFragment(), settingsList)
        recyclerView.adapter = adapter


        completeOrderViewLayout.setOnClickListener{
            setCurrentFragment(OrderHistoryFragment(), null)
        }

        //Onclicklistener for the entire recyclerview. Will change fragment on click.
        //When statement looks at what the user clicked on and matches it with a corresponding action.
        //I.E Sign out signs out the user and sends them to the login page, and "name" sends the user to an edit-tab
        //where they can change their name
        adapter.setOnItemClickListener(object : UserSettingsRecycleAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val settingToChange = settingsList[position].settings

                when (settingToChange){
                    "Email" -> { return }
                    "Sign out" -> {
                        auth.signOut()
                        setCurrentFragment(LoginFragment(), null)
                    }
                    "Order history" -> {
                        //Fragment that loads full order history.
                        //Leaving empty for now as we need full order history in DB before this is testable.
                        //Probably need to check collection "orders" in DB, look for the current user ID, fetch info
                        //save to list, & display in recyclerview.
                    }
                    else -> {
                        val currentUser = auth.currentUser?.uid.toString()
                        val userSetting = settingsList[position].userSetting.toString()

                        val bundle = Bundle()

                        bundle.putString("settingToChange", settingToChange)
                        bundle.putString("editSetting", userSetting)
                        bundle.putString("userId", currentUser)
                        arguments = bundle
                        setCurrentFragment(userEditSettingsFragment, bundle)


                    }
                }


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