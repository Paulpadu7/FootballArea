package com.example.footballarea

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.ReservationDBManager
import com.example.footballarea.Domain.Match
import java.time.LocalDateTime

class CreateReservation : Fragment() {
    private var mcontext: Context? =null
    //var reservationRepository : ReservationRepository ?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
       // reservationRepository = ReservationRepository(mcontext!!)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // val reservationRepository = (requireActivity() as MainActivity).reservationRepository
        val view = inflater.inflate(R.layout.fragment_create_reservation, container, false)
        var team1 : TextView
        var team2 : TextView
        var date : TextView
        var location : TextView
        var numberPicker : NumberPicker
        var price : TextView
        var createButton : Button
        val args = this.arguments
        var matchId = args?.getInt("matchId")

        Log.e("fsdf", matchId.toString())

        val matchDBManager = MatchDBManager(mcontext!!)
        val cursor1 = matchDBManager.queryOne(matchId!!)
        var match : Match? = null

        if (cursor1.moveToFirst()) {
            Log.e("aa", "okkk")
            val id = cursor1.getInt(cursor1.getColumnIndex("Id"))
            val homeTeam = cursor1.getString(cursor1.getColumnIndex("HomeTeam"))
            val awayTeam = cursor1.getString(cursor1.getColumnIndex("AwayTeam"))
            val location = cursor1.getString(cursor1.getColumnIndex("Location"))
            val image = cursor1.getInt(cursor1.getColumnIndex("Image"))
            val date = cursor1.getString(cursor1.getColumnIndex("Date"))
            val no_of_tickets = cursor1.getInt(cursor1.getColumnIndex("No_of_tickets"))
            val price = cursor1.getInt(cursor1.getColumnIndex("Price"))
            match = Match(id,homeTeam,awayTeam,location,image,date,no_of_tickets,price)
        }
        matchDBManager.close()
        //var match: Match = Match(2,"CFR Cluj","FC Rapid","Stadionul Dr. Constantin Rădulescu",R.drawable.cfr_vs_rapid,"10.10.2021",15000,10)
        team1 = view.findViewById(R.id.team1)
        team1.text = match?.home_team
        team2 = view.findViewById(R.id.team2)
        team2.text = match?.away_team
        date = view.findViewById(R.id.date)
        date.text = match?.date
        location = view.findViewById(R.id.location)
        location.text = match?.location
        numberPicker = view.findViewById(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = match!!.no_of_tickets
        price = view.findViewById(R.id.price)
        var radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        var p:Int = 1
        var no_of_tickets:Int = 0
        var sum:Int =0
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if( checkedId == R.id.radio_long_side)
                p=2
            else
                if(checkedId == R.id.radio_vip)
                p=5
            else
                p=1
            sum = no_of_tickets*p* match!!.price
            price.text = (sum).toString()
        }
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            no_of_tickets = newVal
            sum = no_of_tickets*p* match!!.price
            price.text = (sum).toString()
        }
        createButton = view.findViewById(R.id.createButton)
        createButton.setOnClickListener{

            val reservationDBManager = ReservationDBManager(mcontext!!)
            val values = ContentValues()
            values.put("UserId", 0)
            values.put("MatchId", match!!.id)
            values.put("No_of_tickets", no_of_tickets)
            values.put("ReservationDate", LocalDateTime.now().toString())
            values.put("Price", sum)
            val mID = reservationDBManager.insert(values)
            reservationDBManager.close()

            val matchDBManager = MatchDBManager(mcontext!!)
            val valuesMatch = ContentValues()
            valuesMatch.put("No_of_tickets", match.no_of_tickets - no_of_tickets)
            val selectionArgs = arrayOf(match.id.toString())
            matchDBManager.update(valuesMatch,"Id=?", selectionArgs)

            view?.findNavController()?.navigate(
                R.id.createReservation_to_myReservations)
        }
        return view
    }

}