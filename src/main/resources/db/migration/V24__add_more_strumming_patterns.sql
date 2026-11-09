-- V24: Add more strumming patterns including bossa nova and folk styles.

-- Bossa nova — nhịp Brazil, nhẹ nhàng và du dương (trung bình)
DECLARE @bn_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@bn_id, N'Bossa nova: ↓ - ↑ ↓ ↑ - ↑', N'↓ - ↑ ↓ ↑ - ↑',
    N'Nhịp bossa nova điển hình — gãy xuống nhịp 1, sau đó các nhịp nhẹ, syncop tạo cảm giác du dương. Phù hợp bài jazz, latin.',
    'INTERMEDIATE', 'ALL', 60, 110, 1, 'PUBLISHED', 6);

-- Folk/country steady down-up — nhịp đều, dễ theo dõi
DECLARE @fc_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@fc_id, N'Folk: ↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑', N'↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑',
    N'Gãy xuống-lên luân phiên đều 8 nhịp. Nhịp dân ca, country, dễ theo dõi cho người mới.',
    'BEGINNER', 'ALL', 70, 130, 1, 'PUBLISHED', 7);

-- Reggae off-beat — chỉ gãy nhịp lẻ
DECLARE @rg_id UNIQUEIDENTIFIER = NEWID();
INSERT INTO music.strumming_patterns (id, name, formula, description, difficulty, guitar_type, bpm_min, bpm_max, is_formula, status, sort_order)
VALUES (@rg_id, N'Reggae: - ↑ - ↑ - ↑ - ↑', N'- ↑ - ↑ - ↑ - ↑',
    N'Chỉ gãy lên các nhịp lẻ (off-beat), tạo cảm giác reggae. Cần giữ nhịp chặt.',
    'INTERMEDIATE', 'ALL', 70, 100, 1, 'PUBLISHED', 8);
