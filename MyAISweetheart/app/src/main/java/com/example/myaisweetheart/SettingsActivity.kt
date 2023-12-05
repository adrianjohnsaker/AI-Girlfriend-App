package com.example.myaisweetheart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    // Initialize variable
    private lateinit var styleSwitch: Switch
    private lateinit var backBtn: ImageButton
    private lateinit var user: Button
    private lateinit var gf: Button
    private lateinit var personality: Button
    private lateinit var hobbies: Button
    private lateinit var clearBtn: Button
    private lateinit var Btn: Button

    val dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        styleSwitch = findViewById(R.id.message_style_switch)
        backBtn = findViewById(R.id.back_btn)
        user = findViewById(R.id.changeUserName_btn)
        gf = findViewById(R.id.changeGfName_btn)
        personality = findViewById(R.id.changePersonality_btn)
        hobbies = findViewById(R.id.changeHobbies_btn)
        clearBtn = findViewById(R.id.clear_btn)
        Btn = findViewById(R.id.button5)


        val sharedPreferences = getSharedPreferences("save", MODE_PRIVATE)
        styleSwitch.setChecked(sharedPreferences.getBoolean("value", true))

        styleSwitch.setOnClickListener(View.OnClickListener {
            if (styleSwitch.isChecked()) {
                // When switch checked
                val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
                editor.putBoolean("value", true)
                editor.apply()
                styleSwitch.setChecked(true)
            } else {
                // When switch unchecked
                val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
                editor.putBoolean("value", false)
                editor.apply()
                styleSwitch.setChecked(false)
            }
        })

        clearBtn.setOnClickListener(){
            dbHelper.deleteData()
        }

        user.setOnClickListener{

        }

        backBtn.setOnClickListener {
            var backIntent = Intent(this, MainActivity::class.java)
            if(styleSwitch.isChecked)
                backIntent = Intent(this, TextActivity::class.java)
            startActivity(backIntent)
        }
        Btn.setOnClickListener {

            var Intent = Intent(this, UsernameActivity::class.java)
            startActivity(Intent)
        }
    }
}