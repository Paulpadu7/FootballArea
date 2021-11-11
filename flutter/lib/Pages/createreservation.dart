import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:football_area/Domain/match.dart';
import 'package:football_area/Repository/reservationrepository.dart';
import 'myreservationspage.dart';

enum TicketType{VIP,long_side,short_side}

class CreateReservation extends StatefulWidget{
  Match match;
  ReservationRepositorty reservationRepositorty = new ReservationRepositorty();
  CreateReservation(this.reservationRepositorty,this.match);

  @override
  State<StatefulWidget> createState() => CreateReservationState(reservationRepositorty, match);
}

class CreateReservationState extends State<CreateReservation> {
  Match match;
  ReservationRepositorty reservationRepositorty = new ReservationRepositorty();

  CreateReservationState(this.reservationRepositorty, this.match);

  TicketType? ticketType = null;
  int number_of_tickets =0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: Drawer(),
        appBar: AppBar(),
        body: Center(
          child:Padding(
            padding: EdgeInsets.all(20),
            child: ListView(
             // crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
            Positioned(
                child:
                  Column(
                    children: [
                        Text(match.home_team + " vs " + match.away_team,style:TextStyle(fontSize: 25)),
                        SizedBox(height: 25),
                        Text(match.date,style:TextStyle(fontSize: 20)),
                        SizedBox(height: 25),
                        Text("Select your ticket type:",style:TextStyle(fontSize: 18)),
                        SizedBox(height: 25),
                    ]
                    )
            ),
            Positioned(

            child: Column(
            children: <Widget>[
            _myRadioButton(
            title: "Short side",
                value: TicketType.short_side,
                onChanged: (newValue) =>
                    setState(() => ticketType = newValue), Function: null
            ),
              _myRadioButton(
                  title: "Long side",
                  value: TicketType.long_side,
                  onChanged: (newValue) =>
                      setState(() => ticketType = newValue), Function: null
              ),
              _myRadioButton(
                  title: "VIP",
                  value: TicketType.VIP,
                  onChanged: (newValue) =>
                      setState(() => ticketType = newValue), Function: null
              )
              ],
        )
            ),
              Container(
                  child:Padding(
                    padding: EdgeInsets.only(
                      left: 100,right: 100,top: 40,bottom: 40
                    ),
                    child: Column(
                      children: [
                        Text("Number of tickets:",style:TextStyle(fontSize: 18)),
                        TextField(textAlign: TextAlign.center,
                          keyboardType: TextInputType.number,
                          onChanged: (val) {
                            setState(() => number_of_tickets = int.tryParse(val)!);
                          }
                        )
                      ],
                    ),
                  ),
              ),
              Container(
                child:Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Text("Total price is:  " + calculate_total_price().toString() + " (ron)",style: TextStyle(fontSize: 20),)
                  ],
                ),
              ),
            Positioned(child:
            Padding(padding: EdgeInsets.all(50),
              child:
                MaterialButton(
                    height: 30.0,
                    color: Colors.lightGreen,
                    minWidth: 50.0,
                    child: Text("Create reservation", style: TextStyle(fontSize: 15)),
                    onPressed: () {

                      if(calculate_total_price()!=0){
                        reservationRepositorty.add(1, match, number_of_tickets, "", calculate_total_price());
                        Navigator.push(context, MaterialPageRoute(builder: (context) {
                          return MyReservationsPage(reservationRepositorty);
                        }
                        )
                        );
                      }
                      else {
                        showDialog<String>(
                          context: context,
                          builder: (BuildContext context) => AlertDialog(
                            title: const Text('Error!'),
                            content: const Text('You must complete all fields'),
                            actions: <Widget>[
                              TextButton(
                                onPressed: () => Navigator.pop(context, 'OK'),
                                child: const Text('OK'),
                              ),
                            ],
                          ),
                        );
                    }
                    }
                ),
            )
            ),
          ]
          ),
        )
        )
    );
  }

  Widget _myRadioButton(
      {required String title, required TicketType value, required void Function,required void onChanged(newValue) }) {
    return RadioListTile(
      value: value,
      groupValue: ticketType,
      onChanged: onChanged,
      title: Text(title),
      subtitle: Text("You select your seat when you pick-up your ticket"),
    );
  }
  int calculate_total_price()
  {
    int sum =0;
    if(ticketType==null)
      return 0;
    else
      {
        if(ticketType == TicketType.short_side)
          sum = number_of_tickets * match.price;
        else{
          if(ticketType == TicketType.long_side)
            sum = number_of_tickets * match.price * 2;
          else
            sum = number_of_tickets * match.price * 5;
        }
      }
    return sum;
  }
}