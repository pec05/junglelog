package com.peccio.junglelog.adapter.out.persistence;

import com.peccio.junglelog.domain.model.Game;
import com.peccio.junglelog.domain.model.GankEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GankEntryRepository extends JpaRepository<GankEntry, UUID> {
    List<GankEntry> findByGameId(UUID gameId);

    @Query("""
        SELECT g FROM GankEntry g
        JOIN g.game gm
        ORDER BY gm.playedAt DESC
        LIMIT :limit
    """)
    List<GankEntry> findRecentGanks(int limit);
}
