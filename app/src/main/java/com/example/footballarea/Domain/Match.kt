package com.example.footballarea.Domain

import android.os.Parcel
import android.os.Parcelable


class Match() : Parcelable {
    var id:Int = 0
    var home_team:String ? = null
    var away_team:String ? = null
    var location:String ? = null
    var image:Int = 0
    var date:String ? = null
    var no_of_tickets:Int = 0
    var price:Int =0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        home_team = parcel.readString()
        away_team = parcel.readString()
        location = parcel.readString()
        image = parcel.readInt()
        date = parcel.readString()
        no_of_tickets = parcel.readInt()
        price = parcel.readInt()
    }

    constructor(id: Int, home_team: String?, away_team: String?, location: String? ,image: Int, date: String?, no_of_tickets: Int, price: Int) : this() {
        this.id = id
        this.home_team = home_team
        this.away_team = away_team
        this.location = location
        this.image = image
        this.date = date
        this.no_of_tickets = no_of_tickets
        this.price = price
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(home_team)
        parcel.writeString(away_team)
        parcel.writeString(location)
        parcel.writeInt(image)
        parcel.writeString(date)
        parcel.writeInt(no_of_tickets)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Match> {
        override fun createFromParcel(parcel: Parcel): Match {
            return Match(parcel)
        }

        override fun newArray(size: Int): Array<Match?> {
            return arrayOfNulls(size)
        }
    }
}