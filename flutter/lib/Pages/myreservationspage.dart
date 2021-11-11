import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:football_area/Domain/match.dart';
import 'package:football_area/Domain/reservation.dart';
import 'package:football_area/Pages/updatereservationpage.dart';
import 'package:football_area/Repository/reservationrepository.dart';
import 'package:football_area/helpers/utils.dart';
import 'package:football_area/pages/matcheslistpage.dart';
import 'package:football_area/pages/myreservationspage.dart';

class MyReservationsPage extends StatefulWidget{
  ReservationRepositorty reservationRepositorty = new ReservationRepositorty();
  MyReservationsPage(ReservationRepositorty reservationRepositorty)
  {
    this.reservationRepositorty = reservationRepositorty;
  }
  @override
  MyReservationsPageState createState() => MyReservationsPageState(reservationRepositorty);

}

class MyReservationsPageState extends State<MyReservationsPage>{
  ReservationRepositorty reservationRepositorty = new ReservationRepositorty();
  MyReservationsPageState(ReservationRepositorty reservationRepositorty)
  {
    this.reservationRepositorty = reservationRepositorty;
  }
  @override
  Widget build(BuildContext context) {
    List<Reservation> reservationsList = reservationRepositorty.getAll();
    return Scaffold(
      drawer: Drawer(),
      appBar: new AppBar(actions: <Widget>[
        RaisedButton(
          child: Text("Mathches list", style: TextStyle(fontSize: 15)),
          onPressed: (){
            Navigator.push(context, MaterialPageRoute(builder: (context){
              return MatchesListPage(reservationRepositorty);
            }));
          },
        )
      ],
      ),
      body: Container(
        child:Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Padding(padding: const EdgeInsets.all(8.0),
              child: Text('My reservations',
                  textAlign:TextAlign.center ,
                  style: TextStyle(color: Colors.black,fontSize: 25)
              ),
            ),
            Expanded(
                child: ListView.builder(
                    itemCount: reservationsList.length,
                    itemBuilder: (BuildContext ctx, int index){
                      return Container(
                      margin: EdgeInsets.all(10),
                      height: 200,
                      child: Stack(
                          children: [
                            Positioned(
                              child: Container(
                                margin: EdgeInsets.only(
                                    top: 20
                                ),
                                decoration: BoxDecoration(
                                    image: DecorationImage(
                                        fit: BoxFit.fill,
                                        image: AssetImage("assets/" +
                                            reservationsList[index].match.image + ".png")
                                    )
                                ),
                              ),
                            ),
                            Positioned(width: 370,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Text(reservationsList[index].match.home_team + " vs " +
                                    reservationsList[index].match.away_team + "    ",
                                  style: TextStyle(fontSize: 18),),
                                MaterialButton(
                                  height: 30.0,
                                  color: Colors.white,
                                  minWidth: 50.0,
                                  child: new Text("UPDATE"),
                                  onPressed: () {
                                    Navigator.push(context, MaterialPageRoute(builder: (context)
                                    {
                                      return UpdateReservation(
                                          reservationRepositorty,
                                          reservationsList[index]);
                                    }
                                    )
                                    );
                                  }
                                ),
                                MaterialButton(
                                  height: 30.0,
                                  color: Colors.red,
                                  minWidth: 50.0,
                                  child: new Text("DELETE"),
                                  onPressed: () {
                                    setState(() {
                                      reservationsList.removeAt(index);
                                    });
                                    },
                                ),
                              ],
                            )
                            ),
                            Positioned(
                              top: 180,
                              left: 50,
                              child:Container(
                                  width:350,
                                  child: Text(
                                      reservationsList[index].no_of_tickets.toString() + " tickets, total price: " + reservationsList[index].total_price.toString() + " (ron)",
                                      textAlign: TextAlign.center,
                                      style: TextStyle(fontSize: 16)
                                  )
                              ),
                            )
                          ]
                      )
                  );
                }
              )
            )
          ]
        )
      )
    );
  }
}