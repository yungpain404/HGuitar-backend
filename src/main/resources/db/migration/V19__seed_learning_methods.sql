-- V19: Seed common learning methods (phương pháp học phổ biến) for beginners.
-- steps_json is a JSON array of step descriptions.

DECLARE @m1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.learning_methods (id, name_key, difficulty, target_guitar, estimated_weeks, steps_count, status, sort_order)
VALUES (@m1_id, 'method_golden_4_chords', 'BEGINNER', 'ACOUSTIC', 4, 4, 'PUBLISHED', 1);

INSERT INTO learning.learning_method_translations (id, method_id, language, title, description, steps_json) VALUES
    (NEWID(), @m1_id, 'VI', N'Phương pháp 4 hợp âm vàng (C - G - Am - F)',
     N'Học 4 hợp âm C, G, Am, F rồi áp dụng ngay vào hàng trăm bài hát pop. Phương pháp nhanh nhất để có thể ca nhạc ngay.',
     N'["Bước 1: Học và nắm hình ngón 4 hợp âm C, G, Am, F", "Bước 2: Thực hành đổi hợp âm chậm giữa các cặp", "Bước 3: Áp dụng công thức đánh ↓ ↓↑↑ ↓ ↑ vào từng bài", "Bước 4: Chọn một bài đơn giản (ví dụ Là Anh Mà, Let It Be) và ca toàn bài"]'),
    (NEWID(), @m1_id, 'EN', N'Golden 4-chord method (C - G - Am - F)',
     N'Learn 4 chords C, G, Am, F then apply to hundreds of pop songs. Fastest way to start playing songs.',
     N'["Step 1: Learn and memorize finger shapes for C, G, Am, F", "Step 2: Practice slow chord changes between each pair", "Step 3: Apply strumming formula ↓ ↓↑↑ ↓ ↑ to each song", "Step 4: Pick one easy song (e.g. La Anh Ma, Let It Be) and play it all the way through"]');

DECLARE @m2_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.learning_methods (id, name_key, difficulty, target_guitar, estimated_weeks, steps_count, status, sort_order)
VALUES (@m2_id, 'method_strumming_formulas', 'BEGINNER', 'ALL', 2, 3, 'PUBLISHED', 2);

INSERT INTO learning.learning_method_translations (id, method_id, language, title, description, steps_json) VALUES
    (NEWID(), @m2_id, 'VI', N'Thực hành các công thức đánh (strumming formulas)',
     N'Thay vì học nhiều kiểu đánh rắc rối, người mới chỉ cần nắm 5 công thức đánh cơ bản đã chơi được hầu hết bài.',
     N'["Bước 1: Nắm công thức 1 (↓ ↓↑↑ ↓ ↑) với metronome nhịp 70", "Bước 2: Thử công thức 2 (↓ ↓ ↓↑ ↓↑) cho bài chậm, ballad", "Bước 3: Áp dụng công thức 3, 4, 5 tùy thể loại bài hát", "Bước 4: Kết hợp công thức đánh với bài hát từ thư viện"]'),
    (NEWID(), @m2_id, 'EN', N'Strumming formulas practice',
     N'Instead of many complex patterns, beginners only need 5 basic strumming formulas to play most songs.',
     N'["Step 1: Master formula 1 (↓ ↓↑↑ ↓ ↑) with metronome at 70 bpm", "Step 2: Try formula 2 (↓ ↓ ↓↑ ↓↑) for slow ballads", "Step 3: Apply formulas 3, 4, 5 by song genre", "Step 4: Combine formulas with songs from the library"]');

DECLARE @m3_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.learning_methods (id, name_key, difficulty, target_guitar, estimated_weeks, steps_count, status, sort_order)
VALUES (@m3_id, 'method_learn_by_songs', 'BEGINNER', 'ACOUSTIC', 6, 3, 'PUBLISHED', 3);

INSERT INTO learning.learning_method_translations (id, method_id, language, title, description, steps_json) VALUES
    (NEWID(), @m3_id, 'VI', N'Học theo bài hát (Learn by songs)',
     N'Không học lý thuyết khô khan, người mới chọn bài yêu thích và học thông qua ca nhạc thực tế.',
     N'["Bước 1: Chọn một bài dễ từ thư viện (ví dụ Horse with No Name chỉ 2 hợp âm)", "Bước 2: Học hợp âm và hình ngón từng hợp âm trong bài", "Bước 3: Dùng công thức đánh đi kèm bài hát", "Bước 4: Ca từng phần (Intro, Verse, Chorus) rồi ghép thành toàn bài"]'),
    (NEWID(), @m3_id, 'EN', N'Learn by songs',
     N'No dry theory — beginners pick a favorite song and learn by playing it for real.',
     N'["Step 1: Pick an easy song from the library (e.g. Horse with No Name only 2 chords)", "Step 2: Learn the chords and finger shapes used in the song", "Step 3: Use the matching strumming formula", "Step 4: Play each section (Intro, Verse, Chorus) then put the whole song together"]');

DECLARE @m4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO learning.learning_methods (id, name_key, difficulty, target_guitar, estimated_weeks, steps_count, status, sort_order)
VALUES (@m4_id, 'method_fast_chord_changes', 'BEGINNER', 'ALL', 3, 3, 'PUBLISHED', 4);

INSERT INTO learning.learning_method_translations (id, method_id, language, title, description, steps_json) VALUES
    (NEWID(), @m4_id, 'VI', N'Cách đổi hợp âm nhanh (Fast chord changes)',
     N'Vấn đề lớn nhất của người mới là đổi hợp âm bị gián đoạn. Phương pháp này tập trung vào chuyển hợp âm mượt mà.',
     N'["Bước 1: Chọn hai hợp âm thường đổi cùng (ví dụ G ↔ C)", "Bước 2: Đặt ngón tay, nhấc lên, đặt lại lặp đi lặp lại 20 lần", "Bước 3: Dùng metronome, đổi hợp âm mỗi 4 nhịp rồi giảm dần xuống 2 nhịp", "Bước 4: Áp dụng vào bài hát thực tế"]'),
    (NEWID(), @m4_id, 'EN', N'Fast chord changes',
     N'The biggest beginner hurdle is choppy chord changes. This method focuses on smooth transitions.',
     N'["Step 1: Pick two chords that change often (e.g. G ↔ C)", "Step 2: Place fingers, lift, replace — repeat 20 times", "Step 3: With metronome, change every 4 beats then reduce to 2 beats", "Step 4: Apply to real songs"]');
