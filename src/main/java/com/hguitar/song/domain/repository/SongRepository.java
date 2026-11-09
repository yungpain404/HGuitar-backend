package com.hguitar.song.domain.repository;

import com.hguitar.song.domain.model.Song;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongRepository {
    Song save(Song song);
    Optional<Song> findById(UUID id);
    List<Song> findAll();
    List<Song> findBySourceAndDifficulty(String source, String difficulty);
    List<Song> findBySource(String source);
    List<Song> findByDifficulty(String difficulty);
    List<Song> findPublished();
}
