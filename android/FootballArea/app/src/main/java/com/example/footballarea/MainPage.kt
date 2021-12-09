package com.example.footballarea

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.navigation.findNavController
import com.example.footballarea.Adapters.MatchAdapters
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.ReservationContract
import com.example.footballarea.Domain.Match

class MainPage : Fragment() {
    private var listView:ListView ? =null
    private var matchAdapters: MatchAdapters? =null
    private var arrayList:ArrayList<Match> ? =null
    private var mcontext: Context ? =null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)
        listView = view.findViewById(R.id.matches_list_view)
        arrayList = ArrayList()

//        val dbManager = MatchDBManager(mcontext!!)
//        val values = ContentValues()
//        values.put("HomeTeam", "CFR Cluj")
//        values.put("AwayTeam", "FC Rapid")
//        values.put("Location", "Stadionul DR. Constantin Radulescu")
//        values.put("Image", R.drawable.cfr_vs_rapid)
//        values.put("Date", "10.10.2021")
//        values.put("No_of_tickets", 10)
//        values.put("Price", 20)
//        val mID = dbManager.insert(values)
//        if (mID > 0) {
//            Log.e("aa","Add note successfully!")
//        } else {
//            Log.e("aa","Fail to add note!")
//        }
//        values.put("HomeTeam", "Farul Constanta")
//        values.put("AwayTeam", "CSU Craiova")
//        values.put("Location", "Stadionul Farul")
//        values.put("Image", R.drawable.farul_vs_craiova)
//        values.put("Date", "10.10.2021")
//        values.put("No_of_tickets", 1000)
//        values.put("Price", 15)
//        val mID2 = dbManager.insert(values)
//        if (mID2 > 0) {
//            Log.e("aa","Add note successfully!")
//        } else {
//            Log.e("aa","Fail to add note!")
//        }

        arrayList = setDataItem()
        matchAdapters = MatchAdapters(mcontext!!, arrayList!!, requireFragmentManager())
        listView?.adapter = matchAdapters

        var myReservationButton:Button = view.findViewById(R.id.myReservationsButton)
        myReservationButton.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_mainPage_to_myReservations)
        }
        return view
    }
    private fun setDataItem():ArrayList<Match>{
        val dbManager = MatchDBManager(mcontext!!);
        var listMatch:ArrayList<Match> = ArrayList()
        val cursor = dbManager.queryAll()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val homeTeam = cursor.getString(cursor.getColumnIndex("HomeTeam"))
                val awayTeam = cursor.getString(cursor.getColumnIndex("AwayTeam"))
                val location = cursor.getString(cursor.getColumnIndex("Location"))
                val image = cursor.getInt(cursor.getColumnIndex("Image"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                val no_of_tickets = cursor.getInt(cursor.getColumnIndex("No_of_tickets"))
                val price = cursor.getInt(cursor.getColumnIndex("Price"))
                listMatch.add(Match(id,homeTeam,awayTeam,location,image,date,no_of_tickets,price))
            } while (cursor.moveToNext())
        }
        dbManager.close()

        return listMatch
    }

}