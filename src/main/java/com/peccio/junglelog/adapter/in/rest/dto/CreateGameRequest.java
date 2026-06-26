package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.GameResult;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateGameRequest(
        @NotBlank String champion,
        @NotBlank String patch,
        @NotNull GameResult result,
        int lpChange,
        @Min(0) int durationSeconds,
        @Min(0) int kills,
        @Min(0) int deaths,
        @Min(0) int assists,
        LocalDateTime playedAt
) {
}
