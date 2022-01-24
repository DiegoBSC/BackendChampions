package com.sport.system.play.champion.championservice.presentation.presenter;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Pager {
    private Integer totalPages;
    private Long totalElements;
    private Set data;
}
