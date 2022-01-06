package com.example.footballarea

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.footballarea.Adapters.MyMatchesAdapter
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.DBRepo.MyReservationsViewModel
import com.example.footballarea.DBRepo.ReservationDBManager
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.Reservation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyReservations : Fragment() {
    private var listView: ListView? =null
    private var myMatchAdapters: MyMatchesAdapter? =null
    private val viewModel: MyReservationsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_reservations, container, false)
        listView = view.findViewById(R.id.my_matches_list_view)

        viewModel.fetchData(requireContext()){
            myMatchAdapters = MyMatchesAdapter(requireContext(), it)
            listView?.adapter = myMatchAdapters
            val textView:TextView = view.findViewById(R.id.textView)
            if(it.isEmpty())
                textView.visibility = View.VISIBLE
            else
                textView.visibility = View.INVISIBLE
        }

        val buttonMainPage:Button = view.findViewById(R.id.buttonMainPage)
        buttonMainPage.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_myReservations_to_mainPage)
        }
        return view
    }

}