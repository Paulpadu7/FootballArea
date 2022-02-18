package com.footballArea.server.controllers;

import com.footballArea.server.model.Match;
import com.footballArea.server.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(MatchController.BASE_URL)
public class MatchController {
    public static final String BASE_URL = "/match";
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Match> getMatches(){
        return matchService.getAll();
    }
}
