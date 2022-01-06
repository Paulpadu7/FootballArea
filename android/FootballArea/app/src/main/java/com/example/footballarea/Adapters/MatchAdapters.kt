package com.example.footballarea.Adapters

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.footballarea.Domain.Match
import com.example.footballarea.R
import com.squareup.picasso.Picasso


class MatchAdapters(var context: Context, var arrayList: ArrayList<Match>) : BaseAdapter() {
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
        val image:AppCompatImageView = view.findViewById(R.id.match_image)
        val matchName:TextView = view.findViewById(R.id.matchName)
        val matchDate:TextView = view.findViewById(R.id.matchDate)
        val matchLocation : TextView = view.findViewById(R.id.match_location)
        val macthPrice:TextView = view.findViewById(R.id.matchPrice)
        val no_of_tickets:TextView = view.findViewById(R.id.no_of_tickets)
        val book_button : Button = view.findViewById(R.id.book_button)

        val match : Match = arrayList.get(p0)

        Picasso.with(context).load(match.image!!).into(image)
        matchName.text = (match.home_team + " vs " +match.away_team)
        matchDate.text = match.matchDate
        matchLocation.text = match.location
        macthPrice.text = ("The price starts at " + match.price.toString() + " lei")
        if(match.no_of_tickets > 0)
        {
            no_of_tickets.text = match.no_of_tickets.toString() + " tickets!"
        }
        else
        {
            no_of_tickets.text = "Sold out!"
            no_of_tickets.setTextColor(Color.RED)
            book_button.isEnabled = false
        }



        book_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("matchId",match.id)
            view?.findNavController()?.navigate(
                R.id.action_mainPage_to_createReservation,
                bundle)
        }
        return view!!
    }

}