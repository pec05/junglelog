package com.peccio.junglelog.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String matchId;
    private String champion;
    private String patch;

    @Enumerated(EnumType.STRING)
    private GameResult result;

    private int lpChange;
    private int durationSeconds;
    private int kills;
    private int deaths;
    private int assists;

    private LocalDateTime playedAt;

    private boolean importedFromRiot;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GankEntry> ganks = new ArrayList<>();
}
