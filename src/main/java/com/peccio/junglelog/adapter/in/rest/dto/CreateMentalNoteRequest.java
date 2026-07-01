package com.peccio.junglelog.adapter.in.rest.dto;

import com.peccio.junglelog.domain.model.MentalTag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateMentalNoteRequest(
        @Min(1) @Max(5) int mood,
        MentalTag tag,
        @Size(max = 500) String freeText,
        @Size(max = 200) String focusArea
) {
}
