package com.sport.system.play.champion.championservice.service.impl;

import com.sport.system.play.champion.championservice.entity.Competitor;
import com.sport.system.play.champion.championservice.entity.GoalMatch;
import com.sport.system.play.champion.championservice.entity.Match;
import com.sport.system.play.champion.championservice.enums.TypeMessage;
import com.sport.system.play.champion.championservice.presentation.presenter.CompetitorPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.GoalMatchPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.MatchPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.MessagePresenter;
import com.sport.system.play.champion.championservice.repository.CompetitorRepository;
import com.sport.system.play.champion.championservice.repository.GoalMatchRepository;
import com.sport.system.play.champion.championservice.repository.MatchRepository;
import com.sport.system.play.champion.championservice.service.GoalMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GoalMatchServiceImpl implements GoalMatchService {
    @Autowired
    public GoalMatchRepository repository;

    @Autowired
    public MatchRepository matchRepository;

    @Autowired
    public CompetitorRepository competitorRepository;

    @Override
    public ResponseEntity save(GoalMatchPresenter presenter) {
       try {
           addGoalsMatchByCompetitor(presenter);
           repository.save(buildEntityGoalMatchPresenter(presenter));
           return ResponseEntity.status(HttpStatus.CREATED).body(MessagePresenter.builder()
                   .timeMessage(new Date())
                   .typeMessage(TypeMessage.OK)
                   .message("Goles Registrados")
                   .build());

       }catch (ValidationException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessagePresenter.builder()
                   .timeMessage(new Date())
                   .typeMessage(TypeMessage.ERROR)
                   .message(e.getMessage())
                   .build());
       }
    }

    @Override
    public ResponseEntity getGoalMatch(String matchId) {
        List<GoalMatchPresenter> result = repository.findByMatch(Match.builder()
                .id(UUID.fromString(matchId))
                .build()).stream().map(this::buildPresenterGoalMatchEntity).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity updateGoalMatch(GoalMatchPresenter presenter) {
        try {
            GoalMatch goalMatchDb = repository.findById(UUID.fromString(presenter.getId())).orElse(null);
            if(goalMatchDb == null )
                return ResponseEntity.badRequest().body(MessagePresenter.builder()
                        .message("Gol no encontrado")
                        .typeMessage(TypeMessage.ERROR)
                        .timeMessage(new Date())
                        .build());

            subtractGoalsMatchByCompetitor(presenter, goalMatchDb);
            addGoalsMatchByCompetitor(presenter);
            goalMatchDb.setGoals(presenter.getGoals());
            repository.save(goalMatchDb);
            return ResponseEntity.ok().body(MessagePresenter.builder()
                    .message("Gol Actualizado")
                    .typeMessage(TypeMessage.OK)
                    .timeMessage(new Date())
                    .build());
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessagePresenter.builder()
                    .timeMessage(new Date())
                    .typeMessage(TypeMessage.ERROR)
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity deleteGoalMatch(String goalMatchId) {
        try {
            GoalMatch goalMatchDb = repository.findById(UUID.fromString(goalMatchId)).orElse(null);
            if(goalMatchDb == null )
                return ResponseEntity.badRequest().body(MessagePresenter.builder()
                        .message("Jornada no encontrada")
                        .typeMessage(TypeMessage.ERROR)
                        .timeMessage(new Date())
                        .build());

            subtractGoalsMatchByCompetitor(buildPresenterGoalMatchEntity(goalMatchDb), goalMatchDb);
            repository.deleteById(UUID.fromString(goalMatchId));
            return ResponseEntity.ok().body(MessagePresenter.builder()
                    .message("Gol Eliminado")
                    .typeMessage(TypeMessage.OK)
                    .timeMessage(new Date())
                    .build());
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessagePresenter.builder()
                    .timeMessage(new Date())
                    .typeMessage(TypeMessage.ERROR)
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public GoalMatchPresenter buildPresenterGoalMatchEntity(GoalMatch entity) {
        return GoalMatchPresenter.builder()
                .id(entity.getId().toString())
                .matchPresenter(MatchPresenter.builder()
                        .id(entity.getMatch().getId().toString())
                        .build())
                .competitorPresenter(CompetitorPresenter.builder()
                        .id(entity.getCompetitor().getId().toString())
                        .build())
                .goals(entity.getGoals())
                .build();
    }

    @Override
    public GoalMatch buildEntityGoalMatchPresenter(GoalMatchPresenter presenter) {
        return GoalMatch.builder()
                .id(presenter.getId() != null ? UUID.fromString(presenter.getId()) : null)
                .match(Match.builder()
                        .id(UUID.fromString(presenter.getMatchPresenter().getId()))
                        .build())
                .competitor(Competitor.builder()
                        .id(UUID.fromString(presenter.getCompetitorPresenter().getId()))
                        .build())
                .goals(presenter.getGoals())
                .build();
    }

    private void addGoalsMatchByCompetitor(GoalMatchPresenter presenter) throws ValidationException {
        BigDecimal totalGoals = BigDecimal.ZERO;
        Match entityDb = matchRepository.findById(UUID.fromString(presenter.getMatchPresenter().getId())).orElse(null);
        Competitor competitor = competitorRepository.findById(UUID.fromString(presenter.getCompetitorPresenter().getId())).orElse(null);

        if (entityDb == null)
            throw new ValidationException( "Partido no encontrado");
        if (competitor == null)
            throw new ValidationException( "Jugador no encontrado");

        if(entityDb.getLocalTeam().getId().equals(competitor.getTeam().getId())){
            totalGoals = totalGoals.add(entityDb.getLocalGoals()).add(presenter.getGoals());
            entityDb.setLocalGoals(totalGoals);
        }

        if(entityDb.getVisitTeam().getId().equals(competitor.getTeam().getId())){
            totalGoals = totalGoals.add(entityDb.getVisitGoals()).add(presenter.getGoals());
            entityDb.setVisitGoals(totalGoals);
        }
        matchRepository.save(entityDb);
    }

    private void subtractGoalsMatchByCompetitor(GoalMatchPresenter presenter, GoalMatch goalMatchDb) throws ValidationException {
        BigDecimal totalGoals = BigDecimal.ZERO;
        Match entityDb = matchRepository.findById(UUID.fromString(presenter.getMatchPresenter().getId())).orElse(null);
        Competitor competitor = competitorRepository.findById(UUID.fromString(presenter.getCompetitorPresenter().getId())).orElse(null);

        if (entityDb == null)
            throw new ValidationException( "Partido no encontrado");
        if (competitor == null)
            throw new ValidationException( "Jugador no encontrado");

        if(entityDb.getLocalTeam().getId().equals(competitor.getTeam().getId())){
            totalGoals = totalGoals.add(entityDb.getLocalGoals()).subtract(goalMatchDb.getGoals());
            entityDb.setLocalGoals(totalGoals);
        }

        if(entityDb.getVisitTeam().getId().equals(competitor.getTeam().getId())){
            totalGoals = totalGoals.add(entityDb.getVisitGoals()).subtract(goalMatchDb.getGoals());
            entityDb.setVisitGoals(totalGoals);
        }
        matchRepository.save(entityDb);
    }
}
