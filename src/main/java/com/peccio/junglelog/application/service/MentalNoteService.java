package com.peccio.junglelog.application.service;

import com.peccio.junglelog.adapter.in.rest.dto.CreateMentalNoteRequest;
import com.peccio.junglelog.adapter.in.rest.dto.MentalNoteDto;
import com.peccio.junglelog.adapter.out.persistence.GameRepository;
import com.peccio.junglelog.adapter.out.persistence.MentalNoteRepository;
import com.peccio.junglelog.domain.model.MentalNote;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentalNoteService {

    private final MentalNoteRepository mentalNoteRepository;
    private final GameRepository gameRepository;

    public MentalNoteDto findByGame(UUID gameId) {
        return mentalNoteRepository.findByGameId(gameId)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "MentalNote not found for game: " + gameId));
    }

    @Transactional
    public MentalNoteDto upsert(UUID gameId, CreateMentalNoteRequest req) {
        var game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found: " + gameId));

        MentalNote note = mentalNoteRepository.findByGameId(gameId)
                .orElse(MentalNote.builder().game(game).build());

        note.setMood(req.mood());
        note.setTag(req.tag());
        note.setFreeText(req.freeText());
        note.setFocusArea(req.focusArea());

        return toDto(mentalNoteRepository.save(note));
    }

    @Transactional
    public void delete(UUID gameId) {
        MentalNote note = mentalNoteRepository.findByGameId(gameId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "MentalNote not found for game: " + gameId));
        mentalNoteRepository.delete(note);
    }

    private MentalNoteDto toDto(MentalNote n) {
        return new MentalNoteDto(
                n.getId(), n.getMood(), n.getTag(),
                n.getFreeText(), n.getFocusArea()
        );
    }
}
