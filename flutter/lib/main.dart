import 'package:flutter/material.dart';
import 'package:football_area/Repository/reservationrepository.dart';
import 'package:football_area/pages/matcheslistpage.dart';

void main() {
  ReservationRepositorty reservationRepositorty = new ReservationRepositorty();
  //reservationRepositorty.reservationList.clear();
  runApp( MaterialApp(
    debugShowCheckedModeBanner: false,
    home: MatchesListPage(reservationRepositorty)
  ));
}