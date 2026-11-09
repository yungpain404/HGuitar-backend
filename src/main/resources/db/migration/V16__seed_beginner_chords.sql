-- V16: Seed additional beginner chords needed by the easy song repertoire.
-- All literals use N'' so Vietnamese diacritics are preserved.

-- F chord
DECLARE @f_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@f_id, 'F', 'ACOUSTIC', N'Hợp âm F major — hợp âm barre phổ biến, cần áp buộc dây 1.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @f_id, 1, 1,
    N'[{"string":6,"fret":1,"finger":1},{"string":5,"fret":3,"finger":3},{"string":4,"fret":3,"finger":4},{"string":3,"fret":2,"finger":2},{"string":2,"fret":1,"finger":1},{"string":1,"fret":1,"finger":1}]');

-- Dm chord
DECLARE @dm_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@dm_id, 'Dm', 'ACOUSTIC', N'Hợp âm D minor open — nhẹ nhàng, thường dùng trong ballad.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @dm_id, 1, 1,
    N'[{"string":4,"fret":0},{"string":3,"fret":2,"finger":1},{"string":2,"fret":3,"finger":3},{"string":1,"fret":2,"finger":2},{"string":5,"fret":-1},{"string":6,"fret":-1}]');

-- E chord
DECLARE @e_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@e_id, 'E', 'ACOUSTIC', N'Hợp âm E major open — âm thanh sáng, nền tảng của nhiều bài rock.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @e_id, 1, 1,
    N'[{"string":5,"fret":2,"finger":2},{"string":4,"fret":2,"finger":3},{"string":3,"fret":1,"finger":1},{"string":2,"fret":0},{"string":1,"fret":0},{"string":6,"fret":0}]');

-- A chord
DECLARE @a_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@a_id, 'A', 'ACOUSTIC', N'Hợp âm A major open — ba ngón cùng phím 2.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @a_id, 1, 1,
    N'[{"string":5,"fret":0},{"string":4,"fret":2,"finger":1},{"string":3,"fret":2,"finger":2},{"string":2,"fret":2,"finger":3},{"string":1,"fret":0},{"string":6,"fret":-1}]');

-- Em7 chord
DECLARE @em7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@em7_id, 'Em7', 'ACOUSTIC', N'Hợp âm Em7 — biến thể nhẹ nhàng của Em, dùng rất nhiều trong pop.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @em7_id, 1, 1,
    N'[{"string":5,"fret":2,"finger":2},{"string":4,"fret":2,"finger":3},{"string":3,"fret":0},{"string":2,"fret":3,"finger":4},{"string":1,"fret":0},{"string":6,"fret":0}]');

-- Cadd9 chord
DECLARE @cadd9_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@cadd9_id, 'Cadd9', 'ACOUSTIC', N'Hợp âm Cadd9 — âm thanh mở, dễ chuyển sang G.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @cadd9_id, 1, 1,
    N'[{"string":5,"fret":3,"finger":3},{"string":4,"fret":2,"finger":2},{"string":3,"fret":0},{"string":2,"fret":3,"finger":4},{"string":1,"fret":0},{"string":6,"fret":-1}]');

-- Dsus4 chord
DECLARE @dsus4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@dsus4_id, 'Dsus4', 'ACOUSTIC', N'Hợp âm Dsus4 — giữوتر trước khi xuống D, dùng nhiều trong Wonderwall.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @dsus4_id, 1, 1,
    N'[{"string":4,"fret":0},{"string":3,"fret":2,"finger":1},{"string":2,"fret":3,"finger":3},{"string":1,"fret":3,"finger":4},{"string":5,"fret":-1},{"string":6,"fret":-1}]');

-- A7sus4 chord
DECLARE @a7sus4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@a7sus4_id, 'A7sus4', 'ACOUSTIC', N'Hợp âm A7sus4 — biểu tượng của bài Wonderwall.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @a7sus4_id, 1, 1,
    N'[{"string":5,"fret":0},{"string":4,"fret":2,"finger":1},{"string":3,"fret":2,"finger":2},{"string":2,"fret":3,"finger":3},{"string":1,"fret":0},{"string":6,"fret":-1}]');

-- G7 chord
DECLARE @g7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@g7_id, 'G7', 'ACOUSTIC', N'Hợp âm G7 — dominant seventh, dùng trong blues và folk.');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @g7_id, 1, 1,
    N'[{"string":6,"fret":3,"finger":2},{"string":5,"fret":2,"finger":1},{"string":4,"fret":0},{"string":3,"fret":0},{"string":2,"fret":0},{"string":1,"fret":1,"finger":3}]');

-- Bm chord (simplified beginner version)
DECLARE @bm_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@bm_id, 'Bm', 'ACOUSTIC', N'Hợp âm B minor — phiên bản đơn giản cho người mới (không barre toàn dây).');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @bm_id, 1, 2,
    N'[{"string":5,"fret":2,"finger":1},{"string":4,"fret":4,"finger":3},{"string":3,"fret":4,"finger":4},{"string":2,"fret":3,"finger":2},{"string":1,"fret":2,"finger":1},{"string":6,"fret":-1}]');

-- F#m chord
DECLARE @fsharp_m_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@fsharp_m_id, 'F#m', 'ACOUSTIC', N'Hợp âm F# minor — dùng trong Someone Like You (Adele).');
INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @fsharp_m_id, 1, 2,
    N'[{"string":5,"fret":2,"finger":1},{"string":4,"fret":4,"finger":3},{"string":3,"fret":4,"finger":4},{"string":2,"fret":2,"finger":1},{"string":1,"fret":2,"finger":1},{"string":6,"fret":-1}]');
