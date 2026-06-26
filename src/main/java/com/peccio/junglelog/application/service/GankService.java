package com.peccio.junglelog.application.service;

import com.peccio.junglelog.adapter.in.rest.dto.CreateGankRequest;
import com.peccio.junglelog.adapter.in.rest.dto.GankEntryDto;
import com.peccio.junglelog.adapter.in.rest.dto.GankStatsDto;
import com.peccio.junglelog.adapter.out.persistence.GameRepository;
import com.peccio.junglelog.adapter.out.persistence.GankEntryRepository;
import com.peccio.junglelog.domain.model.Game;
import com.peccio.junglelog.domain.model.GankEntry;
import com.peccio.junglelog.domain.model.GankResult;
import com.peccio.junglelog.domain.model.Lane;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GankService {

    private final GankEntryRepository gankRepo;
    private final GameRepository gameRepo;

    public List<GankEntryDto> findByGame(UUID gameId) {
        return gankRepo.findByGameId(gameId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public GankEntryDto create(UUID gameId, CreateGankRequest req) {
        Game game = gameRepo.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found: " + gameId));

        GankEntry entry = GankEntry.builder()
                .game(game)
                .lane(req.lane())
                .minuteOfGame(req.minuteOfGame())
                .result(req.result())
                .enemyWardedBefore(req.enemyWardedBefore())
                .preMadeWithLaner(req.preMadeWithLaner())
                .posX(req.posX())
                .posY(req.posY())
                .notes(req.notes())
                .build();

        return toDto(gankRepo.save(entry));
    }

    @Transactional
    public GankEntryDto update(UUID gankId, CreateGankRequest req) {
        GankEntry entry = gankRepo.findById(gankId)
                .orElseThrow(() -> new EntityNotFoundException("Gank not found: " + gankId));

        entry.setLane(req.lane());
        entry.setMinuteOfGame(req.minuteOfGame());
        entry.setResult(req.result());
        entry.setEnemyWardedBefore(req.enemyWardedBefore());
        entry.setPreMadeWithLaner(req.preMadeWithLaner());
        entry.setPosX(req.posX());
        entry.setPosY(req.posY());
        entry.setNotes(req.notes());

        return toDto(gankRepo.save(entry));
    }

    @Transactional
    public void delete(UUID gankId) {
        if (!gankRepo.existsById(gankId)) {
            throw new EntityNotFoundException("Gank not found: " + gankId);
        }
        gankRepo.deleteById(gankId);
    }

    public GankStatsDto computeStats(int lastGames) {
        List<GankEntry> ganks = gankRepo.findRecentGanks(lastGames);

        long total      = ganks.size();
        long kills      = count(ganks, GankResult.KILL);
        long flashes    = count(ganks, GankResult.FLASH_BURNED);
        long wasted     = count(ganks, GankResult.NOTHING);

        Map<Lane, Long> byLane = ganks.stream()
                .collect(Collectors.groupingBy(GankEntry::getLane, Collectors.counting()));

        Map<GankResult, Double> avgMinute = ganks.stream()
                .collect(Collectors.groupingBy(GankEntry::getResult,
                        Collectors.averagingInt(GankEntry::getMinuteOfGame)));

        List<GankEntry> unwarded = ganks.stream()
                .filter(g -> !g.isEnemyWardedBefore()).toList();
        long unwSucc = unwarded.stream()
                .filter(g -> g.getResult() != GankResult.NOTHING).count();
        double successRateUnwarded = unwarded.isEmpty()
                ? 0.0
                : (unwSucc * 100.0) / unwarded.size();

        return new GankStatsDto(total, kills, flashes, wasted,
                byLane, avgMinute, successRateUnwarded);
    }

    private long count(List<GankEntry> ganks, GankResult result) {
        return ganks.stream().filter(g -> g.getResult() == result).count();
    }

    private GankEntryDto toDto(GankEntry g) {
        return new GankEntryDto(
                g.getId(), g.getLane(), g.getMinuteOfGame(), g.getResult(),
                g.isEnemyWardedBefore(), g.isPreMadeWithLaner(),
                g.getPosX(), g.getPosY(), g.getNotes()
        );
    }

}
