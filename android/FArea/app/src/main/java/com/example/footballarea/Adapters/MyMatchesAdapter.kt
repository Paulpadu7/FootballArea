package com.example.footballarea.Adapters

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
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.R
import com.example.footballarea.Repository.ReservationRepository
import com.example.footballarea.UpdateReservation

class MyMatchesAdapter (var context: Context, var arrayList: ArrayList<Reservation>, var fragmentManager: FragmentManager, var reservationRepository: ReservationRepository) : BaseAdapter() {
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

        var match : Match = arrayList.get(p0).match!!
        var reservation : Reservation = arrayList.get(p0)
        image.setImageResource(match.image)
        matchName.text = (match.home_team + " vs " +match.away_team)
        matchDate.text = match.date
        matchLocation.text = match.location
        macthDetails.text = (reservation.no_of_tickets.toString() + " tickets " + "total price(ron): " + reservation.total_price)

        var buttonUpdate : Button = view.findViewById(R.id.update_button)
        buttonUpdate.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelable("key",reservationRepository)
            bundle.putInt("pos", reservation.idReservation!!)
            val fragment = UpdateReservation()
            fragment.arguments = bundle
            //fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
            view?.findNavController()?.navigate(
                R.id.myReservations_to_updateReservation,
                bundle)
        }
        var buttonDelete : Button = view.findViewById(R.id.delete_button)
        buttonDelete.setOnClickListener {
            arrayList.removeAt(p0)
            notifyDataSetChanged()
        }

        return view!!
    }
}