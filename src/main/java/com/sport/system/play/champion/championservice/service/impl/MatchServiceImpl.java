package com.sport.system.play.champion.championservice.service.impl;

import com.sport.system.play.champion.championservice.entity.Journey;
import com.sport.system.play.champion.championservice.entity.Match;
import com.sport.system.play.champion.championservice.entity.Team;
import com.sport.system.play.champion.championservice.entity.TeamResult;
import com.sport.system.play.champion.championservice.enums.EnumResult;
import com.sport.system.play.champion.championservice.enums.EnumStatusMatch;
import com.sport.system.play.champion.championservice.enums.TypeMessage;
import com.sport.system.play.champion.championservice.presentation.presenter.JourneyPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.MatchPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.MessagePresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import com.sport.system.play.champion.championservice.repository.MatchRepository;
import com.sport.system.play.champion.championservice.repository.TeamResultRepository;
import com.sport.system.play.champion.championservice.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    public MatchRepository repository;

    @Autowired
    public TeamResultRepository teamResultRepository;

    @Override
    public ResponseEntity save(MatchPresenter presenter) {
        repository.save(buildEntityMatchPresenter(presenter));
        return ResponseEntity.status(HttpStatus.CREATED).body(MessagePresenter.builder()
                .timeMessage(new Date())
                .typeMessage(TypeMessage.OK)
                .message("Partido Creado")
                .build());
    }

    @Override
    public ResponseEntity getMatchesActive(String journeyId) {
        List<Match> result = repository.findByJourney(Journey.builder()
                .id(UUID.fromString(journeyId))
                .build());
        return ResponseEntity.ok().body(result.stream().map(this::buildPresenterMatchEntity).collect(Collectors.toSet()));
    }

    @Override
    public ResponseEntity updateMatch(MatchPresenter presenter) {
        Match result = repository.findById(UUID.fromString(presenter.getId())).orElse(null);
        if (result == null)
            return ResponseEntity.badRequest().body(MessagePresenter.builder()
                    .message("Partido no encontrado")
                    .typeMessage(TypeMessage.ERROR)
                    .timeMessage(new Date())
                    .build());

        if (result.getStatus().equals(EnumStatusMatch.FIN))
            if (result == null)
                return ResponseEntity.badRequest().body(MessagePresenter.builder()
                        .message("No se puede modificar un partido finalizado")
                        .typeMessage(TypeMessage.ERROR)
                        .timeMessage(new Date())
                        .build());

        result.setStatus(presenter.getStatus());
        result.setMatchDate(presenter.getMatchDate());
        result.setLocalGoals(presenter.getLocalGoals());
        result.setVisitGoals(presenter.getVisitGoals());
        result.setLocation(presenter.getLocation());
        repository.save(result);
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Partido Actualizado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity deleteMatch(String matchId) {
        repository.deleteById(UUID.fromString(matchId));
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Partido Eliminado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity closedMatch(String matchId) {
        Match result = repository.findById(UUID.fromString(matchId)).orElse(null);
        if (result == null)
            return ResponseEntity.badRequest().body(MessagePresenter.builder()
                    .message("Partido no encontrado")
                    .typeMessage(TypeMessage.ERROR)
                    .timeMessage(new Date())
                    .build());
        if (result == null)
            return ResponseEntity.badRequest().body(MessagePresenter.builder()
                    .message("Partido no encontrado")
                    .typeMessage(TypeMessage.ERROR)
                    .timeMessage(new Date())
                    .build());
        result.setStatus(EnumStatusMatch.FIN);

        if (result.getLocalGoals().compareTo(result.getVisitGoals()) > 0) {
            // TODO: Local Ganador
            teamResultRepository.save(TeamResult.builder()
                    .id(UUID.randomUUID())
                    .points(BigDecimal.valueOf(3))
                    .result(EnumResult.WINNER)
                    .team(Team.builder()
                            .id(result.getLocalTeam().getId())
                            .build())
                    .match(Match.builder()
                            .id(result.getId())
                            .build())
                    .build());
            teamResultRepository.save(TeamResult.builder()
                    .id(UUID.randomUUID())
                    .points(BigDecimal.valueOf(0))
                    .result(EnumResult.LOSER)
                    .team(Team.builder()
                            .id(result.getVisitTeam().getId())
                            .build())
                    .match(Match.builder()
                            .id(result.getId())
                            .build())
                    .build());
        }

        if (result.getLocalGoals().compareTo(result.getVisitGoals()) < 0) {
            // TODO: Visitante Ganador
            teamResultRepository.save(TeamResult.builder()
                    .id(UUID.randomUUID())
                    .points(BigDecimal.valueOf(0))
                    .result(EnumResult.LOSER)
                    .team(Team.builder()
                            .id(result.getLocalTeam().getId())
                            .build())
                    .match(Match.builder()
                            .id(result.getId())
                            .build())
                    .build());
            teamResultRepository.save(TeamResult.builder()
                    .id(UUID.randomUUID())
                    .points(BigDecimal.valueOf(3))
                    .result(EnumResult.WINNER)
                    .team(Team.builder()
                            .id(result.getVisitTeam().getId())
                            .build())
                    .match(Match.builder()
                            .id(result.getId())
                            .build())
                    .build());
        }

        if (result.getLocalGoals().compareTo(result.getVisitGoals()) == 0) {
            // TODO: Empates
            teamResultRepository.save(TeamResult.builder()
                    .id(UUID.randomUUID())
                    .points(BigDecimal.valueOf(1))
                    .result(EnumResult.DRAWN)
                    .team(Team.builder()
                            .id(result.getLocalTeam().getId())
                            .build())
                    .match(Match.builder()
                            .id(result.getId())
                            .build())
                    .build());
            teamResultRepository.save(TeamResult.builder()
                    .id(UUID.randomUUID())
                    .points(BigDecimal.valueOf(1))
                    .result(EnumResult.DRAWN)
                    .team(Team.builder()
                            .id(result.getVisitTeam().getId())
                            .build())
                    .match(Match.builder()
                            .id(result.getId())
                            .build())
                    .build());
        }

        repository.save(result);

        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Partido Finalizado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public MatchPresenter buildPresenterMatchEntity(Match entity) {
        return MatchPresenter.builder()
                .id(entity.getId().toString())
                .createdDate(entity.getCreatedDate())
                .localTeamPresenter(TeamPresenter.builder()
                        .id(entity.getLocalTeam().getId().toString())
                        .build())
                .visitTeamPresenter(TeamPresenter.builder()
                        .id(entity.getVisitTeam().getId().toString())
                        .build())
                .journeyPresenter(JourneyPresenter.builder()
                        .id(entity.getJourney().getId().toString())
                        .build())
                .localGoals(entity.getLocalGoals())
                .visitGoals(entity.getVisitGoals())
                .matchDate(entity.getMatchDate())
                .matchTime(entity.getMatchTime())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public Match buildEntityMatchPresenter(MatchPresenter presenter) {
        return Match.builder()
                .id(presenter.getId() != null ? UUID.fromString(presenter.getId()) : null)
                .createdDate(presenter.getCreatedDate() != null ? presenter.getCreatedDate() : new Date())
                .localTeam(Team.builder()
                        .id(UUID.fromString(presenter.getLocalTeamPresenter().getId()))
                        .build())
                .visitTeam(Team.builder()
                        .id(UUID.fromString(presenter.getVisitTeamPresenter().getId()))
                        .build())
                .journey(Journey.builder()
                        .id(UUID.fromString(presenter.getJourneyPresenter().getId()))
                        .build())
                .localGoals(presenter.getLocalGoals())
                .visitGoals(presenter.getVisitGoals())
                .matchDate(presenter.getMatchDate())
                .matchTime(presenter.getMatchTime())
                .location(presenter.getLocation())
                .status(presenter.getStatus())
                .build();
    }
}
