package fragment.user

import android.graphics.Color
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.db
import java.util.*

class UserEditSettingsFragment : Fragment() {
    private lateinit var userAttributeToChangeTextView : TextView
    private lateinit var userAttributeToChangeEditText : EditText
    private lateinit var saveSettingButton : Button

    private lateinit var goBackButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_edit_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userAttributeToChangeTextView = view.findViewById(R.id.completeOrderHistoryTextView)
        userAttributeToChangeEditText = view.findViewById(R.id.userAttributeToChangeEditText)
        saveSettingButton = view.findViewById(R.id.saveSettingsButton)
        goBackButton = view.findViewById(R.id.editSettingsGoBackButton)

        val settingToChange = initializeArgumentSettingToChange()
        val userSetting = initializeArgumentUserSetting()
        when (settingToChange){
            "Name" -> userAttributeToChangeEditText.hint = getString(R.string.enter_name)
            "Address" -> userAttributeToChangeEditText.hint = getString(R.string.enter_address)
            "Phone number" -> userAttributeToChangeEditText.hint = getString(R.string.enter_phonenr)
        }
        initializeLayout(settingToChange, userSetting)

        //Onclick: Takes the text in edittext and sends it to fun updateUserInDb
        saveSettingButton.setOnClickListener {

            val emptyCheck = isFieldEmpty()

            if (!emptyCheck){
                val currentUserId = auth.currentUser!!.uid
                val newSetting = userAttributeToChangeEditText.text.toString()
                updateUserInDb(currentUserId, settingToChange, newSetting)
                setCurrentFragmentToProfile()
            } else {
                var redColor = "#FFB0B0"
                userAttributeToChangeEditText.setBackgroundColor(Color.parseColor(redColor))
                Toast.makeText(context, getString(R.string.no_empty_fields,
                    userAttributeToChangeTextView.text.toString().lowercase(Locale.getDefault())
                ),Toast.LENGTH_SHORT).show()

            }


        }

        goBackButton.setOnClickListener {
            setCurrentFragmentToProfile()
        }


    }

    private fun isFieldEmpty() : Boolean{
        return userAttributeToChangeEditText.text.isEmpty()
    }


    //Initialize the layout by filling the textview and edittext
    //If the setting to be changed is a phone number, only digits will be displayed -
    //and vice versa if it's any other setting but with letters.
    private fun initializeLayout(settingToChange: String, settingToChangeUserSetting : String){
        userAttributeToChangeTextView.text = settingToChange
        userAttributeToChangeEditText.setText(settingToChangeUserSetting)

        if (settingToChange == "Phone number"){
            userAttributeToChangeEditText.inputType = TYPE_CLASS_NUMBER
        } else {
            userAttributeToChangeEditText.inputType = TYPE_CLASS_TEXT
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


// Sends updated information from the edittext to the database after checking if the setting is a phone number.
// If it's a phone number it sends number as int instead of string
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

    private fun setCurrentFragmentToProfile(){
        val profileFragment = ProfileFragment()
        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, profileFragment)
        transaction.commit()
    }

}
