package com.sport.system.play.champion.championservice.presentation.controller;

import com.sport.system.play.champion.championservice.presentation.presenter.MatchPresenter;
import com.sport.system.play.champion.championservice.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MatchController {
    @Autowired
    public MatchService service;

    @PostMapping("/match/save")
    public ResponseEntity saveMatch(@RequestBody MatchPresenter presenter){
        return service.save(presenter);
    }

    @GetMapping("/match/teamsFilter")
    public ResponseEntity getMatches(@RequestParam String journeyId){
        return service.getMatchesActive(journeyId);
    }

    @PutMapping("/match/update")
    public ResponseEntity updateMatch(@RequestBody MatchPresenter presenter){
        return  service.updateMatch(presenter);
    }

    @DeleteMapping("/match/delete")
    public ResponseEntity deleteMatch(@RequestParam String matchId){
        return  service.deleteMatch(matchId);
    }

    @PutMapping("/match/closedMatch")
    public ResponseEntity closedMatch(@RequestParam String matchId){
        return service.closedMatch(matchId);
    }
}
