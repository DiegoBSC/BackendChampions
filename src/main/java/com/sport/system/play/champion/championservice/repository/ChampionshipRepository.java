package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.Championship;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChampionshipRepository extends CrudRepository<Championship, UUID> {

    List<Championship> findByStatus(EnumStatusGeneral status);
}
