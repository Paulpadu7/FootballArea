package com.footballArea.server.controllers;

import com.footballArea.server.model.Reservation;
import com.footballArea.server.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ReservationController.BASE_URL)
public class ReservationController {
    public static final String BASE_URL = "/reservation";
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getReservationsForUser(@PathVariable Long id)
    {
        System.out.println("reservations sent");
        return reservationService.getReservationsForOneUser(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createReservation(@RequestBody Reservation reservation){
        reservationService.createReservation(reservation);
        System.out.println("reservation created");
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateReservation(@RequestBody Reservation reservation){
        reservationService.updateReservation(reservation);
        System.out.println("reservation updated");
    }
    @GetMapping({"delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable Long id)
    {
        reservationService.deleteReservation(id);
        System.out.println("reservation deleted");
    }
}
