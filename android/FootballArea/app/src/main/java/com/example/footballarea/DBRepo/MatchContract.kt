package com.example.footballarea.DBRepo

import android.provider.BaseColumns

object MatchEntry : BaseColumns{
        const val DB_TABLE = "Match"
        const val COLUMN_ID = "Id"
        const val COLUMN_HOME_TEAM = "HomeTeam"
        const val COLUMN_AWAY_TEAM = "AwayTeam"
        const val COLUMN_LOCATION = "Location"
        const val COLUMN_IMAGE = "Image"
        const val COLUMN_DATE = "Date"
        const val COLUMN_TICKETS = "No_of_tickets"
        const val COLUMN_PRICE = "Price"
}