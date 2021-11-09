package com.example.footballarea.Repository

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ReservationRepository() : Parcelable {
    private var mcontext:Context ?=null
    private var reservationList:ArrayList<Reservation> = ArrayList<Reservation>()
    private val filename:String = "reservations.txt"

    constructor(parcel: Parcel) : this() {
    }

    constructor(context: Context) : this() {
        mcontext = context
    }

    fun add(idReservation:Int, idUser:Int, match:Match, no_of_tickets:Int, reservationDate:String, totalPrice:Int){
        var reservation = Reservation(idReservation,idUser,match,no_of_tickets,reservationDate,totalPrice)
        reservationList?.add(reservation)
//        val fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = mcontext!!.openFileOutput(filename, Context.MODE_PRIVATE)
//            fileOutputStream.write(reservation.toString().toByteArray())
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: NumberFormatException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
    fun update(idReservation: Int, no_of_tickets: Int, totalPrice: Int)
    {
        for (reservation in reservationList!!)
        {
            if(reservation.idReservation == idReservation)
            {
                reservation.no_of_tickets = no_of_tickets
                reservation.total_price = totalPrice
            }
        }
    }
    fun delete(idReservation: Int)
    {
        for (reservation in reservationList!!) {
            if (reservation.idReservation == idReservation) {
                reservationList!!.remove(reservation)
            }
        }
    }
    fun getReservation(idReservation: Int) : Reservation? {
        for (reservation in reservationList!!) {
            if (reservation.idReservation == idReservation) {
                return reservation
            }
        }
        return null
    }
    fun getAllReservation() : ArrayList<Reservation>
    {
        return reservationList!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReservationRepository> {
        override fun createFromParcel(parcel: Parcel): ReservationRepository {
            return ReservationRepository(parcel)
        }

        override fun newArray(size: Int): Array<ReservationRepository?> {
            return arrayOfNulls(size)
        }
    }

}