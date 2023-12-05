package com.example.myaisweetheart

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myaisweetheart.AIManager.getResponse
import com.google.android.material.textfield.TextInputEditText

class UsernameActivity : AppCompatActivity(){

    private lateinit var nextBtn: Button
    private lateinit var username: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.username)

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
                val nextIntent = Intent(this, GfNameActivity::class.java)
                startActivity(nextIntent)
            }
        }
    }
}