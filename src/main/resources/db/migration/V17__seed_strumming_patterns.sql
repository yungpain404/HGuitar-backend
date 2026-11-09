-- V17: Seed strumming patterns (cách đánh) kiểu công thức cho người mới bắt đầu.
-- symbols: ↓ = gảy xuống, ↑ = gảy lên, - = ngắtâm/không gảy

-- Công thức 1: ↓ ↓↑↑ ↓ ↑ (công thức phổ biến nhất, dùng cho hầu hết bài pop)
DECLARE @cf1_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@cf1_id, N'Công thức 1: ↓ ↓↑↑ ↓ ↑', N'↓ ↓↑↑ ↓ ↑',
    N'Công thức đánh phổ biến nhất cho người mới. Dùng cho hầu hết các bài pop, ballad. Gãy đều 4 nhịp.',
    'BEGINNER', 'ALL', 60, 120, 1, 'PUBLISHED', 1);

-- Công thức 2: ↓ ↓ ↓↑ ↓↑ (nhịp nhẹ, đưa dạo)
DECLARE @cf2_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@cf2_id, N'Công thức 2: ↓ ↓ ↓↑ ↓↑', N'↓ ↓ ↓↑ ↓↑',
    N'Nhịp nhẹ, thích hợp bài tình ca, bolero. Có nhịp nghỉ giữa các cụm gảy.',
    'BEGINNER', 'ALL', 50, 100, 1, 'PUBLISHED', 2);

-- Công thức 3: ↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑ (luân phiên, gãy đều)
DECLARE @cf3_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@cf3_id, N'Công thức 3: ↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑', N'↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑',
    N'Gảy luân phiên xuống-lên, đều 8 nhịp. Dùng cho bài ritmo nhanh, nhịp nhàng rõ.',
    'BEGINNER', 'ALL', 70, 140, 1, 'PUBLISHED', 3);

-- Công thức 4: ↓ - ↓ ↑ ↓ ↑ (có nhịp nghỉ, nhịp 자유)
DECLARE @cf4_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@cf4_id, N'Công thức 4: ↓ - ↓ ↑ ↓ ↑', N'↓ - ↓ ↑ ↓ ↑',
    N'Có nhịp nghỉ sau gảy đầu, tạo cảm giác du dương, phù hợp ballad chậm.',
    'BEGINNER', 'ALL', 50, 90, 1, 'PUBLISHED', 4);

-- Công thức 5: ↓↑ ↓↑ ↓↑ ↓↑ (gãy nhanh, nhịp đáy)
DECLARE @cf5_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@cf5_id, N'Công thức 5: ↓↑ ↓↑ ↓↑ ↓↑', N'↓↑ ↓↑ ↓↑ ↓↑',
    N'Gảy xuống-lên liền nhau 4 lần, nhịp nhanh, dùng cho bài rock/pop nhịp mạnh.',
    'BEGINNER', 'ALL', 80, 150, 1, 'PUBLISHED', 5);
