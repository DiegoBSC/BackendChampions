package com.sport.system.play.champion.championservice.presentation.presenter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sport.system.play.champion.championservice.entity.Match;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyPresenter {

    private String id;
    private Date createdDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private String name;
    private EnumStatusGeneral status;
    @NotNull
    private ChampionshipPresenter championshipPresenter;
    private Set<Match> matches;

}
