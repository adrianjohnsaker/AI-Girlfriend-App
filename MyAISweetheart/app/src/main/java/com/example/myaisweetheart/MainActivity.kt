package com.example.myaisweetheart

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


class MainActivity : AppCompatActivity() {

    // creating variables on below line.
    private lateinit var responseTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var queryEdt: TextInputEditText

    val dbHelper = DatabaseHelper(this)

    val template: String  = """Roleplay as my girlfriend. Your name is Yui. You're a young adult. You are a college student. 
        You love me. You're open to any conversation. Respond with care and concern. You're flirty.""".trimMargin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //button click leads to notification page
        val nextButton: Button = findViewById(R.id.notif_button)
        nextButton.setOnClickListener {
            // Start ContentActivity when next_button is clicked
            val intent = Intent(this@MainActivity, NotifActivity::class.java)
            startActivity(intent)
        }

        
        // initializing variables on below line.
        responseTV = findViewById(R.id.idTVResponse)
        questionTV = findViewById(R.id.idTVQuestion)
        queryEdt = findViewById(R.id.idEdtQuery)
        // adding editor action listener for edit text on below line.

        //getResponse(template)
        val cursor: Cursor? = dbHelper.getLastTwoEntries()

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    // Check if the column exists in the cursor
                    val columnIndex = it.getColumnIndex("msg")
                    val column2Index = it.getColumnIndex("sender")

                    if (columnIndex != -1 && column2Index != -1) {
                        val columnValue = it.getString(columnIndex)
                        val column2Value = it.getString(column2Index)

                        if (column2Value == "1") {
                            questionTV.text = columnValue
                        } else {
                            responseTV.text = columnValue
                        }
                    } else {
                        // Handle the case where one or both columns are not found
                        if (columnIndex == -1) {
                            Log.e("YourTag", "Column 'columnName' not found in the cursor")
                        }
                        if (column2Index == -1) {
                            Log.e("YourTag", "Column 'sender' not found in the cursor")
                        }
                        // You might throw an exception, display a user-friendly message, or take other actions
                    }
                } while (it.moveToNext())
            }
        }


        queryEdt.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                // setting response tv on below line.
                responseTV.text = "..."
                // validating text
                if (queryEdt.text.toString().isNotEmpty()) {
                    // calling get response to get the response.
                    getResponse(queryEdt.text.toString())
                } else {
                    Toast.makeText(this, "Chat with your gf..", Toast.LENGTH_SHORT).show()
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
        dbHelper.insertData(query, true)
        if (query != template)
            questionTV.text = query
        queryEdt.setText("")

        //AI response placeholder
        responseTV.text = "Response$responseCounter"
        dbHelper.insertData(responseTV.text.toString(), false)
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
