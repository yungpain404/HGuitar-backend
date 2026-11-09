# API — Cách đánh đàn (Strumming Patterns / Công thức đánh)

Base path: `GET /api/v1/strumming-patterns`

## 1. Lấy danh sách cách đánh
```
GET /api/v1/strumming-patterns?guitarType=ALL&difficulty=BEGINNER
```
Query params (tùy chọn):
- `guitarType`: `ACOUSTIC` | `CLASSICAL` | `ALL`
- `difficulty`: `BEGINNER` | `INTERMEDIATE` | `ADVANCED`

Trả về: `List<StrummingPatternResponse>` (chỉ những công thức `is_formula = true`, trạng thái `PUBLISHED`)

### StrummingPatternResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID công thức |
| name | String | Tên (vd: "Công thức 1: ↓ ↓↑↑ ↓ ↑") |
| formula | String | Công thức đánh, ký hiệu: ↓ = xuống, ↑ = lên, - = nghỉ |
| description | String | Mô tả ngắn |
| difficulty | String | `BEGINNER` | `INTERMEDIATE` | `ADVANCED` |
| guitarType | String | `ACOUSTIC` | `CLASSICAL` | `ALL` |
| bpmMin / bpmMax | Integer | Nhịp độ đề xuất |
| isFormula | boolean | true = công thức dành cho người mới |
| status | String | `PUBLISHED` |
| sortOrder | int | Thứ tự hiển thị |
| createdAt | Instant | Thời gian tạo |

## 2. Lấy chi tiết 1 công thức (kèm hướng dẫn từng bước)
```
GET /api/v1/strumming-patterns/{id}?lang=vi
```
Query params:
- `lang` (tùy chọn): `vi` (mặc định) | `en`

Trả về: `StrummingPatternDetailResponse` — giống `StrummingPatternResponse` thêm trường `instructions`.

### StrummingPatternDetailResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID công thức |
| name | String | Tên |
| formula | String | Công thức ký hiệu |
| description | String | Mô tả ngắn |
| difficulty | String | `BEGINNER` | `INTERMEDIATE` | `ADVANCED` |
| guitarType | String | `ACOUSTIC` | `CLASSICAL` | `ALL` |
| bpmMin / bpmMax | Integer | Nhịp độ đề xuất |
| isFormula | boolean | true = công thức người mới |
| status | String | `PUBLISHED` |
| sortOrder | int | Thứ tự hiển thị |
| createdAt | Instant | Thời gian tạo |
| instructions | List<StrummingInstructionResponse> | Hướng dẫn đánh chi tiết từng bước |

### StrummingInstructionResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID bước |
| stepNumber | int | Số thứ tự bước (1, 2, 3...) |
| title | String | Tiêu đề bước (theo `lang`) |
| content | String | Nội dung chi tiết bước (theo `lang`) |
| tip | String | Mẹo nhỏ (tùy chọn, theo `lang`) |

## 3. Lấy chỉ danh sách hướng dẫn từng bước
```
GET /api/v1/strumming-patterns/{id}/instructions?lang=vi
```
Query params:
- `lang` (tùy chọn): `vi` (mặc định) | `en`

Trả về: `List<StrummingInstructionResponse>` sắp xếp theo `stepNumber`.

## 4. Tạo công thức mới (admin)
```
POST /api/v1/strumming-patterns
Content-Type: application/json
```

{
  "name": "Công thức 1: ↓ ↓↑↑ ↓ ↑",
  "formula": "↓ ↓↑↑ ↓ ↑",
  "description": "Công thức đánh phổ biến nhất...",
  "difficulty": "BEGINNER",
  "guitarType": "ALL",
  "bpmMin": 60,
  "bpmMax": 120,
  "isFormula": true,
  "sortOrder": 1
}

Trả về: `StrummingPatternResponse`

## Công thức đã seed (kiểu công thức người mới)
| Công thức | Ký hiệu | Dùng cho | Độ khó |
|-----------|---------|----------|--------|
| Công thức 1 | `↓ ↓↑↑ ↓ ↑` | Hầu hết bài pop (phổ biến nhất) | BEGINNER |
| Công thức 2 | `↓ ↓ ↓↑ ↓↑` | Bài chậm, ballad, bolero | BEGINNER |
| Công thức 3 | `↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑` | Nhịp nhanh, gãy đều 8 nhịp | BEGINNER |
| Công thức 4 | `↓ - ↓ ↑ ↓ ↑` | Ballad chậm, có nhịp nghỉ | BEGINNER |
| Công thức 5 | `↓↑ ↓↑ ↓↑ ↓↑` | Nhịp mạnh, rock/pop | BEGINNER |

## Ví dụ JSON chi tiết (Bossa nova)
```json
{
  "id": "650e8400-e29b-41d4-a716-446655440001",
  "name": "Bossa nova",
  "formula": "↓ - ↑ ↓ ↑ - ↑",
  "description": "Nhịp Bossa nova đặc trưng, nhẹ nhàng, du dương. Dùng cho bài jazz, latin nhẹ.",
  "difficulty": "INTERMEDIATE",
  "guitarType": "ACOUSTIC",
  "bpmMin": 70,
  "bpmMax": 110,
  "isFormula": true,
  "status": "PUBLISHED",
  "sortOrder": 6,
  "createdAt": "2026-01-01T00:00:00Z",
  "instructions": [
    {
      "id": "750e8400-e29b-41d4-a716-446655440010",
      "stepNumber": 1,
      "title": "Đặt ngón tay và làm quen nhịp",
      "content": "Đặt ngón tay trên hợp âm (ví dụ Am). Dùng ngón cái gãy xuống nhẹ nhàng theo nhịp 1 (↓). Đừng gãy quá mạnh.",
      "tip": "Dùng metronome nhịp 60 bpm, gãy đều 1 lần mỗi nhịp."
    },
    {
      "id": "750e8400-e29b-41d4-a716-446655440011",
      "stepNumber": 2,
      "title": "Thêm gãy lên nhẹ (↑)",
      "content": "Gãy thêm một nét lên (↑) ngay sau nét xuống. Gãy lên nhẹ hơn gãy xuống để tạo phong cách Bossa Nova.",
      "tip": "Ngón trỏ hoặc ngón giữa gãy dây 1, 2 khi gãy lên."
    },
    {
      "id": "750e8400-e29b-41d4-a716-446655440012",
      "stepNumber": 3,
      "title": "Kết hợp nhịp nghỉ (-)",
      "content": "Có nhịp nghỉ giữa các cụm gãy để tạo cảm giác du dương, đặc trưng của Bossa Nova.",
      "tip": "Trong nhịp nghỉ, ngón tay nhẹ nhàng chạm dây để tắt âm (muted)."
    }
  ]
}
```
