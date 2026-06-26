package com.peccio.junglelog.adapter.out.persistence;

import com.peccio.junglelog.domain.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    boolean existsByMatchId(String matchId);
}
