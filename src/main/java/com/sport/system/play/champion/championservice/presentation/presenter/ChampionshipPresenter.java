package com.sport.system.play.champion.championservice.presentation.presenter;

import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampionshipPresenter {
    private String id;
    private Date createdDate;
    private String image;
    private Date startDate;
    private Date endDate;
    private String name;
    private String description;
    private EnumStatusGeneral status;
    private Set<TeamPresenter> teamsPresenter;
    private Set<JourneyPresenter> journeysPresenter;
}
