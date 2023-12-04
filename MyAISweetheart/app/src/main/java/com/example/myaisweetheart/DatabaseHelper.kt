package com.example.myaisweetheart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_TABLE =
        "CREATE TABLE messages (id INTEGER PRIMARY KEY AUTOINCREMENT, msg TEXT, sender BOOLEAN)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE);
    }

    // Insert data
    fun insertData(msg: String?, sender: Boolean) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("msg", msg)
        contentValues.put("sender", sender)
        db.insert("messages", null, contentValues)
    }

    // Read data
    fun readData(): Cursor? {
        val db = this.readableDatabase
        val query = "SELECT * FROM messages"
        return db.rawQuery(query, null)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade here
    }

    // Delete data
    fun deleteData() {
        val db = this.writableDatabase
        db.delete("messages", null, null)
    }

    fun getLastTwoEntries(): Cursor? {
        val db = this.readableDatabase
        val query = "SELECT * FROM messages ORDER BY id DESC LIMIT 2"
        return db.rawQuery(query, null)
    }


    companion object {
        private const val DATABASE_NAME = "msgdb.db"
        private const val DATABASE_VERSION = 1
    }
}