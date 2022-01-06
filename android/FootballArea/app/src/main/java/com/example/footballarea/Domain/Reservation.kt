package com.example.footballarea.Domain

import java.time.LocalDateTime

class Reservation()
{
    var idReservation:Int ?=null
    var idUser:Int ?=null
    var idMatch: Int?=null
    var no_of_tickets:Int ?=null
    var reservation_date:LocalDateTime ?=null
    var total_price:Int ?=null
    var status:String ?="PENDING"


    constructor(idReservation: Int?, idUser: Int?, idMatch: Int?, no_of_tickets: Int?, reservation_date: LocalDateTime?, total_price: Int?, status: String?)
            : this() {
        this.idReservation = idReservation
        this.idUser = idUser
        this.idMatch = idMatch
        this.no_of_tickets = no_of_tickets
        this.reservation_date = reservation_date
        this.total_price = total_price
        this.status = status
    }

    override fun toString(): String {
        return "Reservation(idReservation=$idReservation, idUser=$idUser, match=$idMatch, no_of_tickets=$no_of_tickets, reservation_date=$reservation_date, total_price=$total_price)"
    }

}