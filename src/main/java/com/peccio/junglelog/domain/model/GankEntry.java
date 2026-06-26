package com.peccio.junglelog.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "gank_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GankEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lane lane;

    @Column(nullable = false)
    private int minuteOfGame;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GankResult result;

    private boolean enemyWardedBefore;
    private boolean preMadeWithLaner;

    private int posX;   // position sur la minimap (0-100)
    private int posY;

    @Column(length = 300)
    private String notes;

}
