-- V23: Add chord category column and seed more chords (major, minor, 7th, suspended).
-- All text literals use N'' so Vietnamese diacritics are preserved.
--
-- IMPORTANT: GO batch separators are required between the ALTER TABLE that adds
-- the `category` column and the DML that references it. SQL Server compiles each
-- batch as a unit, so a column added by ALTER TABLE is not visible to subsequent
-- statements in the same batch without GO.

-- 1. Add category column (nullable first so existing rows can be populated).
ALTER TABLE music.chords ADD category NVARCHAR(20) NULL;
GO

-- 2. Categorize existing seeded chords.
UPDATE music.chords SET category = 'MAJOR' WHERE name IN ('C', 'G', 'D', 'A', 'E', 'F', 'Cadd9');
UPDATE music.chords SET category = 'MINOR' WHERE name IN ('Am', 'Em', 'Dm', 'Bm', 'F#m');
UPDATE music.chords SET category = 'DOMINANT_7' WHERE name = 'G7';
UPDATE music.chords SET category = 'MINOR_7' WHERE name = 'Em7';
UPDATE music.chords SET category = 'SUSPENDED' WHERE name IN ('Dsus4', 'A7sus4');
-- Safety: default any remaining NULLs to MAJOR before making NOT NULL.
UPDATE music.chords SET category = 'MAJOR' WHERE category IS NULL;
GO

-- 3. Make category NOT NULL and constrain valid values.
ALTER TABLE music.chords ALTER COLUMN category NVARCHAR(20) NOT NULL;
ALTER TABLE music.chords
    ADD CONSTRAINT ck_chords_category CHECK (category IN ('MAJOR','MINOR','DOMINANT_7','MAJOR_7','MINOR_7','SUSPENDED'));

-- Index for category-based lookups.
CREATE INDEX ix_chords_category ON music.chords(category);
GO

-- ── DOMINANT 7 ──
DECLARE @c7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@c7_id, 'C7', 'ACOUSTIC', 'DOMINANT_7', N'Hợp âm C7 — dominant seventh, âm thanh blues/folk.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @c7_id, 1, 1,
    N'[{"string":5,"fret":3,"finger":3},{"string":4,"fret":2,"finger":2},{"string":3,"fret":3,"finger":4},{"string":2,"fret":1,"finger":1},{"string":1,"fret":0},{"string":6,"fret":-1}]');

DECLARE @d7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@d7_id, 'D7', 'ACOUSTIC', 'DOMINANT_7', N'Hợp âm D7 — dominant seventh, dùng nhiều trong blues và country.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @d7_id, 1, 1,
    N'[{"string":4,"fret":0},{"string":3,"fret":2,"finger":1},{"string":2,"fret":1,"finger":2},{"string":1,"fret":2,"finger":3},{"string":5,"fret":-1},{"string":6,"fret":-1}]');

DECLARE @a7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@a7_id, 'A7', 'ACOUSTIC', 'DOMINANT_7', N'Hợp âm A7 — dominant seventh, âm risoluti mạnh, hay dùng cuối giai điệu.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @a7_id, 1, 1,
    N'[{"string":5,"fret":0},{"string":4,"fret":2,"finger":1},{"string":3,"fret":0},{"string":2,"fret":2,"finger":2},{"string":1,"fret":0},{"string":6,"fret":-1}]');

DECLARE @e7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@e7_id, 'E7', 'ACOUSTIC', 'DOMINANT_7', N'Hợp âm E7 — dominant seventh, nền tảng của blues.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @e7_id, 1, 1,
    N'[{"string":6,"fret":0},{"string":5,"fret":2,"finger":1},{"string":4,"fret":0},{"string":3,"fret":1,"finger":2},{"string":2,"fret":0},{"string":1,"fret":0}]');

-- ── MAJOR 7 ──
DECLARE @cmaj7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@cmaj7_id, 'Cmaj7', 'ACOUSTIC', 'MAJOR_7', N'Hợp âm Cmaj7 — major seventh, âm thanh jazz, êm ái.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @cmaj7_id, 1, 1,
    N'[{"string":5,"fret":3,"finger":3},{"string":4,"fret":2,"finger":2},{"string":3,"fret":0},{"string":2,"fret":0},{"string":1,"fret":0},{"string":6,"fret":-1}]');

DECLARE @gmaj7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@gmaj7_id, 'Gmaj7', 'ACOUSTIC', 'MAJOR_7', N'Hợp âm Gmaj7 — major seventh, âm thanh mở và mát.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @gmaj7_id, 1, 1,
    N'[{"string":6,"fret":3,"finger":2},{"string":5,"fret":2,"finger":1},{"string":4,"fret":0},{"string":3,"fret":0},{"string":2,"fret":0},{"string":1,"fret":2,"finger":3}]');

DECLARE @dmaj7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@dmaj7_id, 'Dmaj7', 'ACOUSTIC', 'MAJOR_7', N'Hợp âm Dmaj7 — major seventh, nhẹ nhàng, dùng trong pop/jazz.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @dmaj7_id, 1, 1,
    N'[{"string":4,"fret":0},{"string":3,"fret":2,"finger":1},{"string":2,"fret":2,"finger":2},{"string":1,"fret":2,"finger":3},{"string":5,"fret":-1},{"string":6,"fret":-1}]');

-- ── MINOR 7 ──
DECLARE @am7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@am7_id, 'Am7', 'ACOUSTIC', 'MINOR_7', N'Hợp âm Am7 — minor seventh, nhẹ nhàng hơn Am.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @am7_id, 1, 1,
    N'[{"string":5,"fret":0},{"string":4,"fret":2,"finger":1},{"string":3,"fret":0},{"string":2,"fret":1,"finger":2},{"string":1,"fret":0},{"string":6,"fret":-1}]');

DECLARE @dm7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@dm7_id, 'Dm7', 'ACOUSTIC', 'MINOR_7', N'Hợp âm Dm7 — minor seventh, buồn ngọt, hay dùng trong ballad.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @dm7_id, 1, 1,
    N'[{"string":4,"fret":0},{"string":3,"fret":2,"finger":1},{"string":2,"fret":1,"finger":2},{"string":1,"fret":1,"finger":3},{"string":5,"fret":-1},{"string":6,"fret":-1}]');

-- ── SUSPENDED ──
DECLARE @asus4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@asus4_id, 'Asus4', 'ACOUSTIC', 'SUSPENDED', N'Hợp âm Asus4 — giữ đường trước khi xuống A, dùng nhiều trong pop.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @asus4_id, 1, 1,
    N'[{"string":5,"fret":0},{"string":4,"fret":2,"finger":1},{"string":3,"fret":2,"finger":2},{"string":2,"fret":3,"finger":3},{"string":1,"fret":0},{"string":6,"fret":-1}]');

DECLARE @esus4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, category, description)
VALUES (@esus4_id, 'Esus4', 'ACOUSTIC', 'SUSPENDED', N'Hợp âm Esus4 — giữ đường trước khi xuống E.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @esus4_id, 1, 1,
    N'[{"string":6,"fret":0},{"string":5,"fret":2,"finger":1},{"string":4,"fret":2,"finger":2},{"string":3,"fret":2,"finger":3},{"string":2,"fret":0},{"string":1,"fret":0}]');
