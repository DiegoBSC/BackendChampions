package com.sport.system.play.champion.championservice.entity;

import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cam_teams")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private Date createdDate;
    private String image;
    @NotNull
    private String name;
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;

    @ManyToMany(mappedBy = "teams")
    private List<Championship> Championships;

    @OneToOne(mappedBy = "localTeam")
    private Match match;

    @OneToOne(mappedBy = "visitTeam")
    private Match visitTeam;

    @OneToOne(mappedBy = "localTeam")
    private Match localTeam;

}
