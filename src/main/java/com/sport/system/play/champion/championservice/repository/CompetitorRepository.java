package com.sport.system.play.champion.championservice.repository;

import com.sport.system.play.champion.championservice.entity.Competitor;
import com.sport.system.play.champion.championservice.entity.Journey;
import com.sport.system.play.champion.championservice.enums.EnumTypeCompetitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CompetitorRepository extends CrudRepository<Competitor, UUID> {

    @Query("SELECT c FROM Competitor c " +
            "WHERE ((cast(UPPER(c.name) as string) like UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.name as string))))" +
            " OR (cast(UPPER(c.lastName) as string) like UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.lastName as string))))" +
            " OR (cast(UPPER(c.numberCompetitor) as string) like UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.numberCompetitor as string))))" +
            " OR (cast(UPPER(c.dni) as string) like UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.dni as string))))) " +
            "AND c.status = 'ACT' " +
            "AND (cast(UPPER(c.typeCompetitor) as string) like UPPER(coalesce(cast(CONCAT('%', :typeCompetitor,'%') as string), cast(c.typeCompetitor as string))))"
    )
    Page<Competitor> findByFilters(String mainFilter, String typeCompetitor, Pageable pageable);
}
