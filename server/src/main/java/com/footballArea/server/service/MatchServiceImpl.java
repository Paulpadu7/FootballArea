package com.footballArea.server.service;

import com.footballArea.server.model.Match;
import com.footballArea.server.repo.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> getAll() {
        return (List<Match>) matchRepository.findAll();
    }
}
