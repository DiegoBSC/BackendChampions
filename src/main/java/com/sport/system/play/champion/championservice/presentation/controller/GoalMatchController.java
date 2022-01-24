package com.sport.system.play.champion.championservice.presentation.controller;

import com.sport.system.play.champion.championservice.presentation.presenter.GoalMatchPresenter;
import com.sport.system.play.champion.championservice.service.GoalMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class GoalMatchController {
    @Autowired
    public GoalMatchService service;

    @PostMapping("/goalMatch/save")
    public ResponseEntity saveGoalMatch(@RequestBody GoalMatchPresenter presenter){
        return service.save(presenter);
    }

    @GetMapping("/goalMatch/goalMatchByMatch")
    public ResponseEntity getGoalMatches(@RequestParam String matchId){
        return service.getGoalMatch(matchId);
    }

    @PutMapping("/goalMatch/update")
    public ResponseEntity updateGoalMatch(@RequestBody GoalMatchPresenter presenter){
        return  service.updateGoalMatch(presenter);
    }

    @DeleteMapping("/goalMatch/delete")
    public ResponseEntity deleteGoalMatch(@RequestParam String goalMatchId){
        return  service.deleteGoalMatch(goalMatchId);
    }
}
