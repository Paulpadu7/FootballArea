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
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.footballarea.Adapters.MyMatchesAdapter
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.Repository.ReservationRepository

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
        val args = this.arguments
        var reservationRepository: ReservationRepository = args?.get("key") as ReservationRepository
        listView = view.findViewById(R.id.my_matches_list_view)
        arrayList = ArrayList()
        arrayList = setDataItem(reservationRepository)
        myMatchAdapters = MyMatchesAdapter(mcontext!!, arrayList!!,requireFragmentManager(),reservationRepository)
        listView?.adapter = myMatchAdapters
        var buttonMainPage:Button = view.findViewById(R.id.buttonMainPage)
        buttonMainPage.setOnClickListener {
            val fragment = MainPage()
            view?.findNavController()?.navigate(
                R.id.action_myReservations_to_mainPage)
        }
        return view
    }
    private fun setDataItem(reservationRepository: ReservationRepository):ArrayList<Reservation>{
        var listReservations:ArrayList<Reservation> = ArrayList()
        for(reservation in reservationRepository.getAllReservation())
            listReservations?.add(reservation)
        return listReservations!!
    }
}