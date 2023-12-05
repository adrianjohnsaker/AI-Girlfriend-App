package com.example.myaisweetheart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PersonalityActivity : AppCompatActivity(){

    private lateinit var shy: SeekBar
    private lateinit var pessimistic: SeekBar
    private lateinit var ordinary: SeekBar
    private lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personality)

        shy = findViewById(R.id.shyBar)
        pessimistic = findViewById(R.id.pessimisticBar)
        ordinary = findViewById(R.id.ordinaryBar)
        nextBtn = findViewById(R.id.toHobbies_btn)

        seekBarListener(shy, 0)
        seekBarListener(pessimistic, 1)
        seekBarListener(ordinary, 69)

        nextBtn.setOnClickListener {
            val nextIntent = Intent(this, HobbiesActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private fun seekBarListener(personality: SeekBar, trait: Int) {
        personality.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                AIManager.setPersonality(personality.progress, trait)
            }
        })
    }
}