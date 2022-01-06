package com.example.footballarea

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.footballarea.Adapters.MatchAdapters
import com.example.footballarea.DBRepo.MainPageViewModel
import com.example.footballarea.DBRepo.MatchDBManager
import com.example.footballarea.Domain.Match
import com.squareup.picasso.Picasso

class MainPage : Fragment() {
    private var listView: ListView? = null
    private var matchAdapters: MatchAdapters? = null
    private val viewModel: MainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)
        listView = view.findViewById(R.id.matches_list_view)

        viewModel.fetchData(requireContext()) {
            matchAdapters = MatchAdapters(requireContext(), it)
            listView?.adapter = matchAdapters
        }

        viewModel.fetchReservationsFromNetwork(requireContext())

        val myReservationButton: Button = view.findViewById(R.id.myReservationsButton)
        myReservationButton.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_mainPage_to_myReservations
            )
        }
        return view
    }

}