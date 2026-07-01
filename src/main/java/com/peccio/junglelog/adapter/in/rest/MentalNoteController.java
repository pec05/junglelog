package com.peccio.junglelog.adapter.in.rest;

import com.peccio.junglelog.adapter.in.rest.dto.CreateMentalNoteRequest;
import com.peccio.junglelog.adapter.in.rest.dto.MentalNoteDto;
import com.peccio.junglelog.application.service.MentalNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games/{gameId}/mental-note")
@RequiredArgsConstructor
public class MentalNoteController {

    private final MentalNoteService mentalNoteService;

    @GetMapping
    public MentalNoteDto get(@PathVariable UUID gameId) {
        return mentalNoteService.findByGame(gameId);
    }

    @PutMapping
    public MentalNoteDto upsert(@PathVariable UUID gameId,
                                @Valid @RequestBody CreateMentalNoteRequest req) {
        return mentalNoteService.upsert(gameId, req);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID gameId) {
        mentalNoteService.delete(gameId);
    }
}
