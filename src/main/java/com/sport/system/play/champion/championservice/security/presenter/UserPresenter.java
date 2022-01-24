package com.sport.system.play.champion.championservice.security.presenter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sport.system.play.champion.championservice.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPresenter {
    private UUID id;
    @NotBlank
    private String username;
    @Email
    private String email;
    private String password;
    private EnumStatusGeneral status;
    @NotNull
    private Set<RolPresenter> rolesPresenter;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate;
}