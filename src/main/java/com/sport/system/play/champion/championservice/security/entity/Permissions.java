package com.sport.system.play.champion.championservice.security.entity;

import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Table(name = "sec_permissions")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;

    @ManyToMany(mappedBy = "permissions")
    private List<Rol> roles;
}
