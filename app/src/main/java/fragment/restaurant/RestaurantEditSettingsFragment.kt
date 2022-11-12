package fragment.restaurant

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import fragment.user.auth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantEditSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantEditSettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var restaurantAttributeToChangeTextView : TextView
    private lateinit var restaurantAttributeToChangeEditText : EditText
    private lateinit var restaurantSaveSettingButton : Button

    private lateinit var restaurantGoBackButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_edit_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantAttributeToChangeTextView = view.findViewById(R.id.restaurantSettingView)
        restaurantAttributeToChangeEditText = view.findViewById(R.id.restaurantAttributeToChangeEditText)
        restaurantSaveSettingButton = view.findViewById(R.id.restaurantSaveSettingsButton)
        restaurantGoBackButton = view.findViewById(R.id.restaurantEditSettingsGoBackButton)

        val settingToChange = initializeArgumentSettingToChange()
        val userSetting = initializeArgumentUserSetting()
        when (settingToChange){
            "Name" -> restaurantAttributeToChangeEditText.hint = getString(R.string.enter_name)
            "Address" -> restaurantAttributeToChangeEditText.hint = getString(R.string.enter_address)
            "Phone number" -> restaurantAttributeToChangeEditText.hint = getString(R.string.enter_phonenr)
        }
        initializeLayout(settingToChange, userSetting)

        //Onclick: Takes the text in edittext and sends it to fun updateUserInDb
        restaurantSaveSettingButton.setOnClickListener {

            val emptyCheck = isFieldEmpty()

            if (!emptyCheck){
                val currentUserId = auth.currentUser!!.uid
                val newSetting = restaurantAttributeToChangeEditText.text.toString()
                updateUserInDb(currentUserId, settingToChange, newSetting)
                setCurrentFragmentToRestaurantProfile()
            } else {
                var redColor = "#FFB0B0"
                restaurantAttributeToChangeEditText.setBackgroundColor(Color.parseColor(redColor))
                Toast.makeText(context, getString(R.string.no_empty_fields, restaurantAttributeToChangeTextView.text.toString().toLowerCase()),
                    Toast.LENGTH_SHORT).show()

            }


        }

        restaurantGoBackButton.setOnClickListener {
            setCurrentFragmentToRestaurantProfile()
        }



    }

    private fun isFieldEmpty() : Boolean{
        return restaurantAttributeToChangeEditText.text.isEmpty()
    }

    //Initialize the layout by filling the textview and edittext
    //If the setting to be changed is a phone number, only digits will be displayed -
    //and vice versa if it's any other setting but with letters.
    private fun initializeLayout(settingToChange: String, settingToChangeUserSetting : String){
        restaurantAttributeToChangeTextView.text = settingToChange
        restaurantAttributeToChangeEditText.setText(settingToChangeUserSetting)

        if (settingToChange == "Phone number"){
            restaurantAttributeToChangeEditText.inputType = InputType.TYPE_CLASS_NUMBER
        } else {
            restaurantAttributeToChangeEditText.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    //Initializes the argument setting to change from profile fragment
    private fun initializeArgumentSettingToChange() : String{
        val args = this.arguments
        return args?.get("settingToChange").toString()
    }

    //Initializes the edittext that  setting  to change from profile fragment
    private fun initializeArgumentUserSetting() : String{
        val args = this.arguments
        return args?.get("editSetting").toString()
    }

    private fun updateUserInDb(currentUser : String, settingToChange : String, userSetting : String){

        var setting = ""
        when (settingToChange) {
            "Name" -> { setting = "name" }
            "Email" -> { setting = "email"}
            "Address" -> {setting = "address"}
            "Phone number" -> {setting = "phoneNumber"}
            "Order history" -> {setting = "orderHistory"
            }
        }
        val docRef = db.collection("users").document(currentUser)

        //Usersetting som int
        if (setting == "phoneNumber"){
            val mapUpdateAsInt = mapOf(
                setting to userSetting.toInt()
            )
            docRef.update(mapUpdateAsInt)
                .addOnSuccessListener {
                    Log.d("!!!", "Update success!")
                }

        } else {
            val mapUpdate = mapOf(
                setting to userSetting
            )
            docRef.update(mapUpdate)
                .addOnSuccessListener {
                    Log.d("!!!", "Update success!")
                }
        }
    }

    private fun setCurrentFragmentToRestaurantProfile(){
        val restaurantProfileFragment = RestaurantProfileFragment()
        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.restaurantInterfaceContainer, restaurantProfileFragment)
        transaction.commit()
    }




























    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestaurantEditSettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestaurantEditSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}