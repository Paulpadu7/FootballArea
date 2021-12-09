package com.example.footballarea.DBRepo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val SQL_CREATE_RESERVATION_ENTRIES =
    """ CREATE TABLE IF NOT EXISTS ${ReservationContract.ReservationEntry.DB_TABLE} (
            ${ReservationContract.ReservationEntry.COLUMN_ID} INTEGER PRIMARY KEY,
            ${ReservationContract.ReservationEntry.COLUMN_ID_USER} INTEGER,
            ${ReservationContract.ReservationEntry.COLUMN_ID_MATCH} INTEGER,
            ${ReservationContract.ReservationEntry.COLUMN_TICKETS} INTEGER,
            ${ReservationContract.ReservationEntry.COLUMN_RESERVATION_DATE} TEXT,
            ${ReservationContract.ReservationEntry.COLUMN_PRICE} INTEGER);
    """

private const val SQL_CREATE_MATCH_ENTRIES =
    """ CREATE TABLE IF NOT EXISTS ${MatchContract.MatchEntry.DB_TABLE} (
            ${MatchContract.MatchEntry.COLUMN_ID} INTEGER PRIMARY KEY,
            ${MatchContract.MatchEntry.COLUMN_HOME_TEAM} TEXT,
            ${MatchContract.MatchEntry.COLUMN_AWAY_TEAM} TEXT,
            ${MatchContract.MatchEntry.COLUMN_LOCATION} TEXT,
            ${MatchContract.MatchEntry.COLUMN_IMAGE} INTEGER,
            ${MatchContract.MatchEntry.COLUMN_DATE} TEXT,
            ${MatchContract.MatchEntry.COLUMN_TICKETS} INTEGER,
            ${MatchContract.MatchEntry.COLUMN_PRICE} INTEGER);
    """

private const val SQL_DELETE_RESERVATION_ENTRIES =
    "DROP TABLE IF EXISTS ${ReservationContract.ReservationEntry.DB_TABLE}"

private const val SQL_DELETE_MATCH_ENTRIES =
    "DROP TABLE IF EXISTS ${MatchContract.MatchEntry.DB_TABLE}"

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        ReservationContract.DB_NAME,
        null,
        ReservationContract.DB_VERSION
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