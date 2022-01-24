package com.sport.system.play.champion.championservice.service.impl;

import com.sport.system.play.champion.championservice.entity.Team;
import com.sport.system.play.champion.championservice.enums.TypeMessage;
import com.sport.system.play.champion.championservice.presentation.presenter.MessagePresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.Pager;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import com.sport.system.play.champion.championservice.repository.TeamRepository;
import com.sport.system.play.champion.championservice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    public TeamRepository repository;

    @Override
    public ResponseEntity save(TeamPresenter presenter) {
        repository.save(buildEntityTeamPresenter(presenter));
        return ResponseEntity.status(HttpStatus.CREATED).body(MessagePresenter.builder()
                        .timeMessage(new Date())
                        .typeMessage(TypeMessage.OK)
                        .message("Equipo Creado")
                .build());
    }

    @Override
    public ResponseEntity getTeamsActive(Integer page, Integer size, String mainFilter) {
        Pageable paging = PageRequest.of(page, size, Sort.by("name"));

        Page<Team> result = repository.findByFilters(mainFilter, paging);

        Set<TeamPresenter> resultPresenter =
                result.getContent().stream().map(this::buildPresenterTeamEntity).collect(Collectors.toSet());
        return ResponseEntity.ok().body(Pager.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .data(resultPresenter)
                .build());
    }

    @Override
    public ResponseEntity updateTeam(TeamPresenter presenter) {
        Team result = repository.findById(UUID.fromString(presenter.getId())).orElse(null);
        if(result == null )
            return  ResponseEntity.badRequest().body(MessagePresenter.builder()
                .message("Equipo no encontrado")
                .typeMessage(TypeMessage.ERROR)
                .timeMessage(new Date())
                .build());
        result.setImage(presenter.getImage());
        result.setName(presenter.getName());
        result.setDescription(presenter.getDescription());
        result.setStatus(presenter.getStatus());
        repository.save(result);
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Equipo Actualizado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity deleteTeam(String teamId) {
        repository.deleteById(UUID.fromString(teamId));
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Equipo Eliminado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public TeamPresenter buildPresenterTeamEntity(Team entity) {
        return TeamPresenter.builder()
                .id(entity.getId().toString())
                .createdDate(entity.getCreatedDate())
                .image(entity.getImage())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public Team buildEntityTeamPresenter(TeamPresenter presenter) {
        return Team.builder()
                .id(presenter.getId() != null ? UUID.fromString(presenter.getId()): null)
                .createdDate(presenter.getCreatedDate() != null ? presenter.getCreatedDate() : new Date())
                .image(presenter.getImage())
                .name(presenter.getName())
                .description(presenter.getDescription())
                .status(presenter.getStatus())
                .build();
    }
}
