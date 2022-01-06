package com.example.footballarea.Domain


class Match()  {
    var id:Int = 0
    var home_team:String ? = null
    var away_team:String ? = null
    var location:String ? = null
    var image:String? = null
    var matchDate:String ? = null
    var no_of_tickets:Int = 0
    var price:Int =0


    constructor(id: Int, home_team: String?, away_team: String?, location: String? ,image: String?, date: String?, no_of_tickets: Int, price: Int) : this() {
        this.id = id
        this.home_team = home_team
        this.away_team = away_team
        this.location = location
        this.image = image
        this.matchDate = date
        this.no_of_tickets = no_of_tickets
        this.price = price
    }

}