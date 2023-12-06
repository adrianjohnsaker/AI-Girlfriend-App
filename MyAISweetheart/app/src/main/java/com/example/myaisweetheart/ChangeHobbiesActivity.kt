package com.example.myaisweetheart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class ChangeHobbiesActivity : AppCompatActivity(){
    private lateinit var nextBtn: ImageButton
    private var toggledCount: Int = 0
    private var hobbies = ""
    private val dbHelper = ProfileDatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hobbies)

        nextBtn = findViewById(R.id.toChat_btn)

        val toggleButtons: Array<ToggleButton> = arrayOf(
            findViewById(R.id.traveling),
            findViewById(R.id.exercise),
            findViewById(R.id.movies),
            findViewById(R.id.dancing),
            findViewById(R.id.cooking),
            findViewById(R.id.outdoors),
            findViewById(R.id.politics),
            findViewById(R.id.pets),
            findViewById(R.id.photography),
            findViewById(R.id.sports),
            findViewById(R.id.music),
            findViewById(R.id.videogames),
            findViewById(R.id.karaoke),
            findViewById(R.id.art),
            findViewById(R.id.reading),
            findViewById(R.id.writing),
            findViewById(R.id.fashion),
            findViewById(R.id.fishing),
            findViewById(R.id.shopping),
            findViewById(R.id.socialmedia),
            findViewById(R.id.anime),
            findViewById(R.id.gardening),
            findViewById(R.id.drinking),
            findViewById(R.id.astrology),
            findViewById(R.id.drawing),
            findViewById(R.id.boardgames),
            findViewById(R.id.shows)
        )

        for (toggleButton in toggleButtons) {
            toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
                handleToggleButtonCheckedChanged(isChecked, toggleButtons)
            }
        }

        nextBtn.setOnClickListener {
            for (toggleButton in toggleButtons) {
                if(toggleButton.isChecked) {
                    hobbies += toggleButton.text.toString() + " "
                }
            }
            AIManager.setHobbies(hobbies)
            dbHelper.updateHobbies(hobbies)

            val nextIntent = Intent(this, SettingsActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private fun handleToggleButtonCheckedChanged(isChecked: Boolean, toggleButtons: Array<ToggleButton>) {
        if (isChecked) {
            toggledCount++
            if (toggledCount < 5) {
            } else {
                for (toggleButton in toggleButtons) {
                    toggleButton.isClickable = toggleButton.isChecked
                }
            }
        } else {
            toggledCount--
            if (toggledCount == 4) {
                for (toggleButton in toggleButtons) {
                    toggleButton.isClickable = true
                }
            }
        }
    }
}