package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.Journey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JourneyRepository extends CrudRepository<Journey, UUID> {

    @Query("SELECT j FROM Journey j " +
            "WHERE ((cast(UPPER(j.name) as string) like UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(j.name as string))))) " +
            "AND j.status = 'ACT'"
    )
    Page<Journey> findByFilters(String mainFilter, Pageable pageable);
}
