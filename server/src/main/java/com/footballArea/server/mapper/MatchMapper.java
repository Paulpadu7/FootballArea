package com.footballArea.server.mapper;

import com.footballArea.server.model.Match;
import com.footballArea.server.model.MatchDTO;

public class MatchMapper {

    public Match matchDTOtoMatch(MatchDTO matchDTO){

        Match match = new Match();
        match.setHome_team(matchDTO.getHome_team());
        match.setAway_team(matchDTO.getAway_team());
        match.setLocation(matchDTO.getLocation());
        match.setImage(matchDTO.getImage());
        match.setMatchDate(match.getMatchDate());
        match.setNo_of_tickets(match.getNo_of_tickets());
        match.setPrice(matchDTO.getPrice());
        return match;
    }

    public  MatchDTO matchToMatchDTO(Match match){

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setHome_team(match.getHome_team());
        matchDTO.setAway_team(match.getAway_team());
        matchDTO.setLocation(match.getLocation());
        matchDTO.setImage(match.getImage());
        matchDTO.setMatchDate(match.getMatchDate());
        matchDTO.setNo_of_tickets(match.getNo_of_tickets());
        matchDTO.setPrice(match.getPrice());
        return matchDTO;
    }
}
