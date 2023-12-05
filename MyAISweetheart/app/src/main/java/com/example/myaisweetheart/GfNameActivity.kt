package com.example.myaisweetheart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class GfNameActivity : AppCompatActivity(){

    private lateinit var nextBtn: Button
    private lateinit var gfname: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gfname)

        nextBtn = findViewById(R.id.toPersonality_btn)
        gfname = findViewById(R.id.gfname_input)

        nextBtn.setOnClickListener {
            if (gfname.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "Hey come on! I need you to call me by something.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AIManager.setGfName(gfname.text.toString())
                val nextIntent = Intent(this, PersonalityActivity::class.java)
                startActivity(nextIntent)
            }
        }
    }
}