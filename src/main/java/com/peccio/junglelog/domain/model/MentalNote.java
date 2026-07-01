package com.peccio.junglelog.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "mental_notes")
@Getter  @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalNote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "game_id", unique = true)
    private Game game;

    @Column(nullable = false)
    private int mood;

    @Enumerated(EnumType.STRING)
    private MentalTag tag;

    @Column(length = 500)
    private String freeText;

    @Column(length = 200)
    private String focusArea;
}
