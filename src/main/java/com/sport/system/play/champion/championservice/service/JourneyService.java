package com.sport.system.play.champion.championservice.service;

import com.sport.system.play.champion.championservice.entity.Journey;
import com.sport.system.play.champion.championservice.presentation.presenter.JourneyPresenter;
import org.springframework.http.ResponseEntity;

public interface JourneyService {
    ResponseEntity save(JourneyPresenter presenter);
    ResponseEntity getJourneyActive(Integer page, Integer size, String mainFilter);
    ResponseEntity updateJourney(JourneyPresenter presenter);
    ResponseEntity deleteJourney(String journeyId);
    JourneyPresenter buildPresenterJourneyEntity(Journey entity);
    Journey buildEntityJourneyPresenter(JourneyPresenter presenter);
}
