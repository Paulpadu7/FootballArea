package com.example.footballarea.Adapters

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.ReservationDBManager
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.MainActivity
import com.example.footballarea.R
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime

class MyMatchesAdapter (var context: Context, var arrayList: ArrayList<Reservation>) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(context, R.layout.card_view_my_reservations, null)
        val image: AppCompatImageView = view.findViewById(R.id.my_match_image)
        val matchName: TextView = view.findViewById(R.id.my_matchName)
        val matchDate: TextView = view.findViewById(R.id.my_matchDate)
        val matchLocation : TextView = view.findViewById(R.id.my_match_location)
        val macthDetails: TextView = view.findViewById(R.id.my_matchDetails)
        val status:TextView = view.findViewById(R.id.status)



        val matchId = arrayList.get(p0).idMatch
        val dbManager = MatchDBManager(context)
        val cursor = dbManager.queryOne(matchId!!)

        var match : Match? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("Id"))
            val homeTeam = cursor.getString(cursor.getColumnIndex("HomeTeam"))
            val awayTeam = cursor.getString(cursor.getColumnIndex("AwayTeam"))
            val location = cursor.getString(cursor.getColumnIndex("Location"))
            val image = cursor.getString(cursor.getColumnIndex("Image"))
            val date = cursor.getString(cursor.getColumnIndex("Date"))
            val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
            val price = cursor.getInt(cursor.getColumnIndex("Price"))
            match = Match(id,homeTeam,awayTeam,location,image,date,no_of_tickets,price)
        }
        dbManager.close()

        val reservation : Reservation = arrayList.get(p0)
        Picasso.with(context).load(match?.image!!).into(image)
        matchName.text = (match.home_team + " vs " +match.away_team)
        matchDate.text = match.matchDate
        matchLocation.text = match.location
        macthDetails.text = (reservation.no_of_tickets.toString() + " tickets " + "total price(ron): " + reservation.total_price)
        status.text = reservation.status

        val buttonUpdate : Button = view.findViewById(R.id.update_button)
        buttonUpdate.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("reservationId", reservation.idReservation!!)
            bundle.putInt("matchId", match.id)

            view?.findNavController()?.navigate(
                R.id.myReservations_to_updateReservation,bundle)
        }
        val buttonDelete : Button = view.findViewById(R.id.delete_button)
        buttonDelete.setOnClickListener {
        var ok =false
            val url = "ws://192.168.100.20:8080/reservation/delete/" + reservation.idReservation.toString()
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException) {
                    ok = false
                    e.printStackTrace()
                }
                override fun onResponse(call: Call?, response: Response) {
                    ok=true

                }
            })
            Thread.sleep(150)
            if(ok==true)
            {
                val dbManager = ReservationDBManager(context)
                val selectionArgs = arrayOf(reservation.idReservation.toString())
                dbManager.delete("Id=?", selectionArgs)
                arrayList.removeAt(p0)
                notifyDataSetChanged()
            }
            else{
                AlertDialog.Builder(context).setTitle("You are not connected to server!").show()
            }

        }

        return view!!
    }
}