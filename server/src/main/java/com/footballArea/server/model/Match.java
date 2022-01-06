package com.footballArea.server.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String home_team;
    private String away_team;
    private String location;
    private String image;
    private String matchDate;
    private int no_of_tickets;
    private int price;
}
