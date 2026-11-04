package com.hguitar.music.infrastructure.persistence.entity;

import com.hguitar.onboarding.domain.model.GuitarType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chords", schema = "music")
public class ChordEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "guitar_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private GuitarType guitarType;

    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "chord", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("positionNumber ASC")
    private List<ChordPositionEntity> positions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected ChordEntity() {}

    public ChordEntity(UUID id, String name, GuitarType guitarType, String description) {
        this.id = id;
        this.name = name;
        this.guitarType = guitarType;
        this.description = description;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public GuitarType getGuitarType() { return guitarType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<ChordPositionEntity> getPositions() { return positions; }
    public Instant getCreatedAt() { return createdAt; }
}
