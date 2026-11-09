package com.hguitar.music.domain.repository;

import com.hguitar.music.domain.model.Chord;
import com.hguitar.music.domain.model.ChordCategory;
import com.hguitar.onboarding.domain.model.GuitarType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChordRepository {
    Chord save(Chord chord);
    Optional<Chord> findById(UUID id);
    List<Chord> findAll();
    List<Chord> findByGuitarType(GuitarType guitarType);
    List<Chord> findByCategory(ChordCategory category);
}
