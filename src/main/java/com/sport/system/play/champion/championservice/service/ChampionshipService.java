package com.sport.system.play.champion.championservice.service;

import com.sport.system.play.champion.championservice.entity.Championship;
import com.sport.system.play.champion.championservice.presentation.presenter.ChampionshipPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChampionshipService {
    ResponseEntity save(ChampionshipPresenter presenter);
    ResponseEntity getChampionshipsActive();
    ResponseEntity updateChampionships(ChampionshipPresenter presenter);
    ResponseEntity addTeamChampionships(String championshipId, List<TeamPresenter> teams);
    ResponseEntity deleteChampionship(String championshipId);
    ChampionshipPresenter buildPresenterChampionshipEntity(Championship entity);
    Championship buildEntityChampionshipPresenter(ChampionshipPresenter presenter);
}
