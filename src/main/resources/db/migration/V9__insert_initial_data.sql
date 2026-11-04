-- V9: Seed initial learning content for acoustic and classical guitar

-- ============================================================
-- Acoustic Guitar beginner course
-- ============================================================
DECLARE @acoustic_course_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.courses (id, guitar_type, sort_order, status)
VALUES (@acoustic_course_id, 'ACOUSTIC', 1, 'PUBLISHED');

INSERT INTO learning.course_translations (id, course_id, language, title, description) VALUES
    (NEWID(), @acoustic_course_id, 'EN', 'Acoustic Guitar for Beginners',
     'Learn acoustic guitar from zero. No prior music knowledge required.'),
    (NEWID(), @acoustic_course_id, 'VI', 'Guitar Acoustic cho Người Mới Bắt Đầu',
     'Học guitar acoustic từ đầu. Không cần kiến thức âm nhạc trước.');

-- Module 1: Getting started
DECLARE @module1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.modules (id, course_id, sort_order, status)
VALUES (@module1_id, @acoustic_course_id, 1, 'PUBLISHED');

INSERT INTO learning.module_translations (id, module_id, language, title, description) VALUES
    (NEWID(), @module1_id, 'EN', 'Getting Started', 'Learn about your guitar and how to hold it.'),
    (NEWID(), @module1_id, 'VI', 'Bắt Đầu', 'Tìm hiểu về guitar và cách cầm đàn.');

-- Lesson 1: Your guitar
DECLARE @lesson1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.lessons (id, module_id, sort_order, duration_minutes, status)
VALUES (@lesson1_id, @module1_id, 1, 10, 'PUBLISHED');

INSERT INTO learning.lesson_translations (id, lesson_id, language, title, description) VALUES
    (NEWID(), @lesson1_id, 'EN', 'Anatomy of an Acoustic Guitar',
     'Learn the names of the parts of an acoustic guitar.'),
    (NEWID(), @lesson1_id, 'VI', 'Cấu Tạo Guitar Acoustic',
     'Tìm hiểu tên các bộ phận của guitar acoustic.');

-- Lesson content blocks
INSERT INTO content.lesson_blocks (id, lesson_id, block_type, sort_order)
VALUES (NEWID(), @lesson1_id, 'TEXT', 1);

DECLARE @block1_id UNIQUEIDENTIFIER = (SELECT TOP 1 id FROM content.lesson_blocks WHERE lesson_id = @lesson1_id AND sort_order = 1);

INSERT INTO content.lesson_block_translations (id, block_id, language, content) VALUES
    (NEWID(), @block1_id, 'EN',
     'An acoustic guitar has several key parts. The **body** produces the sound. The **neck** holds the frets. The **headstock** holds the tuning pegs. The **strings** run from the bridge to the tuning pegs.'),
    (NEWID(), @block1_id, 'VI',
     'Guitar acoustic có một số bộ phận chính. **Thân đàn** tạo ra âm thanh. **Cổ đàn** giữ các phím đàn. **Đầu đàn** giữ các chốt lên dây. **Dây đàn** chạy từ ngựa đàn đến chốt lên dây.');

-- Lesson 2: Holding the guitar
DECLARE @lesson2_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.lessons (id, module_id, sort_order, duration_minutes, status)
VALUES (@lesson2_id, @module1_id, 2, 8, 'PUBLISHED');

INSERT INTO learning.lesson_translations (id, lesson_id, language, title, description) VALUES
    (NEWID(), @lesson2_id, 'EN', 'How to Hold the Guitar',
     'Learn the correct sitting posture and how to hold the guitar comfortably.'),
    (NEWID(), @lesson2_id, 'VI', 'Cách Cầm Guitar',
     'Học tư thế ngồi đúng và cách cầm guitar thoải mái.');

-- Quiz for lesson 2
DECLARE @quiz1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO assessment.quizzes (id, lesson_id, title_en, title_vi)
VALUES (@quiz1_id, @lesson2_id, 'Guitar Basics Quiz', 'Bài Kiểm Tra Cơ Bản Về Guitar');

DECLARE @q1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO assessment.questions (id, quiz_id, sort_order, text_en, text_vi)
VALUES (@q1_id, @quiz1_id, 1,
    'Which part of the guitar produces the sound?',
    'Bộ phận nào của guitar tạo ra âm thanh?');

INSERT INTO assessment.answers (id, question_id, text_en, text_vi, is_correct) VALUES
    (NEWID(), @q1_id, 'The neck', 'Cổ đàn', 0),
    (NEWID(), @q1_id, 'The body', 'Thân đàn', 1),
    (NEWID(), @q1_id, 'The headstock', 'Đầu đàn', 0),
    (NEWID(), @q1_id, 'The strings alone', 'Chỉ dây đàn', 0);

DECLARE @q2_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO assessment.questions (id, quiz_id, sort_order, text_en, text_vi)
VALUES (@q2_id, @quiz1_id, 2,
    'How many strings does a standard acoustic guitar have?',
    'Một cây guitar acoustic tiêu chuẩn có bao nhiêu dây?');

INSERT INTO assessment.answers (id, question_id, text_en, text_vi, is_correct) VALUES
    (NEWID(), @q2_id, '4', '4', 0),
    (NEWID(), @q2_id, '5', '5', 0),
    (NEWID(), @q2_id, '6', '6', 1),
    (NEWID(), @q2_id, '7', '7', 0);

