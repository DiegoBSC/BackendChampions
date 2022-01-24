package com.sport.system.play.champion.championservice.presentation.presenter;

import com.sport.system.play.champion.championservice.entity.Championship;
import com.sport.system.play.champion.championservice.entity.Match;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamPresenter {

    private String id;
    private Date createdDate;
    private String image;
    private String name;
    private String description;
    private EnumStatusGeneral status;
    private List<Championship> Championships;
    private Match match;
    private Match visitTeam;
}
