package com.example.myaisweetheart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    // Initialize variable
    private lateinit var styleSwitch: Switch
    private lateinit var backBtn: ImageButton
    private lateinit var user: ImageButton
    private lateinit var gf: ImageButton
    private lateinit var personality: ImageButton
    private lateinit var hobbies: ImageButton
    private lateinit var clearBtn: ImageButton
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


        val sharedPreferences = getSharedPreferences("save", MODE_PRIVATE)
        styleSwitch.setChecked(sharedPreferences.getBoolean("value", false))

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
            Toast.makeText(this, "Cleared Chat", Toast.LENGTH_SHORT).show()
        }

        user.setOnClickListener(){
            var Intent = Intent(this, ChangeUserActivity::class.java)
            startActivity(Intent)
        }

        gf.setOnClickListener(){
            var Intent = Intent(this, ChangeGfActivity::class.java)
            startActivity(Intent)
        }

        personality.setOnClickListener(){
            var Intent = Intent(this, ChangePersonalityActivity::class.java)
            startActivity(Intent)
        }

        hobbies.setOnClickListener(){
            var Intent = Intent(this, ChangeHobbiesActivity::class.java)
            startActivity(Intent)
        }

        backBtn.setOnClickListener {
            var backIntent = Intent(this, MainActivity::class.java)
            if(styleSwitch.isChecked)
                backIntent = Intent(this, TextActivity::class.java)
            startActivity(backIntent)
        }
    }
}