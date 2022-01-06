package com.example.footballarea.DBRepo

import android.content.ContentValues
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.MainActivity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.gson.JsonParseException

import com.google.gson.JsonDeserializationContext

import com.google.gson.JsonElement

import com.google.gson.JsonDeserializer

import com.google.gson.Gson
import java.lang.reflect.Type
import java.time.Instant
import java.time.ZoneId


class MyReservationsViewModel: ViewModel() {
    private val job = Job()
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)
    private val scopeMain = CoroutineScope(job + Dispatchers.Main)
    private val _reservationList = MutableLiveData<ArrayList<Reservation>>()
    val reservationList:LiveData<ArrayList<Reservation>> = _reservationList

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun setDataItem(context: Context): ArrayList<Reservation>{
        Thread.sleep(100)
        var listReservations: ArrayList<Reservation> = ArrayList()
        val dbManager = ReservationDBManager(context)
        val cursor = dbManager.queryAll()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val userId = cursor.getInt(cursor.getColumnIndex("UserId"))
                val matchId = cursor.getInt(cursor.getColumnIndex("MatchId"))
                val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
                val reservationDate = cursor.getString(cursor.getColumnIndex("ReservationDate"))
                val price = cursor.getInt(cursor.getColumnIndex("Price"))
                val dateTime: LocalDateTime = LocalDateTime.parse(reservationDate)
                val status = cursor.getString(cursor.getColumnIndex("Status"))
                if(userId == MainActivity.Companion.user?.id)
                    listReservations.add(Reservation(id,userId,matchId,no_of_tickets,dateTime,price,status))
            } while (cursor.moveToNext())
        }
        dbManager.close()
        return listReservations
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchData(context: Context, result: (ArrayList<Reservation>) -> Unit ){
        scopeIO.launch {
            var secondary_reservationList = setDataItem(context)
            receiveReservationsFromServer(context,secondary_reservationList)
            secondary_reservationList = setDataItem(context)
            _reservationList.postValue(secondary_reservationList)
            scopeMain.launch {
                result.invoke(secondary_reservationList)
            }
        }
    }

    private suspend fun receiveReservationsFromServer(context: Context, reservationList:ArrayList<Reservation>){

        val url = "ws://192.168.100.20:8080/reservation/" + MainActivity.Companion.user?.id.toString()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call?, response: Response) {

                val gson = GsonBuilder().registerTypeAdapter(
                    LocalDateTime::class.java,
                    JsonDeserializer<LocalDateTime> { json: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext? ->
                        LocalDateTime.parse(json.asJsonPrimitive.asString)
                    }).create()

                val body = response.body()?.string()
                //val gson = GsonBuilder().create()
                val listReservationFromServer = gson.fromJson(body,Array<Reservation>::class.java)
                var ok = false
                val dbManager = ReservationDBManager(context)
                for(reservationFromServer:Reservation in listReservationFromServer){
                    println(reservationFromServer)
                    for(reservation: Reservation in reservationList){
                        if(reservationFromServer.idReservation!!.equals(reservation.idReservation))
                        {
                            if(!reservationFromServer.equals(reservation))
                            {
                                val values = ContentValues()
                                values.put("No_of_tickets", reservationFromServer.no_of_tickets)
                                values.put("Price", reservationFromServer.total_price)
                                val selectionArgs = arrayOf(reservation.idReservation.toString())
                                values.put("ReservationDate",reservationFromServer.reservation_date.toString())
                                values.put("Status",reservationFromServer.status)
                                dbManager.update(values, "Id=?", selectionArgs)
                            }
                            ok = true
                        }

                    }
                    if(ok==false){
                        val values = ContentValues()
                        values.put("Id",reservationFromServer.idReservation)
                        values.put("UserId",reservationFromServer.idUser)
                        values.put("MatchId",reservationFromServer.idMatch)
                        values.put("No_of_tickets",reservationFromServer.no_of_tickets)
                        values.put("ReservationDate",reservationFromServer.reservation_date.toString())
                        values.put("Price",reservationFromServer.total_price)
                        values.put("Status",reservationFromServer.status)
                        dbManager.insert(values)
                    }
                    else
                        ok=false
                }
                dbManager.close()
            }
            override fun onFailure(call: Call?, e: IOException) {
                println("Failed to execute the request!")
            }
        })
    }

}