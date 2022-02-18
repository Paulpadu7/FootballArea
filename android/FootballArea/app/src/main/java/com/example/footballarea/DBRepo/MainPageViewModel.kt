package com.example.footballarea.DBRepo

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.MainActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainPageViewModel: ViewModel() {
    private val job = Job()
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)
    private val scopeMain = CoroutineScope(job + Dispatchers.Main)
    private val _matchList = MutableLiveData<ArrayList<Match>>()
    val matchList:LiveData<ArrayList<Match>> = _matchList

    private suspend fun setDataItem(context: Context): ArrayList<Match>{
        Thread.sleep(100)
        val dbManager = MatchDBManager(context)
        var listMatch:ArrayList<Match> = ArrayList()
        val cursor = dbManager.queryAll()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val homeTeam = cursor.getString(cursor.getColumnIndex("HomeTeam"))
                val awayTeam = cursor.getString(cursor.getColumnIndex("AwayTeam"))
                val location = cursor.getString(cursor.getColumnIndex("Location"))
                val image = cursor.getString(cursor.getColumnIndex("Image"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
                val price = cursor.getInt(cursor.getColumnIndex("Price"))
                listMatch.add(Match(id,homeTeam,awayTeam,location,image,date,no_of_tickets,price))
            } while (cursor.moveToNext())
        }
        dbManager.close()
        return listMatch
    }

    fun fetchData(context: Context, result: (ArrayList<Match>) -> Unit)
    {
        scopeIO.launch {
            var listMatch = setDataItem(context)
            receiveMatchesFromServer(context,listMatch)
            listMatch = setDataItem(context)
            _matchList.postValue(listMatch)
            scopeMain.launch {
                result.invoke(listMatch)
            }
        }
    }

    fun fetchReservationsFromNetwork(context: Context){
        scopeIO.launch {
            Log.e("asd","okkk")
            var listReservation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getReservationsFromDB(context)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            createReservationsWithPendingStatus(context,listReservation)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getReservationsFromDB(context: Context) : ArrayList<Reservation>{
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

    private fun createReservationsWithPendingStatus(context: Context, reservationList:ArrayList<Reservation>){

        for (reservation:Reservation in reservationList)
        {
            if(reservation.status.equals("PENDING"))
            {
                var ok=false
                val client = OkHttpClient()
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("idReservation", 0)
                    jsonObject.put("idUser", reservation.idUser)
                    jsonObject.put("idMatch", reservation.idMatch)
                    jsonObject.put("no_of_tickets", reservation.no_of_tickets)
                    jsonObject.put("reservation_date", reservation.reservation_date)
                    jsonObject.put("total_price", reservation.total_price)
                    jsonObject.put("status","PENDING")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                val JSON = MediaType.parse("application/json; charset=utf-8")
                val body = RequestBody.create(JSON, jsonObject.toString())
                val request: Request = Request.Builder()
                    .url("ws://192.168.100.20:8080/reservation/create")
                    .post(body)
                    .build()

                client.newCall(request).enqueue(object : Callback {

                    override fun onFailure(call: Call?, e: IOException) {
                        e.printStackTrace()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call?, response: Response) {
                        ok = true
                        val dbManager = ReservationDBManager(context)
                        val selectionArgs = arrayOf(reservation.idReservation.toString())
                        dbManager.delete("Id=?", selectionArgs)
                    }
                })
            }
        }
    }

    private suspend fun receiveMatchesFromServer(context: Context,listMatch: ArrayList<Match>) {

        val url = "ws://192.168.100.20:8080/match"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call?, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val listOfMatchesFromServer = gson.fromJson(body,Array<Match>::class.java)
                var ok:Boolean = false
                val dbManager = MatchDBManager(context)
                for(matchFromServer: Match in listOfMatchesFromServer){
                    for (match: Match in listMatch)
                    {
                        if(matchFromServer.home_team.equals(match.home_team) &&(matchFromServer.away_team.equals(match.away_team))){
                            if(matchFromServer.no_of_tickets!=match.no_of_tickets)
                            {
                                val valuesMatch = ContentValues()
                                valuesMatch.put("No_of_tickets", matchFromServer.no_of_tickets)
                                val selectionArgs = arrayOf(match.id.toString())
                                dbManager.update(valuesMatch,"Id=?", selectionArgs)

                            }
                            ok=true
                        }

                    }
                    if(ok==false){
                        val values = ContentValues()
                        values.put("HomeTeam", matchFromServer.home_team)
                        values.put("AwayTeam",matchFromServer.away_team)
                        values.put("Location", matchFromServer.location)
                        values.put("Image", matchFromServer.image)
                        values.put("Date", matchFromServer.matchDate)
                        values.put("No_of_tickets",matchFromServer.no_of_tickets)
                        values.put("Price",matchFromServer.price)
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