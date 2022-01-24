package com.sport.system.play.champion.championservice.entity;

import com.sport.system.play.champion.championservice.enums.EnumResult;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cam_team_result")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class TeamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumResult result;

    @NotNull
    private BigDecimal points;

    @OneToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

}
