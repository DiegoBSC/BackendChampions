package com.sport.system.play.champion.championservice.presentation.controller;

import com.sport.system.play.champion.championservice.presentation.presenter.JourneyPresenter;
import com.sport.system.play.champion.championservice.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JourneyController {
    @Autowired
    public JourneyService service;

    @PostMapping("/journey/save")
    public ResponseEntity saveJourney(@RequestBody JourneyPresenter presenter){
        return service.save(presenter);
    }

    @GetMapping("/journey/journeyFilter")
    public ResponseEntity getJourneyFilter(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam String mainFilter){
        return service.getJourneyActive(page, size, mainFilter);
    }

    @PutMapping("/journey/update")
    public ResponseEntity updateJourney(@RequestBody JourneyPresenter presenter){
        return  service.updateJourney(presenter);
    }

    @DeleteMapping("/journey/delete")
    public ResponseEntity deleteJourney(@RequestParam String journeyId){
        return  service.deleteJourney(journeyId);
    }
}
