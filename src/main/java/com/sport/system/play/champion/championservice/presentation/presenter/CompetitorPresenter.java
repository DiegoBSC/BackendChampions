package com.sport.system.play.champion.championservice.presentation.presenter;

import com.sport.system.play.champion.championservice.entity.GoalMatch;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import com.sport.system.play.champion.championservice.enums.EnumTypeCompetitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitorPresenter {

    private String id;
    private Date createdDate;
    private String name;
    private String lastName;
    private String numberCompetitor;
    private String dni;
    private EnumTypeCompetitor typeCompetitor;
    private String image;
    private EnumStatusGeneral status;
    @NotNull
    private TeamPresenter teamPresenter;
    private GoalMatch goalMatch;
}
