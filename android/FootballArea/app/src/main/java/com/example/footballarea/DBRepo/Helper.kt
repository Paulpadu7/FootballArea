package com.example.footballarea.DBRepo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val SQL_CREATE_RESERVATION_ENTRIES =
    """ CREATE TABLE IF NOT EXISTS ${ReservationEntry.DB_TABLE} (
            ${ReservationEntry.COLUMN_ID} INTEGER PRIMARY KEY,
            ${ReservationEntry.COLUMN_ID_USER} INTEGER,
            ${ReservationEntry.COLUMN_ID_MATCH} INTEGER,
            ${ReservationEntry.COLUMN_TICKETS} INTEGER,
            ${ReservationEntry.COLUMN_RESERVATION_DATE} TEXT,
            ${ReservationEntry.COLUMN_PRICE} INTEGER,
            ${ReservationEntry.COLUMN_STATUS} TEXT);
    """

private const val SQL_CREATE_MATCH_ENTRIES =
    """ CREATE TABLE IF NOT EXISTS ${MatchEntry.DB_TABLE} (
            ${MatchEntry.COLUMN_ID} INTEGER PRIMARY KEY,
            ${MatchEntry.COLUMN_HOME_TEAM} TEXT,
            ${MatchEntry.COLUMN_AWAY_TEAM} TEXT,
            ${MatchEntry.COLUMN_LOCATION} TEXT,
            ${MatchEntry.COLUMN_IMAGE} INTEGER,
            ${MatchEntry.COLUMN_DATE} TEXT,
            ${MatchEntry.COLUMN_TICKETS} INTEGER,
            ${MatchEntry.COLUMN_PRICE} INTEGER);
    """

private const val SQL_DELETE_RESERVATION_ENTRIES =
    "DROP TABLE IF EXISTS ${ReservationEntry.DB_TABLE}"

private const val SQL_DELETE_MATCH_ENTRIES =
    "DROP TABLE IF EXISTS ${MatchEntry.DB_TABLE}"

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        FootballAreaContract.DB_NAME,
        null,
        FootballAreaContract.DB_VERSION
    )
{

    var context: Context? = context

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_RESERVATION_ENTRIES)
        db!!.execSQL(SQL_CREATE_MATCH_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_RESERVATION_ENTRIES)
        db!!.execSQL(SQL_DELETE_MATCH_ENTRIES)
    }
}