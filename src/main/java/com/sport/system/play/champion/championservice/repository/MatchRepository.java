package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.Journey;
import com.sport.system.play.champion.championservice.entity.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MatchRepository extends CrudRepository<Match, UUID> {

    List<Match> findByJourney(Journey journey);
}
