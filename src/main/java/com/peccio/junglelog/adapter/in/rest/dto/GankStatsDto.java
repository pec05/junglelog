package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.GankResult;
import com.peccio.junglelog.domain.model.Lane;

import java.util.Map;

public record GankStatsDto(
        long total,
        long kills,
        long flashesBurned,
        long wasted,
        Map<Lane, Long> ganksByLane,
        Map<GankResult, Double> avgMinuteByResult,
        double successRateUnwarded
) {
}
