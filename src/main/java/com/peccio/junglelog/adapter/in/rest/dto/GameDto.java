package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.GameResult;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameDto(
        UUID id,
        String matchId,
        String champion,
        String patch,
        GameResult result,
        int lpChange,
        int durationSeconds,
        int kills,
        int deaths,
        int assists,
        LocalDateTime playedAt,
        boolean importedFromRiot
) {
}
