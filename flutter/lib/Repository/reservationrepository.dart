import 'package:football_area/Domain/match.dart';
import 'package:football_area/Domain/reservation.dart';

class ReservationRepositorty
{
  List<Reservation> reservationList = [];
  void add(int idUser, Match match, int no_of_tickets, String reservation_date, int total_price)
  {
    Reservation reservation = new Reservation(reservationList.length + 1, idUser, match, no_of_tickets, reservation_date, total_price);
    reservationList.add(reservation);
  }
  void delete(int idReservation)
  {
    for(Reservation reservation in reservationList)
      {
        if(reservation.idReservation == idReservation)
          {
            reservationList.remove(reservation);
            break;
          }
      }
  }
  void update(int idReservation, int no_of_tickets, int total_price)
  {
    for(Reservation reservation in reservationList)
    {
      if(reservation.idReservation == idReservation)
      {
        reservation.no_of_tickets = no_of_tickets;
        reservation.total_price = total_price;
      }
    }
  }
  List<Reservation> getAll()
  {
    return reservationList;
  }
}