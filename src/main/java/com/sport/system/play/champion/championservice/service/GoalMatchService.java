package com.sport.system.play.champion.championservice.service;

import com.sport.system.play.champion.championservice.entity.GoalMatch;
import com.sport.system.play.champion.championservice.presentation.presenter.GoalMatchPresenter;
import org.springframework.http.ResponseEntity;

public interface GoalMatchService {
    ResponseEntity save(GoalMatchPresenter presenter);
    ResponseEntity getGoalMatch(String matchId);
    ResponseEntity updateGoalMatch(GoalMatchPresenter presenter);
    ResponseEntity deleteGoalMatch(String goalMatchId);
    GoalMatchPresenter buildPresenterGoalMatchEntity(GoalMatch entity);
    GoalMatch buildEntityGoalMatchPresenter(GoalMatchPresenter presenter);
}
