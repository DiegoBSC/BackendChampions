package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends CrudRepository<Team, UUID> {

    @Query("SELECT t FROM Team t " +
            "WHERE ((cast(UPPER(t.name) as string) like UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(t.name as string))))) " +
            "AND t.status = 'ACT'"
    )
    Page<Team> findByFilters(String mainFilter, Pageable pageable);
}
