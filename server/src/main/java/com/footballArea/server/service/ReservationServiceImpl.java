package com.footballArea.server.service;

import com.footballArea.server.model.Match;
import com.footballArea.server.model.Reservation;
import com.footballArea.server.repo.MatchRepository;
import com.footballArea.server.repo.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private MatchRepository matchRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,MatchRepository matchRepository) {
        this.reservationRepository = reservationRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Reservation> getAll() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsForOneUser(Long id) {
        List<Reservation> reservations = new ArrayList<>();
        for(Reservation reservation : reservationRepository.findAll()){
            if(reservation.getIdUser().equals(id))
                reservations.add(reservation);
        }
        return reservations;
    }

    @Override
    public void createReservation(Reservation reservation) {
        if(matchRepository.findById(reservation.getIdMatch()).isPresent()) {
            Match match = matchRepository.findById(reservation.getIdMatch()).get();
            if(match.getNo_of_tickets() - reservation.getNo_of_tickets()<0)
            {
                reservation.setStatus("CANCELED");
            }
            else
            {
                reservation.setStatus("ACCEPTED");
                match.setNo_of_tickets(match.getNo_of_tickets() - reservation.getNo_of_tickets());
            }
            matchRepository.save(match);
            reservationRepository.save(reservation);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        if (reservationRepository.findById(reservation.getIdReservation()).isPresent()) {
            Reservation reservationFromDB = reservationRepository.findById(reservation.getIdReservation()).get();
            if (matchRepository.findById(reservation.getIdMatch()).isPresent()) {
                Match match = matchRepository.findById(reservation.getIdMatch()).get();
                if (match.getNo_of_tickets() - reservation.getNo_of_tickets() < 0) {
                    reservation.setStatus("CANCELED");
                } else reservation.setStatus("ACCEPTED");
                {
                    match.setNo_of_tickets(match.getNo_of_tickets() + reservationFromDB.getNo_of_tickets() - reservation.getNo_of_tickets());
                    matchRepository.save(match);
                }
                reservationRepository.save(reservation);
            }
        }
    }

    @Override
    public void deleteReservation(Long idReservation) {

        if(reservationRepository.findById(idReservation).isPresent())
        {
            Reservation reservation = reservationRepository.findById(idReservation).get();
            if(matchRepository.findById(reservation.getIdMatch()).isPresent()) {
                Match match = matchRepository.findById(reservation.getIdMatch()).get();
                if(!reservation.getStatus().equals("CANCELED"))
                    match.setNo_of_tickets(match.getNo_of_tickets() + reservation.getNo_of_tickets());
                matchRepository.save(match);
            }
            reservationRepository.deleteById(idReservation);
        }
    }
}
