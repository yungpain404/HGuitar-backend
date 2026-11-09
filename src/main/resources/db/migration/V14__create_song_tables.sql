-- V14: Song repertoire (bài hát dễ đánh) — Vietnamese & foreign songs for beginners
-- Schema: song

CREATE SCHEMA song;

CREATE TABLE song.songs (
    id                 UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_songs PRIMARY KEY,
    guitar_type        NVARCHAR(20)     NOT NULL
                        CONSTRAINT ck_songs_guitar_type CHECK (guitar_type IN ('ACOUSTIC','CLASSICAL')),
    difficulty         NVARCHAR(20)     NOT NULL DEFAULT 'BEGINNER'
                        CONSTRAINT ck_songs_difficulty CHECK (difficulty IN ('BEGINNER','INTERMEDIATE','ADVANCED')),
    source             NVARCHAR(20)     NOT NULL
                        CONSTRAINT ck_songs_source CHECK (source IN ('VIETNAMESE','FOREIGN')),
    original_key       NVARCHAR(10)     NULL,             -- e.g. 'C', 'G', 'Am'
    capo_fret          INT              NOT NULL DEFAULT 0 CONSTRAINT ck_songs_capo_fret CHECK (capo_fret >= 0),
    bpm                INT              NULL,
    strumming_pattern_id UNIQUEIDENTIFIER NULL,         -- recommended strumming pattern
    is_formula         BIT              NOT NULL DEFAULT 1, -- 1 = dùng công thức đánh dành cho người mới
    status             NVARCHAR(20)     NOT NULL DEFAULT 'PUBLISHED'
                        CONSTRAINT ck_songs_status CHECK (status IN ('DRAFT','PUBLISHED','ARCHIVED')),
    sort_order         INT              NOT NULL DEFAULT 1,
    created_at         DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at         DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_songs_strumming_pattern FOREIGN KEY (strumming_pattern_id) REFERENCES music.strumming_patterns(id)
);

CREATE TABLE song.song_translations (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_song_translations PRIMARY KEY,
    song_id     UNIQUEIDENTIFIER NOT NULL,
    language    NVARCHAR(5)      NOT NULL,
    title       NVARCHAR(500)    NOT NULL,
    artist      NVARCHAR(500)    NOT NULL,
    description NVARCHAR(2000),
    CONSTRAINT uq_song_translations_song_lang UNIQUE (song_id, language),
    CONSTRAINT fk_song_translations_song FOREIGN KEY (song_id) REFERENCES song.songs(id) ON DELETE CASCADE
);

CREATE TABLE song.song_chords (
    id          UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_song_chords PRIMARY KEY,
    song_id     UNIQUEIDENTIFIER NOT NULL,
    chord_id    UNIQUEIDENTIFIER NOT NULL,
    sort_order  INT              NOT NULL DEFAULT 1,
    is_required BIT              NOT NULL DEFAULT 1,   -- 1 = hợp âm bắt buộc phải biết để chơi bài này
    CONSTRAINT uq_song_chords_song_chord UNIQUE (song_id, chord_id),
    CONSTRAINT fk_song_chords_song FOREIGN KEY (song_id) REFERENCES song.songs(id) ON DELETE CASCADE,
    CONSTRAINT fk_song_chords_chord FOREIGN KEY (chord_id) REFERENCES music.chords(id)
);

CREATE TABLE song.song_sections (
    id                 UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_song_sections PRIMARY KEY,
    song_id            UNIQUEIDENTIFIER NOT NULL,
    section_type       NVARCHAR(20)     NOT NULL
                        CONSTRAINT ck_song_sections_type CHECK (section_type IN ('INTRO','VERSE','CHORUS','BRIDGE','OUTRO')),
    section_order      INT              NOT NULL DEFAULT 1,
    name               NVARCHAR(200),                   -- e.g. N'Verse 1', N'Nhạc đề'
    chord_progression  NVARCHAR(1000) NOT NULL,         -- e.g. N'C | G | Am | F'
    strumming_pattern_id UNIQUEIDENTIFIER NULL,        -- override strumming pattern for this section
    CONSTRAINT fk_song_sections_song FOREIGN KEY (song_id) REFERENCES song.songs(id) ON DELETE CASCADE,
    CONSTRAINT fk_song_sections_strumming FOREIGN KEY (strumming_pattern_id) REFERENCES music.strumming_patterns(id)
);

CREATE INDEX ix_songs_source_difficulty ON song.songs(source, difficulty);
CREATE INDEX ix_songs_status ON song.songs(status);
CREATE INDEX ix_song_chords_song_id ON song.song_chords(song_id);
CREATE INDEX ix_song_sections_song_id ON song.song_sections(song_id);
