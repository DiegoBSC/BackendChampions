package com.sport.system.play.champion.championservice.presentation.presenter;

import com.sport.system.play.champion.championservice.enums.EnumStatusMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPresenter {

    private String id;
    private Date createdDate;
    private Date matchDate;
    private String matchTime;
    private BigDecimal localGoals;
    private BigDecimal visitGoals;
    private String location;
    private EnumStatusMatch status;
    private TeamPresenter localTeamPresenter;
    private TeamPresenter visitTeamPresenter;
    private JourneyPresenter journeyPresenter;
    private GoalMatchPresenter goalMatchPresenter;
}
