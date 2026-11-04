-- V5: Music schema — chords and positions

CREATE TABLE music.chords (
    id           UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_chords PRIMARY KEY,
    name         NVARCHAR(50)     NOT NULL,
    guitar_type  NVARCHAR(20)     NOT NULL
                     CONSTRAINT ck_chords_guitar_type CHECK (guitar_type IN ('ACOUSTIC','CLASSICAL')),
    description  NVARCHAR(1000),
    created_at   DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_chords_name_guitar_type UNIQUE (name, guitar_type)
);

CREATE TABLE music.chord_positions (
    id              UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID() CONSTRAINT pk_chord_positions PRIMARY KEY,
    chord_id        UNIQUEIDENTIFIER NOT NULL,
    position_number INT              NOT NULL,
    base_fret       INT              NOT NULL DEFAULT 1
                        CONSTRAINT ck_chord_positions_base_fret CHECK (base_fret >= 1),
    fingers_json    NVARCHAR(2000)   NOT NULL,
    created_at      DATETIME2(3)     NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_chord_positions_chord_pos UNIQUE (chord_id, position_number),
    CONSTRAINT fk_chord_positions_chord FOREIGN KEY (chord_id) REFERENCES music.chords(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX ix_chords_guitar_type ON music.chords(guitar_type);
CREATE INDEX ix_chord_positions_chord_id ON music.chord_positions(chord_id);
