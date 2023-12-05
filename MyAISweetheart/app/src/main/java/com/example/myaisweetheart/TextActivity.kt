package com.example.myaisweetheart

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext

class TextActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MessageAdapter
    private lateinit var mEditText: EditText
    private lateinit var mButton: ImageButton

    val dbHelper = DatabaseHelper(this)

    private var mMessages: MutableList<Message> = ArrayList()

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

        val settingsBtn: ImageButton = findViewById(R.id.settings_btn)
        settingsBtn.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        mButton.setOnClickListener {
            val text = mEditText.text.toString()
            mMessages.add(Message(text, true))
            mAdapter.notifyItemInserted(mMessages.size - 1)
            dbHelper.insertData(mEditText.text.toString(), true)
            getResponse(mEditText.text.toString())
            mEditText.text.clear()
        }
    }

    private fun getResponse(query: String) {
        AIManager.getResponse(applicationContext, query,
            onResponse = { responseMsg ->
                // Handle response for text messaging style
                // For example, update your message list and notify the adapter
                mMessages.add(Message(responseMsg, false))
                dbHelper.insertData(mMessages.last().getText(), false)
                mAdapter.notifyDataSetChanged()
            },
            onError = { error ->
                Log.e("TAGAPI", "Error is: $error")
            }
        )
    }


    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("X", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("lastActivity", javaClass.name)
        editor.apply()
    }

}