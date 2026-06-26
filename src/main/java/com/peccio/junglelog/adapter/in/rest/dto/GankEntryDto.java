package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.GankResult;
import com.peccio.junglelog.domain.model.Lane;

import java.util.UUID;

public record GankEntryDto(
        UUID id,
        Lane lane,
        int minuteOfGame,
        GankResult result,
        boolean enemyWardedBefore,
        boolean preMadeWithLaner,
        int posX,
        int posY,
        String notes
) {
}
