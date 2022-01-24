package com.sport.system.play.champion.championservice.presentation.presenter;

import com.sport.system.play.champion.championservice.enums.TypeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessagePresenter {
    private TypeMessage typeMessage;
    private String message;
    private Date timeMessage;
}
