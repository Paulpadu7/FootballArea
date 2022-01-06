package com.example.footballarea.DBRepo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class MatchDBManager(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private val db: SQLiteDatabase by lazy { dbHelper.writableDatabase }

    fun insert(values: ContentValues): Long {
        return db.insert(MatchEntry.DB_TABLE, "", values)
    }

    fun queryAll(): Cursor {
        return db.rawQuery("select * from ${MatchEntry.DB_TABLE}", null)
    }

    fun queryOne(id: Int): Cursor {
        return db.rawQuery("select * from ${MatchEntry.DB_TABLE} where ${MatchEntry.COLUMN_ID} = $id", null)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db.delete(MatchEntry.DB_TABLE, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return db.update(MatchEntry.DB_TABLE, values, selection, selectionArgs)
    }

    fun close() {
        db.close()
    }
}