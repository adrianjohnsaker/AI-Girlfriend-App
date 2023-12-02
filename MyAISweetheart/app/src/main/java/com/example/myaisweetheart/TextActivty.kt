package com.example.myaisweetheart

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.example.myaisweetheart.AIManager.getResponse
import org.json.JSONException
import org.json.JSONObject

class TextActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MessageAdapter
    private lateinit var mEditText: EditText
    private lateinit var mButton: Button

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

        val settingsBtn: Button = findViewById(R.id.settings_btn)
        settingsBtn.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        mButton.setOnClickListener {
            getResponse(mEditText.text.toString())
        }
    }

    private var responseCounter = 0;

    private fun getResponse(query: String) {

        val text: String = mEditText.text.toString()

        mMessages.add(Message(text, true))
        mAdapter.notifyItemInserted(mMessages.size - 1)
        mEditText.text.clear()

        mMessages.add(
            Message(
                "Response$responseCounter", false
            )
        )
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