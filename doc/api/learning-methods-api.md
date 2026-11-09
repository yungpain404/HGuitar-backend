# API — Phương pháp học (Learning Methods)

Base path: `GET /api/v1/learning-methods`

## 1. Lấy danh sách phương pháp học
```
GET /api/v1/learning-methods?difficulty=BEGINNER&lang=vi
```
Query params:
- `difficulty` (tùy chọn): `BEGINNER` | `INTERMEDIATE` | `ADVANCED`
- `lang` (mặc định `vi`): `vi` | `en`

Trả về: `List<LearningMethodResponse>`

## 2. Lấy chi tiết 1 phương pháp
```
GET /api/v1/learning-methods/{id}?lang=vi
```
Trả về: `LearningMethodResponse`

### LearningMethodResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID phương pháp |
| nameKey | String | Mã phương pháp (vd: `method_golden_4_chords`) |
| difficulty | String | `BEGINNER` |
| targetGuitar | String | `ACOUSTIC` | `CLASSICAL` | `ALL` |
| estimatedWeeks | int | Số tuần học ước tính |
| stepsCount | int | Số bước |
| status | String | `PUBLISHED` |
| title / description | String | Theo `lang` |
| steps | List<String> | Các bước hướng dẫn chi tiết |

## Phương pháp mẫu đã seed
1. 4 hợp âm vàng (C - G - Am - F) — 4 tuần
2. Thực hành công thức đánh (strumming formulas) — 2 tuần
3. Học theo bài hát (learn by songs) — 6 tuần
4. Cách đổi hợp âm nhanh (fast chord changes) — 3 tuần
