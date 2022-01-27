package com.sport.system.play.champion.championservice.security.presenter;

import com.sport.system.play.champion.championservice.enums.EnumRol;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolPresenter {

    private String id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumRol name;
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    private Set<PermissionsPresenter> permissionsPresenters;
}
