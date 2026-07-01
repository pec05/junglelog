package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.MentalTag;

import java.util.UUID;

public record MentalNoteDto(
        UUID id,
        int mood,
        MentalTag tag,
        String freeText,
        String focusArea
) {
}
