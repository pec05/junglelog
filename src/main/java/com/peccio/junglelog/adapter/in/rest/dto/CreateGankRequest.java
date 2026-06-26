package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.GankResult;
import com.peccio.junglelog.domain.model.Lane;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateGankRequest(
        @NotNull Lane lane,
        @Min(0) @Max(60) int minuteOfGame,
        @NotNull GankResult result,
        boolean enemyWardedBefore,
        boolean preMadeWithLaner,
        @Min(0) @Max(100) int posX,
        @Min(0) @Max(100) int posY,
        @Size(max = 300) String notes
) {
}
