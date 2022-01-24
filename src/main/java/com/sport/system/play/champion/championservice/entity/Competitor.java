package com.sport.system.play.champion.championservice.entity;

import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import com.sport.system.play.champion.championservice.enums.EnumTypeCompetitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cam_competitors")
public class Competitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private Date createdDate;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String numberCompetitor;
    @NotNull
    private String dni;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumTypeCompetitor typeCompetitor;
    private String image;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;
    @OneToOne(mappedBy = "competitor")
    private GoalMatch goalMatch;

}
