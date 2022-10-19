package com.example.fooddeliveryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
     lateinit var userAdressInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userAdressInput=findViewById(R.id.puturadress)

        val continuebutton= findViewById<Button>(R.id.continuebutton)

        //Intent som tar input om användarens adress och skickar vidare till fragment-aktiviteten
        //Intentet nedan ska köras vid knapptryck genom en clicklistener
          continuebutton.setOnClickListener(){
              val userAdressInput= userAdressInput.text.toString()

              val intent = Intent(this, FragmentMenuActivity::class.java)
              if (userAdressInput != null || userAdressInput != "") {
                  intent.getStringExtra(userAdressInput)

                  startActivity(intent)

              }

        }
    }
}



