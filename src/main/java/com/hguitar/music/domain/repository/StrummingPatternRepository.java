package com.hguitar.music.domain.repository;

import com.hguitar.music.domain.model.StrummingPattern;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StrummingPatternRepository {
    StrummingPattern save(StrummingPattern pattern);
    Optional<StrummingPattern> findById(UUID id);
    List<StrummingPattern> findAll();
    List<StrummingPattern> findByGuitarType(String guitarType);
    List<StrummingPattern> findByDifficulty(String difficulty);
    List<StrummingPattern> findPublished();
}
