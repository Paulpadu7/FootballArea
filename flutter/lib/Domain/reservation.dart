import 'package:football_area/Domain/match.dart';

class Reservation
{
  int idReservation = 0;
  int idUser = 0;
  Match match;
  int no_of_tickets = 0;
  String reservation_date = "";
  int total_price = 0;

  Reservation(this.idReservation, this.idUser, this.match, this.no_of_tickets,
      this.reservation_date, this.total_price);
}