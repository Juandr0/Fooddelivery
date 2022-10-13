package com.example.fooddeliveryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Intent som tar input om användarens adress och skickar vidare till fragment-aktiviteten
        //Intentet nedan ska köras vid knapptryck genom en clicklistener

        val intent = Intent(this, FragmentMenuActivity::class.java)
        val userAdressInput = ""//Koppla ihop med ABD's edittext som tar input om användarens adress
        if (userAdressInput != null || userAdressInput != "") {
            intent.getStringExtra(userAdressInput)
        }
        startActivity(intent)
    }
}



