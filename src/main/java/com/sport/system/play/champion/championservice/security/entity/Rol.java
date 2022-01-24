package com.sport.system.play.champion.championservice.security.entity;

import com.sport.system.play.champion.championservice.enums.EnumRol;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name = "sec_roles")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumRol name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name = "sec_rol_permissions",
            joinColumns = @JoinColumn(
                    name = "rol_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    private Set<Permissions> permissions;
}