-- Module 2: Basic chords
DECLARE @module2_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.modules (id, course_id, sort_order, status)
VALUES (@module2_id, @acoustic_course_id, 2, 'PUBLISHED');

INSERT INTO learning.module_translations (id, module_id, language, title, description) VALUES
    (NEWID(), @module2_id, 'EN', 'Basic Open Chords', 'Learn your first guitar chords.'),
    (NEWID(), @module2_id, 'VI', 'Hợp Âm Mở Cơ Bản', 'Học các hợp âm guitar đầu tiên của bạn.');

DECLARE @lesson3_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.lessons (id, module_id, sort_order, duration_minutes, status)
VALUES (@lesson3_id, @module2_id, 1, 15, 'PUBLISHED');

INSERT INTO learning.lesson_translations (id, lesson_id, language, title, description) VALUES
    (NEWID(), @lesson3_id, 'EN', 'The Am Chord',
     'Learn how to play the Am chord — one of the most common beginner chords.'),
    (NEWID(), @lesson3_id, 'VI', 'Hợp Âm Am',
     'Học cách chơi hợp âm Am — một trong những hợp âm phổ biến nhất cho người mới.');

-- ============================================================
-- Classical Guitar beginner course
-- ============================================================
DECLARE @classical_course_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.courses (id, guitar_type, sort_order, status)
VALUES (@classical_course_id, 'CLASSICAL', 1, 'PUBLISHED');

INSERT INTO learning.course_translations (id, course_id, language, title, description) VALUES
    (NEWID(), @classical_course_id, 'EN', 'Classical Guitar for Beginners',
     'Start your classical guitar journey with correct posture and technique.'),
    (NEWID(), @classical_course_id, 'VI', 'Guitar Cổ Điển cho Người Mới Bắt Đầu',
     'Bắt đầu hành trình guitar cổ điển với tư thế và kỹ thuật đúng.');

DECLARE @cmodule1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.modules (id, course_id, sort_order, status)
VALUES (@cmodule1_id, @classical_course_id, 1, 'PUBLISHED');

INSERT INTO learning.module_translations (id, module_id, language, title, description) VALUES
    (NEWID(), @cmodule1_id, 'EN', 'Classical Guitar Fundamentals',
     'Correct posture, hand position, and the basics of nylon strings.'),
    (NEWID(), @cmodule1_id, 'VI', 'Nền Tảng Guitar Cổ Điển',
     'Tư thế đúng, vị trí tay, và cơ bản về dây nylon.');

DECLARE @clesson1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.lessons (id, module_id, sort_order, duration_minutes, status)
VALUES (@clesson1_id, @cmodule1_id, 1, 12, 'PUBLISHED');

INSERT INTO learning.lesson_translations (id, lesson_id, language, title, description) VALUES
    (NEWID(), @clesson1_id, 'EN', 'Classical Sitting Posture',
     'Learn the proper classical guitar sitting position for healthy technique.'),
    (NEWID(), @clesson1_id, 'VI', 'Tư Thế Ngồi Guitar Cổ Điển',
     'Học tư thế ngồi guitar cổ điển đúng cho kỹ thuật lành mạnh.');

-- ============================================================
-- Common chord library
-- ============================================================

-- Am chord
DECLARE @am_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@am_id, 'Am', 'ACOUSTIC', 'A minor open chord — one of the first chords beginners learn.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @am_id, 1, 1,
    '[{"string":4,"fret":2,"finger":2},{"string":3,"fret":2,"finger":3},{"string":2,"fret":1,"finger":1},{"string":1,"fret":0},{"string":5,"fret":0},{"string":6,"fret":-1}]');

-- Em chord
DECLARE @em_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@em_id, 'Em', 'ACOUSTIC', 'E minor open chord — very easy for beginners.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @em_id, 1, 1,
    '[{"string":5,"fret":2,"finger":2},{"string":4,"fret":2,"finger":3},{"string":3,"fret":0},{"string":2,"fret":0},{"string":1,"fret":0},{"string":6,"fret":0}]');

-- G chord
DECLARE @g_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@g_id, 'G', 'ACOUSTIC', 'G major open chord — a common chord used in many beginner songs.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @g_id, 1, 1,
    '[{"string":6,"fret":3,"finger":2},{"string":5,"fret":2,"finger":1},{"string":4,"fret":0},{"string":3,"fret":0},{"string":2,"fret":0},{"string":1,"fret":3,"finger":3}]');

-- C chord
DECLARE @c_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@c_id, 'C', 'ACOUSTIC', 'C major open chord — one of the most used chords in popular music.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @c_id, 1, 1,
    '[{"string":5,"fret":3,"finger":3},{"string":4,"fret":2,"finger":2},{"string":3,"fret":0},{"string":2,"fret":1,"finger":1},{"string":1,"fret":0},{"string":6,"fret":-1}]');

-- D chord
DECLARE @d_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.chords (id, name, guitar_type, description)
VALUES (@d_id, 'D', 'ACOUSTIC', 'D major open chord.');

INSERT INTO music.chord_positions (id, chord_id, position_number, base_fret, fingers_json)
VALUES (NEWID(), @d_id, 1, 1,
    '[{"string":4,"fret":0},{"string":3,"fret":2,"finger":1},{"string":2,"fret":3,"finger":3},{"string":1,"fret":2,"finger":2},{"string":5,"fret":-1},{"string":6,"fret":-1}]');
