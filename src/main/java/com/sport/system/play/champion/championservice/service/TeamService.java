package com.sport.system.play.champion.championservice.service;

import com.sport.system.play.champion.championservice.entity.Team;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import org.springframework.http.ResponseEntity;

public interface TeamService {
    ResponseEntity save(TeamPresenter presenter);
    ResponseEntity getTeamsActive(Integer page, Integer size, String mainFilter);
    ResponseEntity updateTeam(TeamPresenter presenter);
    ResponseEntity deleteTeam(String teamId);
    TeamPresenter buildPresenterTeamEntity(Team entity);
    Team buildEntityTeamPresenter(TeamPresenter presenter);
}
