package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.GoalMatch;
import com.sport.system.play.champion.championservice.entity.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoalMatchRepository extends CrudRepository<GoalMatch, UUID> {

    List<GoalMatch> findByMatch(Match match);
}
