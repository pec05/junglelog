package com.peccio.junglelog.adapter.in.rest;

import com.peccio.junglelog.adapter.in.rest.dto.CreateGameRequest;
import com.peccio.junglelog.adapter.in.rest.dto.GameDto;
import com.peccio.junglelog.application.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<GameDto> getAll() {
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public GameDto getById(@PathVariable UUID id) {
        return gameService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto create(@Valid @RequestBody CreateGameRequest req) {
        return gameService.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        gameService.delete(id);
    }
}
