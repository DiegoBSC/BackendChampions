package com.sport.system.play.champion.championservice.presentation.controller;

import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import com.sport.system.play.champion.championservice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TeamController {
    @Autowired
    public TeamService service;

    @PostMapping("/team/save")
    public ResponseEntity saveTeam(@RequestBody TeamPresenter presenter){
        return service.save(presenter);
    }

    @GetMapping("/team/teamsFilter")
    public ResponseEntity getTeam(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam String mainFilter){
        return service.getTeamsActive(page, size, mainFilter);
    }

    @PutMapping("/team/update")
    public ResponseEntity updateTeam(@RequestBody TeamPresenter presenter){
        return  service.updateTeam(presenter);
    }

    @DeleteMapping("/team/delete")
    public ResponseEntity deleteTeam(@RequestParam String teamId){
        return  service.deleteTeam(teamId);
    }
}
