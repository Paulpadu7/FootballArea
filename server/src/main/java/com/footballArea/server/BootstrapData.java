package com.footballArea.server;

import com.footballArea.server.model.Match;
import com.footballArea.server.model.User;
import com.footballArea.server.repo.MatchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.footballArea.server.repo.UserRepository;
@Component
public class BootstrapData implements CommandLineRunner {

    final UserRepository userRepository;
    final MatchRepository matchRepository;

    public BootstrapData(UserRepository userRepository,MatchRepository matchRepository)
    {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pass1");
      //  userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("pass2");
       // userRepository.save(user2);

        Match match1 = new Match();
        match1.setHome_team("Dinamo");
        match1.setAway_team("FCSB");
        match1.setLocation("Stadionul Dinamo");
        match1.setImage("http://192.168.100.20:8080/dinamo_vs_fcsb.png");
        match1.setMatchDate("05.05.2022");
        match1.setNo_of_tickets(3000);
        match1.setPrice(35);

        Match match2 = new Match();
        match2.setHome_team("CFR Cluj");
        match2.setAway_team("FC Rapid");
        match2.setLocation("Stadionul CFR");
        match2.setImage("http://192.168.100.20:8080/cfr_vs_rapid.png");
        match2.setMatchDate("06.06.2022");
        match2.setNo_of_tickets(3000);
        match2.setPrice(30);

        Match match3 = new Match();
        match3.setHome_team("FC Farul");
        match3.setAway_team("FC Craiova");
        match3.setLocation("Stadionul Farul");
        match3.setImage("http://192.168.100.20:8080/farul_vs_craiova.png");
        match3.setMatchDate("07.07.2022");
        match3.setNo_of_tickets(3000);
        match3.setPrice(25);

        Match match4 = new Match();
        match4.setHome_team("Gaz Metan");
        match4.setAway_team("FC UTA");
        match4.setLocation("Stadionul Gaz");
        match4.setImage("http://192.168.100.20:8080/gaz_vs_uta.png");
        match4.setMatchDate("08.08.2022");
        match4.setNo_of_tickets(3000);
        match4.setPrice(20);

//        matchRepository.save(match1);
//        matchRepository.save(match2);
//        matchRepository.save(match3);
//        matchRepository.save(match4);

    }
}
