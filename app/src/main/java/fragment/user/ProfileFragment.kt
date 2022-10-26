package fragment.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryproject.*
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

        signOutButton.setOnClickListener{
            auth.signOut()
            val loginFragment = LoginFragment()
            setCurrentFragment(loginFragment, null )
        }



        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                greetingsTextView.text = getString(R.string.greetings) + ", ${user!!.name}"
                lastOrderRestaurant.text = "${user.lastOrderRestaurant}"
                lastOrder.text = "${user.lastOrder}"

                //
                //If-statement that fills the recyclerView with user settings, if it's not already filled
                if (settingsList.size == 0){
                    settingsList.add(UserSettings(getString(R.string.name), "${user.name}"))
                    settingsList.add(UserSettings(getString(R.string.email), "${user.email}"))
                    settingsList.add(UserSettings(getString(R.string.address), "${user.address}"))
                    settingsList.add(UserSettings(getString(R.string.phonenumer), "${user.phoneNumber}"))
                    settingsList.add(UserSettings(getString(R.string.order_history), ))
                    settingsList.add(UserSettings())
                    settingsList.add(UserSettings(getString(R.string.sign_out)), )
                }
                initializeSettingsWithRecyclerView(view)
            }
            .addOnFailureListener {
                settingsList.add(UserSettings(getString(R.string.name)))
                settingsList.add(UserSettings(getString(R.string.email)))
                settingsList.add(UserSettings(getString(R.string.address)))
                settingsList.add(UserSettings(getString(R.string.phonenumer)))
                settingsList.add(UserSettings(getString(R.string.order_history)))
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

                val bundle = Bundle()
                bundle.putString("settingToChange", settingToChange)
                bundle.putString("editSetting", userSetting)
                bundle.putString("userId", currentUser)
                arguments = bundle
                setCurrentFragment(userEditSettingsFragment, bundle)
            }
        })
    }



    private fun setCurrentFragment(fragment : Fragment, bundle: Bundle?){

        val fragmentManager = parentFragmentManager
        fragment.arguments = bundle
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


}