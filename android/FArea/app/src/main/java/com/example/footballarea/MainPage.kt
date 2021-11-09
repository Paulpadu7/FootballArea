package com.example.footballarea

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.footballarea.Adapters.MatchAdapters
import com.example.footballarea.Domain.Match
import com.example.footballarea.Repository.ReservationRepository

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
        arrayList = setDataItem()
        matchAdapters = MatchAdapters(mcontext!!, arrayList!!, requireFragmentManager())
        listView?.adapter = matchAdapters
        return view
    }
    private fun setDataItem():ArrayList<Match>{
        var listMatch:ArrayList<Match> = ArrayList()
        listMatch.add(Match(1,"Dinamo","FCSB","Stadionul Dinamo",R.drawable.dinamo_vs_fcsb,"10.10.2021",10000,20))
        listMatch.add(Match(2,"Farul Constanta","CSU Craiova","Stadionul Farul",R.drawable.farul_vs_craiova,"10.10.2021",10000,10))
        listMatch.add(Match(3,"Gaz Metan","UTA","Stadionul Gaz Metan Medias",R.drawable.gaz_vs_uta,"10.10.2021",10000,20))
        listMatch.add(Match(4,"CFR Cluj","FC Rapid","Stadionul Dr. Constantin Rădulescu",R.drawable.cfr_vs_rapid,"10.10.2021",15000,10))
        return listMatch
    }

}