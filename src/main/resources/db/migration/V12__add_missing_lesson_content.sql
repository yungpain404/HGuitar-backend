-- V12: Add the missing beginner content blocks for the seeded acoustic lessons.
--
-- V9 created lesson 2 (holding the guitar) with only a quiz and lesson 3
-- (the Am chord) with no content at all. Every lesson should contain at least
-- a short explanation and a practice instruction. All Vietnamese literals use
-- N'' so SQL Server does not lose diacritics with a Vietnamese collation.

-- Lesson 2: How to Hold the Guitar ------------------------------------------
DECLARE @hold_lesson_id UNIQUEIDENTIFIER = (
    SELECT TOP 1 lt.lesson_id
    FROM learning.lesson_translations AS lt
    WHERE lt.language = 'EN' AND lt.title = 'How to Hold the Guitar'
);

DECLARE @hold_text_block UNIQUEIDENTIFIER = NEWID();
INSERT INTO content.lesson_blocks (id, lesson_id, block_type, sort_order)
VALUES (@hold_text_block, @hold_lesson_id, 'TEXT', 1);

INSERT INTO content.lesson_block_translations (id, block_id, language, content) VALUES
    (NEWID(), @hold_text_block, 'EN',
     N'Sit on the front half of a firm chair with both feet flat on the floor. Rest the guitar body on your right thigh and angle the neck slightly upward. Keep your shoulders relaxed and your wrists straight.'),
    (NEWID(), @hold_text_block, 'VI',
     N'Ngồi ở nửa trước của một chiếc ghế chắc chắn, hai bàn chân đặt phẳng trên sàn. Đặt thân đàn lên đùi phải và hướng cần đàn hơi lên. Giữ vai thả lỏng và cổ tay thẳng.');

DECLARE @hold_practice_block UNIQUEIDENTIFIER = NEWID();
INSERT INTO content.lesson_blocks (id, lesson_id, block_type, sort_order)
VALUES (@hold_practice_block, @hold_lesson_id, 'PRACTICE_INSTRUCTION', 2);

INSERT INTO content.lesson_block_translations (id, block_id, language, content) VALUES
    (NEWID(), @hold_practice_block, 'EN',
     N'Hold the guitar for one minute without tension. Check that your left hand can reach the first fret without pulling the guitar away from your body.'),
    (NEWID(), @hold_practice_block, 'VI',
     N'Giữ đàn trong một phút mà không căng cơ. Kiểm tra xem tay trái có thể với tới phím 1 mà không kéo đàn ra khỏi người không.');

-- Lesson 3: The Am Chord ----------------------------------------------------
DECLARE @am_lesson_id UNIQUEIDENTIFIER = (
    SELECT TOP 1 lt.lesson_id
    FROM learning.lesson_translations AS lt
    WHERE lt.language = 'EN' AND lt.title = 'The Am Chord'
);

DECLARE @am_text_block UNIQUEIDENTIFIER = NEWID();
INSERT INTO content.lesson_blocks (id, lesson_id, block_type, sort_order)
VALUES (@am_text_block, @am_lesson_id, 'TEXT', 1);

INSERT INTO content.lesson_block_translations (id, block_id, language, content) VALUES
    (NEWID(), @am_text_block, 'EN',
     N'The Am chord uses three fingers. Place your index finger on the 2nd string (B), 1st fret. Place your middle finger on the 4th string (D), 2nd fret. Place your ring finger on the 3rd string (G), 2nd fret. The 5th string (A) and 1st string (high E) are open; the 6th string (low E) is muted.'),
    (NEWID(), @am_text_block, 'VI',
     N'Hợp âm Am dùng ba ngón. Đặt ngón trỏ ở dây 2 (B), phím 1. Đặt ngón giữa ở dây 4 (D), phím 2. Đặt ngón áp út ở dây 3 (G), phím 2. Dây 5 (A) và dây 1 (E cao) buông mở; dây 6 (E trầm) không gảy.');

DECLARE @am_diagram_block UNIQUEIDENTIFIER = NEWID();
INSERT INTO content.lesson_blocks (id, lesson_id, block_type, sort_order, metadata_json)
VALUES (
    @am_diagram_block,
    @am_lesson_id,
    'CHORD_DIAGRAM',
    2,
    N'{"name":"Am","baseFret":1,"fingers":[{"string":4,"fret":2,"finger":2},{"string":3,"fret":2,"finger":3},{"string":2,"fret":1,"finger":1},{"string":1,"fret":0},{"string":5,"fret":0},{"string":6,"fret":-1}]}'
);

INSERT INTO content.lesson_block_translations (id, block_id, language, content) VALUES
    (NEWID(), @am_diagram_block, 'EN', N''),
    (NEWID(), @am_diagram_block, 'VI', N'');

DECLARE @am_practice_block UNIQUEIDENTIFIER = NEWID();
INSERT INTO content.lesson_blocks (id, lesson_id, block_type, sort_order)
VALUES (@am_practice_block, @am_lesson_id, 'PRACTICE_INSTRUCTION', 3);

INSERT INTO content.lesson_block_translations (id, block_id, language, content) VALUES
    (NEWID(), @am_practice_block, 'EN',
     N'Press each finger just behind the fret, not on top of it. Strum from the 5th string down and listen for every note to ring clearly.'),
    (NEWID(), @am_practice_block, 'VI',
     N'Đặt mỗi ngón ngay sau phím, không đè lên phím. Gảy từ dây 5 xuống và nghe từng nốt vang rõ.');
