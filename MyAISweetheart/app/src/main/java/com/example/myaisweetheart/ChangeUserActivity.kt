package com.example.myaisweetheart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ChangeUserActivity : AppCompatActivity() {

    private lateinit var nextBtn: ImageButton
    private lateinit var username: TextInputEditText
    private val dbHelper = ProfileDatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_user)

        nextBtn = findViewById(R.id.toGfName_btn)
        username = findViewById(R.id.name_input)

        nextBtn.setOnClickListener {
            if (username.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "You can't be this mysterious, I need to call you by something.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AIManager.setusername(username.text.toString())
                dbHelper.updateUser(username.text.toString())
                val nextIntent = Intent(this, SettingsActivity::class.java)
                startActivity(nextIntent)
            }
        }
    }
}