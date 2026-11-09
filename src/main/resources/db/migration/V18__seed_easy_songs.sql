-- V18: Seed easy beginner songs (bài hát dễ đánh) — Việt Nam & nước ngoài.
-- Mỗi bài kèm hợp âm cần biết, key, capo, cách đánh công thức và phân khúc (section).

-- ── Resolve chord IDs ───────────────────────────────────────────────────────
DECLARE @c_id  UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'C'  AND guitar_type = 'ACOUSTIC');
DECLARE @g_id  UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'G'  AND guitar_type = 'ACOUSTIC');
DECLARE @am_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'Am' AND guitar_type = 'ACOUSTIC');
DECLARE @f_id  UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'F'  AND guitar_type = 'ACOUSTIC');
DECLARE @dm_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'Dm' AND guitar_type = 'ACOUSTIC');
DECLARE @e_id  UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'E'  AND guitar_type = 'ACOUSTIC');
DECLARE @d_id  UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'D'  AND guitar_type = 'ACOUSTIC');
DECLARE @em_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'Em' AND guitar_type = 'ACOUSTIC');
DECLARE @a_id  UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'A'  AND guitar_type = 'ACOUSTIC');
DECLARE @em7_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'Em7' AND guitar_type = 'ACOUSTIC');
DECLARE @dsus4_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'Dsus4' AND guitar_type = 'ACOUSTIC');
DECLARE @a7sus4_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'A7sus4' AND guitar_type = 'ACOUSTIC');
DECLARE @fsharp_m_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.chords WHERE name = 'F#m' AND guitar_type = 'ACOUSTIC');

-- ── Resolve strumming pattern IDs ───────────────────────────────────────────
DECLARE @cf1_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.strumming_patterns WHERE formula = N'↓ ↓↑↑ ↓ ↑');
DECLARE @cf2_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.strumming_patterns WHERE formula = N'↓ ↓ ↓↑ ↓↑');
DECLARE @cf3_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.strumming_patterns WHERE formula = N'↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑');
DECLARE @cf4_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM music.strumming_patterns WHERE formula = N'↓ - ↓ ↑ ↓ ↑');

-- ============================================================
-- VIETNAMESE SONGS
-- ============================================================

