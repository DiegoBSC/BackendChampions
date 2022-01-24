package com.sport.system.play.champion.championservice.entity;

import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cam_championships")
public class Championship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private Date createdDate;
    private String image;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cam_championships_teams", joinColumns= @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams;
    @OneToMany(mappedBy="championship")
    private Set<Journey> journeys;
}
