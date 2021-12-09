package com.example.footballarea

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.footballarea.Adapters.MyMatchesAdapter
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.ReservationDBManager
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation

class MyReservations : Fragment() {
    private var listView: ListView? =null
    private var myMatchAdapters: MyMatchesAdapter? =null
    private var arrayList:ArrayList<Reservation> ? =null
    private var mcontext: Context? =null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_reservations, container, false)
        listView = view.findViewById(R.id.my_matches_list_view)
        arrayList = setDataItem()
        Log.e("aa", arrayList!!.size.toString())
        myMatchAdapters = MyMatchesAdapter(mcontext!!, arrayList!!,requireFragmentManager())
        listView?.adapter = myMatchAdapters
        var buttonMainPage:Button = view.findViewById(R.id.buttonMainPage)

        var textView:TextView = view.findViewById(R.id.textView)
        if(arrayList!!.isEmpty())
            textView.visibility = View.VISIBLE
        else
            textView.visibility = View.INVISIBLE



        buttonMainPage.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_myReservations_to_mainPage)
        }
        return view
    }
    private fun setDataItem():ArrayList<Reservation> {
        var listReservations: ArrayList<Reservation> = ArrayList()
        val dbManager = ReservationDBManager(mcontext!!)
        val cursor = dbManager.queryAll()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val userId = cursor.getInt(cursor.getColumnIndex("UserId"))
                val matchId = cursor.getInt(cursor.getColumnIndex("MatchId"))
                val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
                val reservationDate = cursor.getString(cursor.getColumnIndex("ReservationDate"))
                val price = cursor.getInt(cursor.getColumnIndex("Price"))
                listReservations.add(Reservation(id,userId,matchId,no_of_tickets,reservationDate,price))
            } while (cursor.moveToNext())
        }
        dbManager.close()
        return listReservations
    }
}