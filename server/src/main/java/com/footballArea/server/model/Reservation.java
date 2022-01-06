package com.footballArea.server.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private Long idUser;
    private Long idMatch;
    private int no_of_tickets;
    private LocalDateTime reservation_date;
    private int total_price;
    private String status;
}
