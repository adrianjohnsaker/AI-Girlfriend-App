package com.example.myaisweetheart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ProfileDatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    private val CREATE_PROFILE_TABLE = "CREATE TABLE profile (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, gf TEXT, shy INTEGER, pessimistic INTEGER, ordinary INTEGER, hobbies TEXT)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_PROFILE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade here
    }

    fun readData(): Cursor? {
        val db = this.readableDatabase
        val query = "SELECT * FROM profile"
        return db.rawQuery(query, null)
    }

    private fun deleteData() {
        val db = this.writableDatabase
        db.delete("profile", null, null)
    }

    fun updateUser(user: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        db.delete("profile", "user", null)
        contentValues.put("user", user)
    }

    fun updateGf(gf: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        db.delete("profile", "gf", null)
        contentValues.put("gf", gf)
    }

    fun updateShy(shy: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        db.delete("profile", "shy", null)
        contentValues.put("shy", shy)
    }

    fun updatePessimistic(pessimistic: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        db.delete("profile", "pessimistic", null)
        contentValues.put("pessimistic", pessimistic)
    }

    fun updateOrdinary(ordinary: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        db.delete("profile", "ordinary", null)
        contentValues.put("ordinary", ordinary)
    }

    fun updateHobbies(hobbies: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        db.delete("profile", "hobbies", null)
        contentValues.put("hobbies", hobbies)
    }

    fun updateProfileData(user: String, gf: String, shy: Int, pessimistic: Int, ordinary: Int, hobbies: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        deleteData()
        contentValues.put("user", user)
        contentValues.put("gf", gf)
        contentValues.put("shy", shy)
        contentValues.put("pessimistic", pessimistic)
        contentValues.put("ordinary", ordinary)
        contentValues.put("hobbies", hobbies)
        db.insert("profile", null, contentValues)
    }

    companion object {
        private const val DATABASE_NAME = "prodb.db"
        private const val DATABASE_VERSION = 1
    }
}