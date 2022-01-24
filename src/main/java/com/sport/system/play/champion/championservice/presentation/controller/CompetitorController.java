package com.sport.system.play.champion.championservice.presentation.controller;

import com.sport.system.play.champion.championservice.enums.EnumTypeCompetitor;
import com.sport.system.play.champion.championservice.presentation.presenter.CompetitorPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.JourneyPresenter;
import com.sport.system.play.champion.championservice.service.CompetitorService;
import com.sport.system.play.champion.championservice.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CompetitorController {
    @Autowired
    public CompetitorService service;

    @PostMapping("/competitor/save")
    public ResponseEntity saveCompetitor(@RequestBody CompetitorPresenter presenter){
        return service.save(presenter);
    }

    @GetMapping("/competitor/competitorFilter")
    public ResponseEntity getCompetitorFilter(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam String mainFilter,
                                  @RequestParam String typeCompetitor){
        return service.getCompetitorActive(page, size, mainFilter, typeCompetitor);
    }

    @PutMapping("/competitor/update")
    public ResponseEntity updateCompetitor(@RequestBody CompetitorPresenter presenter){
        return  service.updateCompetitor(presenter);
    }

    @DeleteMapping("/competitor/delete")
    public ResponseEntity deleteCompetitor(@RequestParam String competitorId){
        return  service.deleteCompetitor(competitorId);
    }
}
