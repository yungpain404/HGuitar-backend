package com.hguitar.music.infrastructure.persistence.repository;

import com.hguitar.music.domain.model.Chord;
import com.hguitar.music.domain.model.ChordCategory;
import com.hguitar.music.domain.model.ChordPosition;
import com.hguitar.music.domain.repository.ChordRepository;
import com.hguitar.music.infrastructure.persistence.entity.ChordEntity;
import com.hguitar.music.infrastructure.persistence.entity.ChordPositionEntity;
import com.hguitar.onboarding.domain.model.GuitarType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChordRepositoryAdapter implements ChordRepository {

    private final JpaChordRepository jpa;

    public ChordRepositoryAdapter(JpaChordRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Chord save(Chord chord) {
        ChordEntity entity = jpa.findById(chord.getId())
                .orElse(new ChordEntity(chord.getId(), chord.getName(),
                        chord.getGuitarType(), chord.getCategory(), chord.getDescription()));
        entity.setName(chord.getName());
        entity.setDescription(chord.getDescription());
        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Chord> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Chord> findAll() {
        return jpa.findAllByOrderByNameAsc().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Chord> findByGuitarType(GuitarType guitarType) {
        return jpa.findByGuitarTypeOrderByName(guitarType).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Chord> findByCategory(ChordCategory category) {
        return jpa.findByCategoryOrderByName(category).stream().map(this::toDomain).toList();
    }

    private Chord toDomain(ChordEntity e) {
        List<ChordPosition> positions = e.getPositions().stream()
                .map(p -> new ChordPosition(p.getId(), e.getId(), p.getPositionNumber(),
                        p.getBaseFret(), p.getFingersJson(), p.getCreatedAt()))
                .toList();
        return new Chord(e.getId(), e.getName(), e.getGuitarType(), e.getCategory(),
                e.getDescription(), positions, e.getCreatedAt());
    }
}
