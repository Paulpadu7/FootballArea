package com.footballArea.server.repo;

import com.footballArea.server.model.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository  extends CrudRepository<Match,Long> {
}
