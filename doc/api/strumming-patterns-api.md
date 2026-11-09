# API — Cách đánh đàn (Strumming Patterns / Công thức đánh)

Base path: `GET /api/v1/strumming-patterns`

## 1. Lấy danh sách cách đánh
```
GET /api/v1/strumming-patterns?guitarType=ALL&difficulty=BEGINNER
```
Query params:
- `guitarType` (tùy chọn): `ACOUSTIC` | `CLASSICAL` | `ALL`
- `difficulty` (tùy chọn): `BEGINNER` | `INTERMEDIATE` | `ADVANCED`

Trả về: `List<StrummingPatternResponse>` (chỉ những công thức `is_formula = true`, trạng thái PUBLISHED)

### StrummingPatternResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID công thức |
| name | String | Tên (vd: "Công thức 1: ↓ ↓↑↑ ↓ ↑") |
| formula | String | Công thức đánh, ký hiệu: ↓ = xuống, ↑ = lên, - = nghỉ |
| description | String | Mô tả |
| difficulty | String | `BEGINNER` | `INTERMEDIATE` | `ADVANCED` |
| guitarType | String | `ACOUSTIC` | `CLASSICAL` | `ALL` |
| bpmMin / bpmMax | Integer | Nhịp độ đề xuất |
| isFormula | boolean | true = công thức dành cho người mới |
| status | String | `PUBLISHED` |
| sortOrder | int | Thứ tự hiển thị |
| createdAt | Instant | Thời gian tạo |

## 2. Lấy chi tiết 1 công thức
```
GET /api/v1/strumming-patterns/{id}
```
Trả về: `StrummingPatternResponse`

## 3. Tạo công thức mới (admin)
```
POST /api/v1/strumming-patterns
Content-Type: application/json

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
```
Trả về: `StrummingPatternResponse`

## Công thức đã seed (kiểu công thức người mới)
| Công thức | Ký hiệu | Dùng cho |
|-----------|---------|----------|
| Công thức 1 | `↓ ↓↑↑ ↓ ↑` | Hầu hết bài pop (phổ biến nhất) |
| Công thức 2 | `↓ ↓ ↓↑ ↓↑` | Bài chậm, ballad, bolero |
| Công thức 3 | `↓ ↑ ↓ ↑ ↓ ↑ ↓ ↑` | Nhịp nhanh, gãy đều 8 nhịp |
| Công thức 4 | `↓ - ↓ ↑ ↓ ↑` | Ballad chậm, có nhịp nghỉ |
| Công thức 5 | `↓↑ ↓↑ ↓↑ ↓↑` | Nhịp mạnh, rock/pop |
