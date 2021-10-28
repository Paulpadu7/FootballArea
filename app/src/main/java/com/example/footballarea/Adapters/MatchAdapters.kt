package com.example.footballarea.Adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.footballarea.CreateReservation
import com.example.footballarea.Domain.Match
import com.example.footballarea.R
import com.example.footballarea.Repository.ReservationRepository

class MatchAdapters(var context: Context, var arrayList: ArrayList<Match>, var fragmentManager: FragmentManager) : BaseAdapter() {
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
        val view = View.inflate(context, R.layout.card_view_item_layout_list, null)
        var image:AppCompatImageView = view.findViewById(R.id.match_image)
        var matchName:TextView = view.findViewById(R.id.matchName)
        var matchDate:TextView = view.findViewById(R.id.matchDate)
        var matchLocation : TextView = view.findViewById(R.id.match_location)
        var macthPrice:TextView = view.findViewById(R.id.matchPrice)

        var match : Match = arrayList.get(p0)
        image.setImageResource(match.image)
        matchName.text = (match.home_team + " vs " +match.away_team)
        matchDate.text = match.date
        matchLocation.text = match.location
        macthPrice.text = ("The price starts at " + match.price.toString() + " lei")
        var book_button : Button = view.findViewById(R.id.book_button)
        book_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelable("match data",match)
//            val fragment = CreateReservation()
//            fragment.arguments = bundle
            view?.findNavController()?.navigate(
                R.id.action_mainPage_to_createReservation,
                bundle)

            //Navigation.findNavController(view).navigate(R.id.action_mainPage_to_createReservation)
        }
        return view!!
    }

}