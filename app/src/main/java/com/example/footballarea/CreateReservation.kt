package com.example.footballarea

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.footballarea.Domain.Match
import com.example.footballarea.Repository.ReservationRepository
import java.time.LocalDateTime

class CreateReservation : Fragment() {
    private var mcontext: Context? =null
    var reservationRepository : ReservationRepository ?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
        reservationRepository = ReservationRepository(mcontext!!)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_create_reservation, container, false)
        var team1 : TextView
        var team2 : TextView
        var date : TextView
        var location : TextView
        var numberPicker : NumberPicker
        var price : TextView
        var createButton : Button
        val args = this.arguments
        var match: Match = args?.get("match data") as Match
        //var match: Match = Match(2,"CFR Cluj","FC Rapid","Stadionul Dr. Constantin Rădulescu",R.drawable.cfr_vs_rapid,"10.10.2021",15000,10)
        team1 = view.findViewById(R.id.team1)
        team1.text = match.home_team
        team2 = view.findViewById(R.id.team2)
        team2.text = match.away_team
        date = view.findViewById(R.id.date)
        date.text = match.date
        location = view.findViewById(R.id.location)
        location.text = match.location
        numberPicker = view.findViewById(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 10
        price = view.findViewById(R.id.price)
        var radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        var p:Int = 1
        var x:Int = 0
        var sum:Int =0
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if( checkedId == R.id.radio_long_side)
                p=2
            else
                if(checkedId == R.id.radio_vip)
                p=5
            else
                p=1
            sum = x*p*match.price
            price.text = (sum).toString()
        }
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            x = newVal
            sum = x*p*match.price
            price.text = (sum).toString()
        }
        createButton = view.findViewById(R.id.createButton)
        createButton.setOnClickListener{
            reservationRepository?.add(1,1,match,x,LocalDateTime.now().toString(),sum)

            val bundle = Bundle()
            bundle.putParcelable("key",reservationRepository)
            val fragment = MyReservations()
            fragment.arguments = bundle
            view?.findNavController()?.navigate(
                R.id.createReservation_to_myReservations,
                bundle)
            //fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
        return view
    }

}