package com.footballArea.server.model;

import lombok.Data;

@Data
public class MatchDTO {
    private String home_team;
    private String away_team;
    private String location;
    private String image;
    private String matchDate;
    private int no_of_tickets;
    private int price;
}
