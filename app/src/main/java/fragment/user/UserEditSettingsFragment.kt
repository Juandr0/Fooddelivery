package fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.fooddeliveryproject.R

class UserEditSettingsFragment : Fragment() {
    private lateinit var userAttributeToChangeTextView : TextView
    private lateinit var userAttributeToChangeEditText : EditText
    private lateinit var saveSettingButton : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_edit_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userAttributeToChangeTextView = view.findViewById(R.id.userAttributeToChangeTextView)
        userAttributeToChangeEditText = view.findViewById(R.id.userAttributeToChangeEditText)
        saveSettingButton = view.findViewById(R.id.saveSettingsButton)
        initializeLayout()
        saveSettingButton.setOnClickListener {
            var currentUserId = auth.currentUser?.uid.toString()

            updateUserInDb(currentUserId)
        }
    }

    //Initialize the layout by filling the textview and edittext
    private fun initializeLayout(){

        val settingToChange = initializeArgumentSettingToChange()
        val settingToChangeUserSetting = initializeArgumentUserSetting()

        userAttributeToChangeTextView.text = settingToChange
        userAttributeToChangeEditText.setText(settingToChangeUserSetting)
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

    // Sends updated information from the edittext to the database.
    private fun updateUserInDb(currentUser : String){
        //db.collection("users").document(currentUser).update(, )
    }

}