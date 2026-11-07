-- V11: Repair Vietnamese seed content inserted by V9.
--
-- V9 used plain varchar literals ('...') instead of Unicode literals (N'...').
-- On a SQL Server database using a Vietnamese collation, precomposed Vietnamese
-- characters that are not representable in the collation code page were
-- replaced with '?' during insertion. This migration restores the original
-- text using N'' literals. It is safe to run on a fresh database as well: V9 is
-- applied first and then corrected here.

-- Courses -------------------------------------------------------------------
UPDATE ct
SET title = N'Guitar Acoustic cho Người Mới Bắt Đầu',
    description = N'Học guitar acoustic từ đầu. Không cần kiến thức âm nhạc trước.'
FROM learning.course_translations AS ct
JOIN learning.courses AS c ON c.id = ct.course_id
WHERE ct.language = 'VI'
  AND c.guitar_type = 'ACOUSTIC'
  AND c.sort_order = 1;

UPDATE ct
SET title = N'Guitar Cổ Điển cho Người Mới Bắt Đầu',
    description = N'Bắt đầu hành trình guitar cổ điển với tư thế và kỹ thuật đúng.'
FROM learning.course_translations AS ct
JOIN learning.courses AS c ON c.id = ct.course_id
WHERE ct.language = 'VI'
  AND c.guitar_type = 'CLASSICAL'
  AND c.sort_order = 1;

-- Modules -------------------------------------------------------------------
UPDATE mt
SET title = N'Bắt Đầu',
    description = N'Tìm hiểu về guitar và cách cầm đàn.'
FROM learning.module_translations AS mt
JOIN learning.module_translations AS en
  ON en.module_id = mt.module_id AND en.language = 'EN'
WHERE mt.language = 'VI' AND en.title = 'Getting Started';

UPDATE mt
SET title = N'Hợp Âm Mở Cơ Bản',
    description = N'Học các hợp âm guitar đầu tiên của bạn.'
FROM learning.module_translations AS mt
JOIN learning.module_translations AS en
  ON en.module_id = mt.module_id AND en.language = 'EN'
WHERE mt.language = 'VI' AND en.title = 'Basic Open Chords';

UPDATE mt
SET title = N'Nền Tảng Guitar Cổ Điển',
    description = N'Tư thế đúng, vị trí tay, và cơ bản về dây nylon.'
FROM learning.module_translations AS mt
JOIN learning.module_translations AS en
  ON en.module_id = mt.module_id AND en.language = 'EN'
WHERE mt.language = 'VI' AND en.title = 'Classical Guitar Fundamentals';

-- Lessons -------------------------------------------------------------------
UPDATE lt
SET title = N'Cấu Tạo Guitar Acoustic',
    description = N'Tìm hiểu tên các bộ phận của guitar acoustic.'
FROM learning.lesson_translations AS lt
JOIN learning.lesson_translations AS en
  ON en.lesson_id = lt.lesson_id AND en.language = 'EN'
WHERE lt.language = 'VI' AND en.title = 'Anatomy of an Acoustic Guitar';

UPDATE lt
SET title = N'Cách Cầm Guitar',
    description = N'Học tư thế ngồi đúng và cách cầm guitar thoải mái.'
FROM learning.lesson_translations AS lt
JOIN learning.lesson_translations AS en
  ON en.lesson_id = lt.lesson_id AND en.language = 'EN'
WHERE lt.language = 'VI' AND en.title = 'How to Hold the Guitar';

UPDATE lt
SET title = N'Hợp Âm Am',
    description = N'Học cách chơi hợp âm Am — một trong những hợp âm phổ biến nhất cho người mới.'
FROM learning.lesson_translations AS lt
JOIN learning.lesson_translations AS en
  ON en.lesson_id = lt.lesson_id AND en.language = 'EN'
WHERE lt.language = 'VI' AND en.title = 'The Am Chord';

UPDATE lt
SET title = N'Tư Thế Ngồi Guitar Cổ Điển',
    description = N'Học tư thế ngồi guitar cổ điển đúng cho kỹ thuật lành mạnh.'
