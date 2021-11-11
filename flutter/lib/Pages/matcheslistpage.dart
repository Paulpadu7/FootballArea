import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:football_area/Domain/match.dart';
import 'package:football_area/Pages/createreservation.dart';
import 'package:football_area/Repository/reservationrepository.dart';
import 'package:football_area/helpers/utils.dart';
import 'package:football_area/pages/myreservationspage.dart';


class MatchesListPage extends StatelessWidget{
  ReservationRepositorty reservationRepositorty = new ReservationRepositorty();
  MatchesListPage(this.reservationRepositorty);
  List<Match> matchesList = Utils.getMockedMatches();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: Drawer(),
      appBar: new AppBar(actions: <Widget>[
        RaisedButton(
          child: Text("My reservations", style: TextStyle(fontSize: 15)),
          onPressed: (){
            Navigator.push(context, MaterialPageRoute(builder: (context){
              return MyReservationsPage(reservationRepositorty);
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
            child: Text('Games of the week',
              textAlign:TextAlign.center,
                style: TextStyle(color: Colors.black,fontSize: 25)
            ),
            ),
            Expanded(
                child: ListView.builder(
                itemCount: matchesList.length,
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
                                decoration:BoxDecoration(
                                    image: DecorationImage(
                                        fit: BoxFit.fill,
                                        image: AssetImage("assets/" + matchesList[index].image + ".png")
                                    )
                                ) ,
                              ),
                            ),
                        ButtonBar(buttonHeight: 30,
                          alignment: MainAxisAlignment.center,
                          children: [
                            Text(matchesList[index].home_team + " vs " + matchesList[index].away_team,style: TextStyle(fontSize: 18),),
                            MaterialButton(
                              height: 30.0,
                              color: Colors.green,
                              minWidth: 50.0,
                              child: new Text("BOOK NOW!"),
                              onPressed: () {
                                Navigator.push(context,
                                MaterialPageRoute(builder: (context)
                                {
                                  return CreateReservation(
                                      reservationRepositorty,
                                      matchesList[index]);
                                }
                                )
                                );
                              },
                            ),
                          ],
                        ),
                        Positioned(
                          left: 130,
                          top: 100,
                          child:Container(
                              width:100,
                              child: Column(
                                children: [
                                  Text("Date"),
                                  Text(matchesList[index].date),
                                  // Text("Tickets"),
                                  // Text(matchesList[index].no_of_tickets.toString(),style: TextStyle(color: Colors.green),)
                                ],
                              )
                          ),
                        ),
                        Positioned(
                          left: 10,
                          top: 160,
                          child:Container(
                              width:350,
                            child: Text(
                            matchesList[index].location,
                            textAlign: TextAlign.center,
                            style: TextStyle(fontSize: 16)
                            )
                          ),
                        ),
                        Positioned(
                          top: 180,
                          left: 100,
                          child:Container(
                              width:350,
                              child: Text(
                                  "The price starts at: " + matchesList[index].price.toString() + "(ron)",
                                  textAlign: TextAlign.center,
                                  style: TextStyle(fontSize: 16)
                              )
                          ),
                        )
                      ],
                    )
                  );
                },
            ),
            )
          ],
        ),
      ),
    );
  }
}