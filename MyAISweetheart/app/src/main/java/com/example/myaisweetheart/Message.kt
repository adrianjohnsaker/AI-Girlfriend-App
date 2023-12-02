package com.example.myaisweetheart

class Message(private val text: String, private val isSentByUser: Boolean) {

    fun getText(): String {
        return text
    }

    fun isSentByUser(): Boolean {
        return isSentByUser
    }
}