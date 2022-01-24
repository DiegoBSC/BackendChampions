package com.sport.system.play.champion.championservice.entity;

import com.sport.system.play.champion.championservice.enums.EnumStatusMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cam_matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private Date createdDate;
    @NotNull
    private Date matchDate;
    @NotNull
    private String matchTime;
    @NotNull
    private BigDecimal localGoals;
    @NotNull
    private BigDecimal visitGoals;
    @NotNull
    private String location;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusMatch status;

    @OneToOne
    @JoinColumn(name = "local_team_id", referencedColumnName = "id")
    private Team localTeam;

    @OneToOne
    @JoinColumn(name = "visit_team_id", referencedColumnName = "id")
    private Team visitTeam;

    @ManyToOne
    @JoinColumn(name="journey_id", nullable=false)
    private Journey journey;

    @OneToOne(mappedBy = "match")
    private GoalMatch goalMatch;

}

