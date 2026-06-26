package com.peccio.junglelog.adapter.in.rest;

import com.peccio.junglelog.adapter.in.rest.dto.CreateGankRequest;
import com.peccio.junglelog.adapter.in.rest.dto.GankEntryDto;
import com.peccio.junglelog.adapter.in.rest.dto.GankStatsDto;
import com.peccio.junglelog.application.service.GankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games/{gameId}/ganks")
@RequiredArgsConstructor
public class GankController {

    private final GankService gankService;

    @GetMapping
    public List<GankEntryDto> getAll(@PathVariable UUID gameId) {
        return gankService.findByGame(gameId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GankEntryDto create(@PathVariable UUID gameId,
                               @Valid @RequestBody CreateGankRequest req) {
        return gankService.create(gameId, req);
    }

    @PutMapping("/{gankId}")
    public GankEntryDto update(@PathVariable UUID gameId,
                               @PathVariable UUID gankId,
                               @Valid @RequestBody CreateGankRequest req) {
        return gankService.update(gankId, req);
    }

    @DeleteMapping("/{gankId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID gameId,
                       @PathVariable UUID gankId) {
        gankService.delete(gankId);
    }

    @GetMapping("/stats")
    public GankStatsDto getStats(
            @PathVariable UUID gameId,
            @RequestParam(defaultValue = "20") int lastGames) {
        return gankService.computeStats(lastGames);
    }
}
