package com.sport.system.play.champion.championservice.security.presenter;

import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionsPresenter {

    private UUID id;
    @NotNull
    private String name;
    private EnumStatusGeneral status;
}
