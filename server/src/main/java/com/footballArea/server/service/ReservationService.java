package com.footballArea.server.service;

import com.footballArea.server.model.Reservation;

import java.util.List;

public interface ReservationService {

    public List<Reservation> getAll();
    public List<Reservation> getReservationsForOneUser(Long id);
    public void createReservation(Reservation reservation);
    public void updateReservation(Reservation reservation);
    public void deleteReservation(Long idReservation);
}
