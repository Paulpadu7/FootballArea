package com.example.footballarea

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.ReservationDBManager
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UpdateReservation : Fragment() {
    private var mcontext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_reservation, container, false)
        val args = this.arguments
        val matchId = args?.getInt("matchId")
        val matchDBManager = MatchDBManager(mcontext!!)
        val cursor1 = matchDBManager.queryOne(matchId!!)
        var match: Match? = null

        if (cursor1.moveToFirst()) {
            val id = cursor1.getInt(cursor1.getColumnIndex("Id"))
            val homeTeam = cursor1.getString(cursor1.getColumnIndex("HomeTeam"))
            val awayTeam = cursor1.getString(cursor1.getColumnIndex("AwayTeam"))
            val location = cursor1.getString(cursor1.getColumnIndex("Location"))
            val image = cursor1.getString(cursor1.getColumnIndex("Image"))
            val date = cursor1.getString(cursor1.getColumnIndex("Date"))
            val no_of_tickets = cursor1.getInt(cursor1.getColumnIndex("No_of_tickets"))
            val price = cursor1.getInt(cursor1.getColumnIndex("Price"))
            match = Match(id, homeTeam, awayTeam, location, image, date, no_of_tickets, price)
        }
        matchDBManager.close()
        val reservationId = args?.getInt("reservationId")

        var reservation: Reservation? = null
        val reservationDBManager = ReservationDBManager(mcontext!!)
        val cursor = reservationDBManager.queryOne(reservationId!!)
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("Id"))
            val userId = cursor.getInt(cursor.getColumnIndex("UserId"))
            val matchId = cursor.getInt(cursor.getColumnIndex("MatchId"))
            val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
            val reservationDate = cursor.getString(cursor.getColumnIndex("ReservationDate"))
            val price = cursor.getInt(cursor.getColumnIndex("Price"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val status = cursor.getString(cursor.getColumnIndex("Status"))
            val dateTime: LocalDateTime = LocalDateTime.parse(reservationDate)

            reservation = Reservation(id, userId, matchId, no_of_tickets, dateTime, price,status)
        }
        reservationDBManager.close()

        val team1: TextView = view.findViewById(R.id.team1Update)
        team1.text = match?.home_team
        val team2: TextView = view.findViewById(R.id.team2Update)
        team2.text = match?.away_team
        val location: TextView = view.findViewById(R.id.locationUpdate)
        location.text = match?.location
        val date: TextView = view.findViewById(R.id.dateUpdate)
        date.text = match?.matchDate
        var numberPicker: NumberPicker = view.findViewById(R.id.numberPickerUpdate)
        var price: TextView = view.findViewById(R.id.priceUpdate)
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroupUpdate)
        numberPicker.minValue = reservation?.no_of_tickets!!
        price.text = reservation.total_price.toString()
        var p: Int = 1
        var no_of_tickets: Int = reservation.no_of_tickets!!
        var sum: Int = 0

        Log.e("aa", match.toString())

        if (reservation.total_price!! / (reservation.no_of_tickets!! * match!!.price) == 2) {
            radioGroup.check(R.id.radio_long_sideUpdate)
            p = 2
        } else if (reservation.total_price!! / (reservation.no_of_tickets!! * match!!.price) == 5) {
            radioGroup.check(R.id.radio_vipUpdate)
            p = 5
        } else {
            radioGroup.check(R.id.radio_short_sideUpdate)
            p = 1
        }

        numberPicker = view.findViewById(R.id.numberPickerUpdate)
        numberPicker.minValue = 0
        numberPicker.maxValue = match.no_of_tickets + reservation.no_of_tickets!!
        price = view.findViewById(R.id.priceUpdate)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_long_sideUpdate)
                p = 2
            else if (checkedId == R.id.radio_vipUpdate)
                p = 5
            else
                p = 1
            sum = no_of_tickets * p * match.price
            price.text = (sum).toString()
        }
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            no_of_tickets = newVal
            sum = no_of_tickets * p * match.price
            price.text = (sum).toString()
        }


        val updateButton: Button
        updateButton = view.findViewById(R.id.updateButtonUpdate)
        updateButton.setOnClickListener {

            var ok=false
            val client = OkHttpClient()
            val jsonObject = JSONObject()
            try {
                jsonObject.put("idReservation", reservation.idReservation)
                jsonObject.put("idUser", MainActivity.Companion.user?.id)
                jsonObject.put("idMatch", match.id)
                jsonObject.put("no_of_tickets", no_of_tickets)
                val date = LocalDateTime.now()
                jsonObject.put("reservation_date", date)
                jsonObject.put("total_price", sum)
                jsonObject.put("status","PENDING")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val JSON = MediaType.parse("application/json; charset=utf-8")
            val body = RequestBody.create(JSON, jsonObject.toString())
            val request: Request = Request.Builder()
                .url("ws://192.168.100.20:8080/reservation/update")
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call?, e: IOException) {
                    e.printStackTrace()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call?, response: Response) {
                    ok = true
                }
            })
            Thread.sleep(200)
            if(ok==true)
                view?.findNavController()?.navigate(
                    R.id.action_updateReservation_to_myReservations
                )
            else
            {
                AlertDialog.Builder(context).setTitle("You are not connected to server!!").show()
            }

        }
        return view
    }
}