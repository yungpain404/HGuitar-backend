-- V21: Add created_at to song child tables to match JPA entities
ALTER TABLE song.song_translations
    ADD created_at DATETIME2(3) NOT NULL DEFAULT SYSUTCDATETIME();

ALTER TABLE song.song_chords
    ADD created_at DATETIME2(3) NOT NULL DEFAULT SYSUTCDATETIME();

ALTER TABLE song.song_sections
    ADD created_at DATETIME2(3) NOT NULL DEFAULT SYSUTCDATETIME();
