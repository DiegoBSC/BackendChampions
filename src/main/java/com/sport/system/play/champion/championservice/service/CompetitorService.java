package com.sport.system.play.champion.championservice.service;

import com.sport.system.play.champion.championservice.entity.Competitor;
import com.sport.system.play.champion.championservice.enums.EnumTypeCompetitor;
import com.sport.system.play.champion.championservice.presentation.presenter.CompetitorPresenter;
import org.springframework.http.ResponseEntity;

public interface CompetitorService {
    ResponseEntity save(CompetitorPresenter presenter);
    ResponseEntity getCompetitorActive(Integer page, Integer size, String mainFilter, String typeCompetitor);
    ResponseEntity updateCompetitor(CompetitorPresenter presenter);
    ResponseEntity deleteCompetitor(String competitorId);
    CompetitorPresenter buildPresenterCompetitorEntity(Competitor entity);
    Competitor buildEntityCompetitorPresenter(CompetitorPresenter presenter);
}
