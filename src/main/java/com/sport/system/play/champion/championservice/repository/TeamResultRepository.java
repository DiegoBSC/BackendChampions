package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.TeamResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamResultRepository extends CrudRepository<TeamResult, UUID> {
}
