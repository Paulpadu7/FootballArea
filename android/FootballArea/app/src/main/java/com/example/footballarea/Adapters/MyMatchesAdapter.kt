package com.example.footballarea.Adapters

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.footballarea.CreateReservation
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.ReservationDBManager
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.R
import com.example.footballarea.UpdateReservation

class MyMatchesAdapter (var context: Context, var arrayList: ArrayList<Reservation>, var fragmentManager: FragmentManager) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(context, R.layout.card_view_my_reservations, null)
        var image: AppCompatImageView = view.findViewById(R.id.my_match_image)
        var matchName: TextView = view.findViewById(R.id.my_matchName)
        var matchDate: TextView = view.findViewById(R.id.my_matchDate)
        var matchLocation : TextView = view.findViewById(R.id.my_match_location)
        var macthDetails: TextView = view.findViewById(R.id.my_matchDetails)



        var matchId = arrayList.get(p0).idMatch
        val dbManager = MatchDBManager(context)
        val cursor = dbManager.queryOne(matchId!!)

        var match : Match? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("Id"))
            val homeTeam = cursor.getString(cursor.getColumnIndex("HomeTeam"))
            val awayTeam = cursor.getString(cursor.getColumnIndex("AwayTeam"))
            val location = cursor.getString(cursor.getColumnIndex("Location"))
            val image = cursor.getInt(cursor.getColumnIndex("Image"))
            val date = cursor.getString(cursor.getColumnIndex("Date"))
            val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
            val price = cursor.getInt(cursor.getColumnIndex("Price"))
            match = Match(id,homeTeam,awayTeam,location,image,date,no_of_tickets,price)
        }
        dbManager.close()

        var reservation : Reservation = arrayList.get(p0)
        image.setImageResource(match?.image!!)
        matchName.text = (match.home_team + " vs " +match.away_team)
        matchDate.text = match.date
        matchLocation.text = match.location
        macthDetails.text = (reservation.no_of_tickets.toString() + " tickets " + "total price(ron): " + reservation.total_price)

        var buttonUpdate : Button = view.findViewById(R.id.update_button)
        buttonUpdate.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("reservationId", reservation.idReservation!!)
            bundle.putInt("matchId", match.id)
            view?.findNavController()?.navigate(
                R.id.myReservations_to_updateReservation,bundle)
        }
        var buttonDelete : Button = view.findViewById(R.id.delete_button)
        buttonDelete.setOnClickListener {
            arrayList.removeAt(p0)
            var dbManager = ReservationDBManager(context)
            val selectionArgs = arrayOf(reservation.idReservation.toString())
            dbManager.delete("Id=?", selectionArgs)

            val matchDBManager = MatchDBManager(context)
            val valuesMatch = ContentValues()
            valuesMatch.put("No_of_tickets", match.no_of_tickets + reservation.no_of_tickets!!)
            val selectionArgs2 = arrayOf(match.id.toString())
            matchDBManager.update(valuesMatch,"Id=?", selectionArgs2)

            notifyDataSetChanged()
        }

        return view!!
    }
}