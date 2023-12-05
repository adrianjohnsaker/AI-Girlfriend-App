package com.example.myaisweetheart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Dispatcher : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("X", Context.MODE_PRIVATE)

        // Get the last viewed activity class name from SharedPreferences
        val lastActivityClassName =
            sharedPreferences.getString("lastActivity", UsernameActivity::class.java.name)

        // Launch the last viewed activity
        startActivity(Intent(this, Class.forName(lastActivityClassName)))
        finish()
    }
}
