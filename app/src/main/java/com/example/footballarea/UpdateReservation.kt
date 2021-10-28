package com.example.footballarea

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.footballarea.Domain.Reservation
import com.example.footballarea.Repository.ReservationRepository

class UpdateReservation : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_reservation, container, false)
        var args = this.arguments
        var reservationRepository: ReservationRepository = args?.get("key") as ReservationRepository
        var position:Int = args?.get("pos") as Int
        var reservation:Reservation = reservationRepository.getReservation(position)!!
        var team1: TextView = view.findViewById(R.id.team1Update)
        team1.text = reservation.match?.home_team
        var team2: TextView = view.findViewById(R.id.team2Update)
        team2.text = reservation.match?.away_team
        var location : TextView = view.findViewById(R.id.locationUpdate)
        location.text = reservation.match?.location
        var date : TextView = view.findViewById(R.id.dateUpdate)
        date.text = reservation.match?.date
        var numberPicker : NumberPicker = view.findViewById(R.id.numberPickerUpdate)
        var price : TextView = view.findViewById(R.id.priceUpdate)
        var radioGroup: RadioGroup = view.findViewById(R.id.radioGroupUpdate)
        numberPicker.minValue = reservation.no_of_tickets!!
        price.text = reservation.total_price.toString()
        var p:Int = 1
        var x:Int = reservation.no_of_tickets!!
        var sum:Int =0
        if(reservation.total_price!! / (reservation.no_of_tickets!! * reservation.match!!.price) == 2)
        {
            radioGroup.check(R.id.radio_long_sideUpdate)
            p=2
        }
        else
            if (reservation.total_price!! / (reservation.no_of_tickets!! * reservation.match!!.price) == 5)
            {
                radioGroup.check(R.id.radio_vipUpdate)
                p=5
            }
        else{
                radioGroup.check(R.id.radio_short_sideUpdate)
                p=1
            }


        numberPicker = view.findViewById(R.id.numberPickerUpdate)
        numberPicker.minValue = 0
        numberPicker.maxValue = 10
        price = view.findViewById(R.id.priceUpdate)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if( checkedId == R.id.radio_long_sideUpdate)
                p=2
            else
                if(checkedId == R.id.radio_vipUpdate)
                    p=5
                else
                    p=1
            sum = x*p*reservation.match!!.price
            price.text = (sum).toString()
        }
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            x = newVal
            sum = x*p*reservation.match!!.price
            price.text = (sum).toString()
        }


        var updateButton : Button
        updateButton = view.findViewById(R.id.updateButtonUpdate)
        updateButton.setOnClickListener{
            reservationRepository.update(position,x,sum)
            Log.e("sds", reservationRepository.getReservation(position)!!.total_price.toString())
            var fragment = MyReservations()
            var bundle = Bundle()
            bundle.putParcelable("key",reservationRepository)
            view?.findNavController()?.navigate(
                R.id.action_updateReservation_to_myReservations,
                bundle)
            //fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
        return view
    }
}