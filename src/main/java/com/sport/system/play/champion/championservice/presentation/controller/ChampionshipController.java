package com.sport.system.play.champion.championservice.presentation.controller;

import com.sport.system.play.champion.championservice.presentation.presenter.ChampionshipPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import com.sport.system.play.champion.championservice.service.ChampionshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ChampionshipController {

    @Autowired
    public ChampionshipService service;

    @PostMapping("/championship/save")
    public ResponseEntity saveChampionship(@RequestBody ChampionshipPresenter presenter){
     return service.save(presenter);
    }

    @GetMapping("/championship/championshipsAll")
    public ResponseEntity getChampionship(){
        return service.getChampionshipsActive();
    }

    @PutMapping("/championship/update")
    public ResponseEntity updateChampionship(@RequestBody ChampionshipPresenter presenter){
        return  service.updateChampionships(presenter);
    }

    @PutMapping("/championship/addTeam")
    public ResponseEntity addTeamChampionship(@RequestParam String championshipId, @RequestBody List<TeamPresenter> teams){
        return  service.addTeamChampionships(championshipId, teams);
    }

    @DeleteMapping("/championship/delete")
    public ResponseEntity deleteChampionship(@RequestParam String idChampionship){
        return  service.deleteChampionship(idChampionship);
    }
}
