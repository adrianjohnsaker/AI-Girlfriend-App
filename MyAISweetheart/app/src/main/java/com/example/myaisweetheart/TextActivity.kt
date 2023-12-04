package com.example.myaisweetheart

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TextActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MessageAdapter
    private lateinit var mEditText: EditText
    private lateinit var mButton: Button

    val dbHelper = DatabaseHelper(this)

    private var mMessages: MutableList<Message> = ArrayList()

    private val template: String  = """Roleplay as my girlfriend. Your name is Yui. You're 21 years old. You are a college student. 
        You love me. You're open to any conversation. Respond with care and concern. You're flirty. 
        Always keep your responses within 70 words.""".trimMargin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.text_message_format)

        mRecyclerView = findViewById(R.id.recycler_view)
        mEditText = findViewById(R.id.edit_text)
        mButton = findViewById(R.id.button)

        mAdapter = MessageAdapter(mMessages)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter


        val cursor: Cursor? = dbHelper.readData()

        // Now you can use the 'cursor' to retrieve data
        if (cursor != null) {
            val columnNameIndex = cursor.getColumnIndex("msg")
            val senderIndex = cursor.getColumnIndex("sender")

            // Check if the column exists in the cursor
            if (columnNameIndex != -1) {
                while (cursor.moveToNext()) {
                    val columnName = cursor.getString(columnNameIndex)
                    var senderFrom = cursor.getString(senderIndex) == "1"

                    mMessages.add(Message(columnName, senderFrom))
                    mAdapter.notifyItemInserted(mMessages.size - 1)
                }
            } else {
                Log.e("AnotherClass", "Column 'columnName' not found in the cursor")
            }

            cursor.close()
        }

        //getResponse(template)

        val settingsBtn: Button = findViewById(R.id.settings_btn)
        settingsBtn.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        mButton.setOnClickListener {
            val text = mEditText.text.toString()
            mMessages.add(Message(text, true))
            mAdapter.notifyItemInserted(mMessages.size - 1)
            getResponse(mEditText.text.toString())
            mEditText.text.clear()
        }
    }

    private var responseCounter = 0

    private fun getResponse(query: String) {
        dbHelper.insertData(query, true)
        mMessages.add(Message("Response$responseCounter", false))
        dbHelper.insertData(mMessages.last().getText(), false)
        responseCounter++

        mAdapter.notifyItemInserted(mMessages.size - 1)
    }


    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("X", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("lastActivity", javaClass.name)
        editor.apply()
    }

}