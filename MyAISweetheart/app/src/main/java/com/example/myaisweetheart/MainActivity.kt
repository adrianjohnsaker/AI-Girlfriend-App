package com.example.myaisweetheart

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    // creating variables on below line.
    private lateinit var responseTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var queryEdt: TextInputEditText


    val template: String  = """Roleplay as my girlfriend. Your name is Yui. You're a young adult. You are a college student. 
        You love me. You're open to any conversation. Respond with care and concern. You're flirty.""".trimMargin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing variables on below line.
        responseTV = findViewById(R.id.idTVResponse)
        questionTV = findViewById(R.id.idTVQuestion)
        queryEdt = findViewById(R.id.idEdtQuery)
        // adding editor action listener for edit text on below line.

        getResponse(template)

        queryEdt.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                // setting response tv on below line.
                responseTV.text = "..."
                // validating text
                if (queryEdt.text.toString().isNotEmpty()) {
                    // calling get response to get the response.
                    getResponse(queryEdt.text.toString())
                } else {
                    Toast.makeText(this, "Please enter your query..", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })

        val settingsBtn: Button = findViewById(R.id.settings_btn)
        settingsBtn.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }

    private var responseCounter = 0

    private fun getResponse(query: String) {

        if (query != template)
            questionTV.text = query
        queryEdt.setText("")

        //AI response placeholder
        responseTV.text = "Response$responseCounter"
        responseCounter++
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("X", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("lastActivity", javaClass.name)
        editor.apply()
    }
}
