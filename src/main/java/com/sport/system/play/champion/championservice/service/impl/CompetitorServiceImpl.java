package com.sport.system.play.champion.championservice.service.impl;

import com.sport.system.play.champion.championservice.entity.Competitor;
import com.sport.system.play.champion.championservice.entity.Team;
import com.sport.system.play.champion.championservice.enums.TypeMessage;
import com.sport.system.play.champion.championservice.presentation.presenter.CompetitorPresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.MessagePresenter;
import com.sport.system.play.champion.championservice.presentation.presenter.Pager;
import com.sport.system.play.champion.championservice.presentation.presenter.TeamPresenter;
import com.sport.system.play.champion.championservice.repository.CompetitorRepository;
import com.sport.system.play.champion.championservice.service.CompetitorService;
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
public class CompetitorServiceImpl implements CompetitorService {
    @Autowired
    public CompetitorRepository competitorRepository;

    @Override
    public ResponseEntity save(CompetitorPresenter presenter) {
        competitorRepository.save(buildEntityCompetitorPresenter(presenter));
        return ResponseEntity.status(HttpStatus.CREATED).body(MessagePresenter.builder()
                .timeMessage(new Date())
                .typeMessage(TypeMessage.OK)
                .message("Jugador Creado")
                .build());
    }

    @Override
    public ResponseEntity getCompetitorActive(Integer page, Integer size, String mainFilter, String typeCompetitor) {
        Pageable paging = PageRequest.of(page, size, Sort.by("name"));

        Page<Competitor> result = competitorRepository.findByFilters(mainFilter, typeCompetitor, paging);

        Set<CompetitorPresenter> resultPresenter =
                result.getContent().stream().map(this::buildPresenterCompetitorEntity).collect(Collectors.toSet());
        return ResponseEntity.ok().body(Pager.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .data(resultPresenter)
                .build());
    }

    @Override
    public ResponseEntity updateCompetitor(CompetitorPresenter presenter) {
        Competitor result = competitorRepository.findById(UUID.fromString(presenter.getId())).orElse(null);
        if(result == null )
            return ResponseEntity.badRequest().body(MessagePresenter.builder()
                    .message("Jugador no encontrado")
                    .typeMessage(TypeMessage.ERROR)
                    .timeMessage(new Date())
                    .build());

        result.setName(presenter.getName());
        result.setLastName(presenter.getLastName());
        result.setDni(presenter.getDni());
        result.setStatus(presenter.getStatus());
        result.setNumberCompetitor(presenter.getNumberCompetitor());
        competitorRepository.save(result);
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Jugador Actualizado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity deleteCompetitor(String competitorId) {
        competitorRepository.deleteById(UUID.fromString(competitorId));
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Jugador Eliminado")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public CompetitorPresenter buildPresenterCompetitorEntity(Competitor entity) {
        return CompetitorPresenter.builder()
                .id(entity.getId().toString())
                .createdDate(entity.getCreatedDate())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .numberCompetitor(entity.getNumberCompetitor())
                .dni(entity.getDni())
                .typeCompetitor(entity.getTypeCompetitor())
                .image(entity.getImage())
                .status(entity.getStatus())
                .teamPresenter(TeamPresenter.builder()
                        .id(entity.getTeam().getId().toString())
                        .build())
                .build();
    }

    @Override
    public Competitor buildEntityCompetitorPresenter(CompetitorPresenter presenter) {
        return Competitor.builder()
                .id(presenter.getId() != null ? UUID.fromString(presenter.getId()) : null)
                .createdDate(presenter.getCreatedDate() != null ? presenter.getCreatedDate() : new Date())
                .name(presenter.getName())
                .lastName(presenter.getLastName())
                .dni(presenter.getDni())
                .numberCompetitor(presenter.getNumberCompetitor())
                .typeCompetitor(presenter.getTypeCompetitor())
                .image(presenter.getImage())
                .status(presenter.getStatus())
                .team(Team.builder().id(UUID.fromString(presenter.getTeamPresenter().getId())).build())
                .build();
    }
}
