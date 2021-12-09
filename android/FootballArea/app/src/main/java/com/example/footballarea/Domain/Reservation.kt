package com.example.footballarea.Domain

import android.os.Parcel
import android.os.Parcelable

class Reservation() : Parcelable
{
    var idReservation:Int ?=null
    var idUser:Int ?=null
    var idMatch: Int?=null
    var no_of_tickets:Int ?=null
    var reservation_date:String ?=null
    var total_price:Int ?=null

    constructor(parcel: Parcel) : this() {
        idReservation = parcel.readValue(Int::class.java.classLoader) as?Int
        idUser = parcel.readValue(Int::class.java.classLoader) as? Int
        idMatch = parcel.readValue(Int::class.java.classLoader) as? Int
        no_of_tickets = parcel.readValue(Int::class.java.classLoader) as? Int
        reservation_date = parcel.readString()
        total_price = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    constructor(idReservation: Int?, idUser: Int?, idMatch: Int?, no_of_tickets: Int?, reservation_date: String?, total_price: Int?)
            : this() {
        this.idReservation = idReservation
        this.idUser = idUser
        this.idMatch = idMatch
        this.no_of_tickets = no_of_tickets
        this.reservation_date = reservation_date
        this.total_price = total_price
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idReservation)
        parcel.writeValue(idUser)
        parcel.writeValue(idMatch)
        parcel.writeValue(no_of_tickets)
        parcel.writeString(reservation_date)
        parcel.writeValue(total_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Reservation(idReservation=$idReservation, idUser=$idUser, match=$idMatch, no_of_tickets=$no_of_tickets, reservation_date=$reservation_date, total_price=$total_price)"
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }
    }
}