-- 1. Là Anh Mà — Orange (C, G, Am, F)
DECLARE @song1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song1_id, 'ACOUSTIC', 'BEGINNER', 'VIETNAMESE', 'C', 0, 95, @cf1_id, 1, 'PUBLISHED', 1);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song1_id, 'VI', N'Là Anh Mà', N'Orange', N'Bài hit dễ chơi với 4 hợp âm C - G - Am - F, thích hợp người mới bắt đầu.'),
    (NEWID(), @song1_id, 'EN', N'La Anh Ma', N'Orange', N'Easy hit with 4 chords C - G - Am - F, great for beginners.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song1_id, @c_id, 1, 1), (@song1_id, @g_id, 2, 1), (@song1_id, @am_id, 3, 1), (@song1_id, @f_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song1_id, 'INTRO', 1, N'Nhạc đề', N'C | G | Am | F', @cf1_id),
    (@song1_id, 'VERSE', 2, N'Verse', N'C | G | Am | F', @cf1_id),
    (@song1_id, 'CHORUS', 3, N'Nhạn', N'C | G | Am | F', @cf1_id);

-- 2. Cơn Mưa Ngang Qua — Trịnh Công Sơn (Am, C, G, Dm)
DECLARE @song2_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song2_id, 'ACOUSTIC', 'BEGINNER', 'VIETNAMESE', 'Am', 0, 75, @cf1_id, 1, 'PUBLISHED', 2);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song2_id, 'VI', N'Cơn Mưa Ngang Qua', N'Trịnh Công Sơn', N'Bài ca đ앙 ca đơn giản với Am - C - G - Dm.'),
    (NEWID(), @song2_id, 'EN', N'Ca Mau Ngang Qua', N'Trinh Cong Son', N'Simple Trinh Cong Son song with Am - C - G - Dm.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song2_id, @am_id, 1, 1), (@song2_id, @c_id, 2, 1), (@song2_id, @g_id, 3, 1), (@song2_id, @dm_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song2_id, 'VERSE', 1, N'Verse', N'Am | C | G | Dm', @cf1_id),
    (@song2_id, 'CHORUS', 2, N'Nhạn', N'Am | C | G | Dm', @cf1_id);

-- 3. Nếu Như Anh Đến — Hà Anh Tuấn (G, C, Em, D)
DECLARE @song3_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song3_id, 'ACOUSTIC', 'BEGINNER', 'VIETNAMESE', 'G', 0, 85, @cf2_id, 1, 'PUBLISHED', 3);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song3_id, 'VI', N'Nếu Như Anh Đến', N'Hà Anh Tuấn', N'Bài pop nhẹ nhàng với G - C - Em - D, công thức đánh 2.'),
    (NEWID(), @song3_id, 'EN', N'Neu Nhu Anh Den', N'Ha Anh Tuan', N'Gentle pop song with G - C - Em - D, strumming formula 2.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song3_id, @g_id, 1, 1), (@song3_id, @c_id, 2, 1), (@song3_id, @em_id, 3, 1), (@song3_id, @d_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song3_id, 'VERSE', 1, N'Verse', N'G | C | Em | D', @cf2_id),
    (@song3_id, 'CHORUS', 2, N'Nhạn', N'G | C | Em | D', @cf2_id);

-- ============================================================
-- FOREIGN SONGS
-- ============================================================

-- 4. Stand By Me — Ben E. King (G, Em, C, D)
DECLARE @song4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song4_id, 'ACOUSTIC', 'BEGINNER', 'FOREIGN', 'G', 0, 80, @cf1_id, 1, 'PUBLISHED', 4);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song4_id, 'VI', N'Stand By Me', N'Ben E. King', N'Bài clássikera với G - Em - C - D, công thức 1.'),
    (NEWID(), @song4_id, 'EN', N'Stand By Me', N'Ben E. King', N'Classic hit with G - Em - C - D, strumming formula 1.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song4_id, @g_id, 1, 1), (@song4_id, @em_id, 2, 1), (@song4_id, @c_id, 3, 1), (@song4_id, @d_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song4_id, 'INTRO', 1, N'Intro', N'G | Em | C | D', @cf1_id),
    (@song4_id, 'VERSE', 2, N'Verse', N'G | Em | C | D', @cf1_id),
    (@song4_id, 'CHORUS', 3, N'Chorus', N'G | Em | C | D', @cf1_id);

-- 5. Wonderwall — Oasis (Em7, G, Dsus4, A7sus4), capo 2
DECLARE @song5_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song5_id, 'ACOUSTIC', 'BEGINNER', 'FOREIGN', 'Em', 2, 88, @cf1_id, 1, 'PUBLISHED', 5);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song5_id, 'VI', N'Wonderwall', N'Oasis', N'Bài hay của Oasis với Em7 - G - Dsus4 - A7sus4, đệm capo phím 2.'),
    (NEWID(), @song5_id, 'EN', N'Wonderwall', N'Oasis', N'Oasis hit with Em7 - G - Dsus4 - A7sus4, capo on fret 2.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song5_id, @em7_id, 1, 1), (@song5_id, @g_id, 2, 1), (@song5_id, @dsus4_id, 3, 1), (@song5_id, @a7sus4_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song5_id, 'INTRO', 1, N'Intro', N'Em7 | G | Dsus4 | A7sus4', @cf1_id),
    (@song5_id, 'VERSE', 2, N'Verse', N'Em7 | G | Dsus4 | A7sus4', @cf1_id),
    (@song5_id, 'CHORUS', 3, N'Chorus', N'Em7 | G | Dsus4 | A7sus4', @cf1_id);

-- 6. Let It Be — The Beatles (C, G, Am, F)
DECLARE @song6_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song6_id, 'ACOUSTIC', 'BEGINNER', 'FOREIGN', 'C', 0, 70, @cf3_id, 1, 'PUBLISHED', 6);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song6_id, 'VI', N'Let It Be', N'The Beatles', N'Bài êm ái của Beatles với C - G - Am - F, công thức đánh 3.'),
    (NEWID(), @song6_id, 'EN', N'Let It Be', N'The Beatles', N'Gentle Beatles song with C - G - Am - F, strumming formula 3.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song6_id, @c_id, 1, 1), (@song6_id, @g_id, 2, 1), (@song6_id, @am_id, 3, 1), (@song6_id, @f_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song6_id, 'INTRO', 1, N'Intro', N'C | G | Am | F', @cf3_id),
    (@song6_id, 'VERSE', 2, N'Verse', N'C | G | Am | F', @cf3_id),
    (@song6_id, 'CHORUS', 3, N'Chorus', N'C | G | Am | F', @cf3_id);

-- 7. Riptide — Vance Joy (Am, G, C)
DECLARE @song7_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song7_id, 'ACOUSTIC', 'BEGINNER', 'FOREIGN', 'Am', 0, 140, @cf2_id, 1, 'PUBLISHED', 7);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song7_id, 'VI', N'Riptide', N'Vance Joy', N'Bài nhịp nhanh với 3 hợp âm Am - G - C, công thức 2.'),
    (NEWID(), @song7_id, 'EN', N'Riptide', N'Vance Joy', N'Upbeat song with 3 chords Am - G - C, strumming formula 2.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song7_id, @am_id, 1, 1), (@song7_id, @g_id, 2, 1), (@song7_id, @c_id, 3, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song7_id, 'INTRO', 1, N'Intro', N'Am | G | C', @cf2_id),
    (@song7_id, 'VERSE', 2, N'Verse', N'Am | G | C', @cf2_id),
    (@song7_id, 'CHORUS', 3, N'Chorus', N'Am | G | C', @cf2_id);

-- 8. Horse with No Name — America (Em, D) — simplified beginner
DECLARE @song8_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song8_id, 'ACOUSTIC', 'BEGINNER', 'FOREIGN', 'Em', 0, 90, @cf4_id, 1, 'PUBLISHED', 8);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song8_id, 'VI', N'Horse with No Name', N'America', N'Bài folk dễ chơi với 2 hợp âm Em - D, công thức 4 có nhịp nghỉ.'),
    (NEWID(), @song8_id, 'EN', N'Horse with No Name', N'America', N'Easy folk song with 2 chords Em - D, strumming formula 4 with pause.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song8_id, @em_id, 1, 1), (@song8_id, @d_id, 2, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song8_id, 'INTRO', 1, N'Intro', N'Em | D', @cf4_id),
    (@song8_id, 'VERSE', 2, N'Verse', N'Em | D', @cf4_id),
    (@song8_id, 'CHORUS', 3, N'Chorus', N'Em | D', @cf4_id);

-- 9. Someone Like You — Adele (A, E, F#m, D)
DECLARE @song9_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO song.songs (id, guitar_type, difficulty, source, original_key, capo_fret, bpm, strumming_pattern_id, is_formula, status, sort_order)
VALUES (@song9_id, 'ACOUSTIC', 'BEGINNER', 'FOREIGN', 'A', 0, 78, @cf1_id, 1, 'PUBLISHED', 9);

INSERT INTO song.song_translations (id, song_id, language, title, artist, description) VALUES
    (NEWID(), @song9_id, 'VI', N'Someone Like You', N'Adele', N'Bài ballad trending với A - E - F#m - D, công thức 1.'),
    (NEWID(), @song9_id, 'EN', N'Someone Like You', N'Adele', N'Trending ballad with A - E - F#m - D, strumming formula 1.');

INSERT INTO song.song_chords (song_id, chord_id, sort_order, is_required) VALUES
    (@song9_id, @a_id, 1, 1), (@song9_id, @e_id, 2, 1), (@song9_id, @fsharp_m_id, 3, 1), (@song9_id, @d_id, 4, 1);

INSERT INTO song.song_sections (song_id, section_type, section_order, name, chord_progression, strumming_pattern_id) VALUES
    (@song9_id, 'INTRO', 1, N'Intro', N'A | E | F#m | D', @cf1_id),
    (@song9_id, 'VERSE', 2, N'Verse', N'A | E | F#m | D', @cf1_id),
    (@song9_id, 'CHORUS', 3, N'Chorus', N'A | E | F#m | D', @cf1_id);
