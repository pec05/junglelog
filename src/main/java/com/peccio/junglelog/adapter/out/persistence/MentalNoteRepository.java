package com.peccio.junglelog.adapter.out.persistence;

import com.peccio.junglelog.domain.model.MentalNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MentalNoteRepository extends JpaRepository<MentalNote, UUID> {

    Optional<MentalNote> findByGameId(UUID gameId);
}
