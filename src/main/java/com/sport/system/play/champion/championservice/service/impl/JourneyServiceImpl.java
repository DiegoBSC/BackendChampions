package com.sport.system.play.champion.championservice.service.impl;

import com.sport.system.play.champion.championservice.entity.Championship;
import com.sport.system.play.champion.championservice.entity.Journey;
import com.sport.system.play.champion.championservice.enums.TypeMessage;
import com.sport.system.play.champion.championservice.presentation.presenter.*;
import com.sport.system.play.champion.championservice.repository.JourneyRepository;
import com.sport.system.play.champion.championservice.service.JourneyService;
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
public class JourneyServiceImpl implements JourneyService {
    @Autowired
    public JourneyRepository journeyRepository;

    @Override
    public ResponseEntity save(JourneyPresenter presenter) {
        journeyRepository.save(buildEntityJourneyPresenter(presenter));
        return ResponseEntity.status(HttpStatus.CREATED).body(MessagePresenter.builder()
                .timeMessage(new Date())
                .typeMessage(TypeMessage.OK)
                .message("Jornada Creada")
                .build());
    }

    @Override
    public ResponseEntity getJourneyActive(Integer page, Integer size, String mainFilter) {

        Pageable paging = PageRequest.of(page, size, Sort.by("name"));

        Page<Journey> result = journeyRepository.findByFilters(mainFilter, paging);

        Set<JourneyPresenter> resultPresenter =
                result.getContent().stream().map(this::buildPresenterJourneyEntity).collect(Collectors.toSet());
        return ResponseEntity.ok().body(Pager.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .data(resultPresenter)
                .build());
    }

    @Override
    public ResponseEntity updateJourney(JourneyPresenter presenter) {
        Journey result = journeyRepository.findById(UUID.fromString(presenter.getId())).orElse(null);
        if(result == null )
            return ResponseEntity.badRequest().body(MessagePresenter.builder()
                            .message("Jornada no encontrada")
                            .typeMessage(TypeMessage.ERROR)
                            .timeMessage(new Date())
                    .build());

        result.setName(presenter.getName());
        result.setStartDate(presenter.getStartDate());
        result.setEndDate(presenter.getEndDate());
        result.setStatus(presenter.getStatus());
        journeyRepository.save(result);
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Jornada Actualizada")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public ResponseEntity deleteJourney(String journeyId) {
        journeyRepository.deleteById(UUID.fromString(journeyId));
        return ResponseEntity.ok().body(MessagePresenter.builder()
                .message("Jornada Eliminada")
                .typeMessage(TypeMessage.OK)
                .timeMessage(new Date())
                .build());
    }

    @Override
    public JourneyPresenter buildPresenterJourneyEntity(Journey entity) {
        return JourneyPresenter.builder()
                .id(entity.getId().toString())
                .createdDate(entity.getCreatedDate())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .name(entity.getName())
                .status(entity.getStatus())
                .championshipPresenter(ChampionshipPresenter.builder()
                        .id(entity.getId().toString())
                        .build())
                .build();
    }

    @Override
    public Journey buildEntityJourneyPresenter(JourneyPresenter presenter) {
        return Journey.builder()
                .id(presenter.getId() != null ? UUID.fromString(presenter.getId()) : null)
                .createdDate(presenter.getCreatedDate() != null ? presenter.getCreatedDate(): new Date())
                .startDate(presenter.getStartDate())
                .endDate(presenter.getEndDate())
                .name(presenter.getName())
                .status(presenter.getStatus())
                .championship(Championship.builder()
                        .id(UUID.fromString(presenter.getChampionshipPresenter().getId())).build())
                .build();
    }
}
