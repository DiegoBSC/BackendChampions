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
@Table(name = "cam_journeys")
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private Date createdDate;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    @ManyToOne
    @JoinColumn(name="championship_id", nullable=false)
    private Championship championship;
    @OneToMany(mappedBy="journey")
    private Set<Match> matches;
}
