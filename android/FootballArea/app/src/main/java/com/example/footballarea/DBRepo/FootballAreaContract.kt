package com.example.footballarea.DBRepo

import android.provider.BaseColumns

object FootballAreaContract {
    const val DB_NAME = "FootballArea"
    const val DB_VERSION = 1
}
object ReservationEntry : BaseColumns{
    const val DB_TABLE = "Reservation"
    const val COLUMN_ID = "Id"
    const val COLUMN_ID_USER = "UserId"
    const val COLUMN_ID_MATCH = "MatchId"
    const val COLUMN_TICKETS = "No_of_tickets"
    const val COLUMN_RESERVATION_DATE = "ReservationDate"
    const val COLUMN_PRICE = "Price"
    const val COLUMN_STATUS = "Status"
}