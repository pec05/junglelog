package com.peccio.junglelog.application.service;

import com.peccio.junglelog.adapter.in.rest.dto.CreateGameRequest;
import com.peccio.junglelog.adapter.in.rest.dto.GameDto;
import com.peccio.junglelog.adapter.out.persistence.GameRepository;
import com.peccio.junglelog.domain.model.Game;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepo;

    public List<GameDto> findAll() {
        return gameRepo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public GameDto findById(UUID id) {
        return gameRepo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Game not found: " + id));
    }

    @Transactional
    public GameDto create(CreateGameRequest req) {
        Game game = Game.builder()
                .champion(req.champion())
                .patch(req.patch())
                .result(req.result())
                .lpChange(req.lpChange())
                .durationSeconds(req.durationSeconds())
                .kills(req.kills())
                .deaths(req.deaths())
                .assists(req.assists())
                .playedAt(req.playedAt() != null ? req.playedAt() : LocalDateTime.now())
                .importedFromRiot(false)
                .build();

        return toDto(gameRepo.save(game));
    }

    @Transactional
    public void delete(UUID id) {
        if (!gameRepo.existsById(id)) {
            throw new EntityNotFoundException("Game not found: " + id);
        }
        gameRepo.deleteById(id);
    }

    private GameDto toDto(Game g) {
        return new GameDto(
                g.getId(), g.getMatchId(), g.getChampion(), g.getPatch(),
                g.getResult(), g.getLpChange(), g.getDurationSeconds(),
                g.getKills(), g.getDeaths(), g.getAssists(),
                g.getPlayedAt(), g.isImportedFromRiot()
        );
    }
}
