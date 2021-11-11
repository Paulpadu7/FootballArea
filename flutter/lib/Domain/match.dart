import 'package:flutter/cupertino.dart';

class Match{
  int id = 0;
  String home_team = "";
  String away_team = "";
  String location = "";
  String image = "";
  String date = "";
  int no_of_tickets = 0;
  int price = 0;
  Match(int id, String home_team, String away_team, String location, String image, String date, int no_of_tickets, int price)
  {
    this.id = id;
    this.home_team = home_team;
    this.away_team = away_team;
    this.location = location;
    this.image = image;
    this.date = date;
    this.no_of_tickets = no_of_tickets;
    this.price = price;
  }
}