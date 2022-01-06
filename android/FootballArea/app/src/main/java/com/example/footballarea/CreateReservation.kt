package com.example.footballarea

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
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
import com.example.footballarea.Domain.User
import com.example.footballarea.databinding.FragmentCreateReservationBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateReservation : Fragment() {
    private var _binding: FragmentCreateReservationBinding? =null
    private val binding: FragmentCreateReservationBinding
        get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // val reservationRepository = (requireActivity() as MainActivity).reservationRepository
        //val view = inflater.inflate(R.layout.fragment_create_reservation, container, false)
        _binding = FragmentCreateReservationBinding.inflate(inflater,container,false)
        val view = binding.root
        val args = this.arguments
        var matchId = args?.getInt("matchId")
        val matchDBManager = MatchDBManager(requireContext())
        val cursor1 = matchDBManager.queryOne(matchId!!)
        var match : Match? = null

        if (cursor1.moveToFirst()) {
            val id = cursor1.getInt(cursor1.getColumnIndex("Id"))
            val homeTeam = cursor1.getString(cursor1.getColumnIndex("HomeTeam"))
            val awayTeam = cursor1.getString(cursor1.getColumnIndex("AwayTeam"))
            val location = cursor1.getString(cursor1.getColumnIndex("Location"))
            val image = cursor1.getString(cursor1.getColumnIndex("Image"))
            val date = cursor1.getString(cursor1.getColumnIndex("Date"))
            val no_of_tickets = cursor1.getInt(cursor1.getColumnIndex("No_of_tickets"))
            val price = cursor1.getInt(cursor1.getColumnIndex("Price"))
            match = Match(id,homeTeam,awayTeam,location,image,date,no_of_tickets,price)
        }
        matchDBManager.close()
        //var match: Match = Match(2,"CFR Cluj","FC Rapid","Stadionul Dr. Constantin Rădulescu",R.drawable.cfr_vs_rapid,"10.10.2021",15000,10)

        binding.team1.text = match?.home_team
        binding.team2.text = match?.away_team
        binding.date.text = match?.matchDate
        binding.location.text = match?.location
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = match!!.no_of_tickets
        var radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        var p:Int = 1
        var no_of_tickets:Int = 0
        var sum:Int = 0
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if( checkedId == R.id.radio_long_side)
                p=2
            else
                if(checkedId == R.id.radio_vip)
                p=5
            else
                p=1
            sum = no_of_tickets*p* match!!.price
            binding.price.text = (sum).toString()
        }
        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            no_of_tickets = newVal
            sum = no_of_tickets*p* match!!.price
            binding.price.text = (sum).toString()
        }
        binding.createButton.setOnClickListener{

            var ok=false
            val client = OkHttpClient()
            val jsonObject = JSONObject()
            try {
                jsonObject.put("idReservation", 0)
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
                }
            })
            Thread.sleep(200)
            if(ok==true){

                view.findNavController().navigate(
                    R.id.createReservation_to_myReservations)
            }

            else
            {
                val dbManager = ReservationDBManager(requireContext())
                val values = ContentValues()
                values.put("Id",0)
                values.put("UserId",MainActivity.Companion.user?.id)
                values.put("MatchId",match.id)
                values.put("No_of_tickets",no_of_tickets)
                val date = LocalDateTime.now()
                values.put("ReservationDate",date.toString())
                values.put("Price",sum)
                values.put("Status","PENDING")
                dbManager.insert(values)
                AlertDialog.Builder(context).setTitle("You are not connected to server!").show()
                view.findNavController().navigate(
                    R.id.createReservation_to_myReservations)
            }

//            val reservationDBManager = ReservationDBManager(requireContext())
//            val values = ContentValues()
//            values.put("UserId", 0)
//            values.put("MatchId", match!!.id)
//            values.put("No_of_tickets", no_of_tickets)
//            values.put("ReservationDate", LocalDateTime.now().toString())
//            values.put("Price", sum)
//            val mID = reservationDBManager.insert(values)
//            reservationDBManager.close()

//            val matchDBManager = MatchDBManager(requireContext())
//            val valuesMatch = ContentValues()
//            valuesMatch.put("No_of_tickets", match.no_of_tickets - no_of_tickets)
//            val selectionArgs = arrayOf(match.id.toString())
//            matchDBManager.update(valuesMatch,"Id=?", selectionArgs)


        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}