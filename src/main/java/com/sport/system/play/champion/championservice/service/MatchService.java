package com.sport.system.play.champion.championservice.service;

import com.sport.system.play.champion.championservice.entity.Match;
import com.sport.system.play.champion.championservice.presentation.presenter.MatchPresenter;
import org.springframework.http.ResponseEntity;

public interface MatchService {
    ResponseEntity save(MatchPresenter presenter);
    ResponseEntity getMatchesActive(String journeyId);
    ResponseEntity updateMatch(MatchPresenter presenter);
    ResponseEntity deleteMatch(String matchId);
    ResponseEntity closedMatch(String matchId);
    MatchPresenter buildPresenterMatchEntity(Match entity);
    Match buildEntityMatchPresenter(MatchPresenter presenter);
}
