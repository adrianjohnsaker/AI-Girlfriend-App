package com.example.myaisweetheart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    // Initialize variable
    lateinit var styleSwitch: Switch
    lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        styleSwitch = findViewById(R.id.message_style_switch)
        backBtn = findViewById(R.id.back_btn)

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


        backBtn.setOnClickListener {
            var backIntent = Intent(this, MainActivity::class.java)
            if(styleSwitch.isChecked)
                backIntent = Intent(this, TextActivity::class.java)
            startActivity(backIntent)
        }
    }
}