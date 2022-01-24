package com.sport.system.play.champion.championservice.service.impl;

import com.sport.system.play.champion.championservice.entity.Championship;
import com.sport.system.play.champion.championservice.entity.Team;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import com.sport.system.play.champion.championservice.enums.TypeMessage;
import com.sport.system.play.champion.championservice.presentation.presenter.ChampionshipPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.MessagePresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import com.sport.system.play.champion.championservice.repository.ChampionshipRepository;
import com.sport.system.play.champion.championservice.service.ChampionshipService;
import com.sport.system.play.champion.championservice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    @Autowired
    public ChampionshipRepository championshipRepository;

    @Autowired
    public TeamService teamService;

    @Override
    public ResponseEntity save(ChampionshipPresenter presenter) {
        championshipRepository.save(buildEntityChampionshipPresenter(presenter));
        return ResponseEntity.status(HttpStatus.CREATED).body("Campeonato Creado");
    }

    @Override
    public ResponseEntity getChampionshipsActive() {
        List<Championship> result = (List<Championship>) championshipRepository.findByStatus(EnumStatusGeneral.ACT);
        List<ChampionshipPresenter> resultPresenters = result.stream()
                .map(this::buildPresenterChampionshipEntity).collect(Collectors.toList());
        return ResponseEntity.ok().body(resultPresenters);
    }

    @Override
    public ResponseEntity updateChampionships(ChampionshipPresenter presenter) {
        Championship entity = championshipRepository.findById(UUID.fromString(presenter.getId())).orElse(null);

        if(entity == null)
            return  ResponseEntity.badRequest().body(MessagePresenter.builder()
                    .message("Campeonato no encontrado")
                    .typeMessage(TypeMessage.ERROR)
                    .timeMessage(new Date())
                    .build());

        entity.setImage(presenter.getImage());
        entity.setStartDate(presenter.getStartDate());
        entity.setEndDate(presenter.getEndDate());
        entity.setName(presenter.getName());
        entity.setDescription(presenter.getDescription());
        entity.setStatus(presenter.getStatus());
        championshipRepository.save(entity);
        return ResponseEntity.ok().body(MessagePresenter.builder()
                        .message("Campeonato Actualizado")
                        .typeMessage(TypeMessage.OK)
                        .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity addTeamChampionships(String championshipId, List<TeamPresenter> teams) {

        Championship entity = championshipRepository.findById(UUID.fromString(championshipId)).orElse(null);

        if(entity == null)
            return  ResponseEntity.badRequest().body(MessagePresenter.builder()
                    .message("Campeonato no encontrado")
                    .typeMessage(TypeMessage.ERROR)
                    .timeMessage(new Date())
                    .build());

            entity.setTeams(new HashSet<>());
            teams.forEach(item->{
                entity.getTeams().add(Team.builder()
                                .id(UUID.fromString(item.getId()))
                        .build());
            });

            championshipRepository.save(entity);
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Campeonato Actualizado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity deleteChampionship(String championshipId) {
        championshipRepository.deleteById(UUID.fromString(championshipId));
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Campeonato Eliminado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ChampionshipPresenter buildPresenterChampionshipEntity(Championship entity) {
        return ChampionshipPresenter.builder()
                .id(entity.getId().toString())
                .createdDate(entity.getCreatedDate())
                .image(entity.getImage())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .teamsPresenter(getTeamsPresenters(entity.getTeams()))
                .build();
    }

    @Override
    public Championship buildEntityChampionshipPresenter(ChampionshipPresenter presenter) {
        return Championship.builder()
                .id(presenter.getId() != null ? UUID.fromString(presenter.getId()) : null)
                .createdDate(presenter.getCreatedDate() != null ? presenter.getCreatedDate() : new Date())
                .image(presenter.getImage())
                .startDate(presenter.getStartDate())
                .endDate(presenter.getEndDate())
                .name(presenter.getName())
                .description(presenter.getDescription())
                .status(presenter.getStatus())
                .build();
    }

    private Set<TeamPresenter> getTeamsPresenters(Set<Team> entityTeams){
        Set<TeamPresenter> listResult = new HashSet<>();
        entityTeams.forEach(item ->{
            listResult.add(teamService.buildPresenterTeamEntity(item));
        });
        return listResult;
    }
}
