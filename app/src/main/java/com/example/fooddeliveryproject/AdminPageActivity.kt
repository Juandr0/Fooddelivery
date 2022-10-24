package com.example.fooddeliveryproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminPageActivity : AppCompatActivity() {

    private lateinit var signOutButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)

        signOutButton = findViewById(R.id.adminpage_signOut)

        signOutButton.setOnClickListener{
            auth.signOut()
            finish()
        }
    }

}