FROM learning.lesson_translations AS lt
JOIN learning.lesson_translations AS en
  ON en.lesson_id = lt.lesson_id AND en.language = 'EN'
WHERE lt.language = 'VI' AND en.title = 'Classical Sitting Posture';

-- Lesson content blocks -----------------------------------------------------
UPDATE bt
SET content = N'Guitar acoustic có một số bộ phận chính. **Thân đàn** tạo ra âm thanh. **Cổ đàn** giữ các phím đàn. **Đầu đàn** giữ các chốt lên dây. **Dây đàn** chạy từ ngựa đàn đến chốt lên dây.'
FROM content.lesson_block_translations AS bt
JOIN content.lesson_blocks AS b ON b.id = bt.block_id
JOIN learning.lesson_translations AS en
  ON en.lesson_id = b.lesson_id AND en.language = 'EN'
WHERE bt.language = 'VI'
  AND en.title = 'Anatomy of an Acoustic Guitar'
  AND b.sort_order = 1;

-- Quiz ----------------------------------------------------------------------
UPDATE q
SET title_vi = N'Bài Kiểm Tra Cơ Bản Về Guitar'
FROM assessment.quizzes AS q
JOIN learning.lesson_translations AS en
  ON en.lesson_id = q.lesson_id AND en.language = 'EN'
WHERE en.title = 'How to Hold the Guitar'
  AND q.title_en = 'Guitar Basics Quiz';

UPDATE qu
SET text_vi = N'Bộ phận nào của guitar tạo ra âm thanh?'
FROM assessment.questions AS qu
JOIN assessment.quizzes AS q ON q.id = qu.quiz_id
WHERE q.title_en = 'Guitar Basics Quiz'
  AND qu.sort_order = 1
  AND qu.text_en = 'Which part of the guitar produces the sound?';

UPDATE qu
SET text_vi = N'Một cây guitar acoustic tiêu chuẩn có bao nhiêu dây?'
FROM assessment.questions AS qu
JOIN assessment.quizzes AS q ON q.id = qu.quiz_id
WHERE q.title_en = 'Guitar Basics Quiz'
  AND qu.sort_order = 2
  AND qu.text_en = 'How many strings does a standard acoustic guitar have?';

UPDATE a
SET text_vi = N'Cổ đàn'
FROM assessment.answers AS a
JOIN assessment.questions AS qu ON qu.id = a.question_id
JOIN assessment.quizzes AS q ON q.id = qu.quiz_id
WHERE q.title_en = 'Guitar Basics Quiz'
  AND qu.text_en = 'Which part of the guitar produces the sound?'
  AND a.text_en = 'The neck';

UPDATE a
SET text_vi = N'Thân đàn'
FROM assessment.answers AS a
JOIN assessment.questions AS qu ON qu.id = a.question_id
JOIN assessment.quizzes AS q ON q.id = qu.quiz_id
WHERE q.title_en = 'Guitar Basics Quiz'
  AND qu.text_en = 'Which part of the guitar produces the sound?'
  AND a.text_en = 'The body';

UPDATE a
SET text_vi = N'Đầu đàn'
FROM assessment.answers AS a
JOIN assessment.questions AS qu ON qu.id = a.question_id
JOIN assessment.quizzes AS q ON q.id = qu.quiz_id
WHERE q.title_en = 'Guitar Basics Quiz'
  AND qu.text_en = 'Which part of the guitar produces the sound?'
  AND a.text_en = 'The headstock';

UPDATE a
SET text_vi = N'Chỉ dây đàn'
FROM assessment.answers AS a
JOIN assessment.questions AS qu ON qu.id = a.question_id
JOIN assessment.quizzes AS q ON q.id = qu.quiz_id
WHERE q.title_en = 'Guitar Basics Quiz'
  AND qu.text_en = 'Which part of the guitar produces the sound?'
  AND a.text_en = 'The strings alone';
