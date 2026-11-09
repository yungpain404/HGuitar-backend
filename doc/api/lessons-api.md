# API — Bài học (Lessons)

Base path: `GET /api/v1/lessons` (đã có sẵn trong module `learning`)

## 1. Lấy khóa học
```
GET /api/v1/courses?lang=vi&guitarType=ACOUSTIC
```
Trả về: `List<CourseResponse>`

## 2. Lấy bài học theo module
```
GET /api/v1/modules/{moduleId}/lessons?lang=vi
```
Trả về: `List<LessonResponse>`

### LessonResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID bài học |
| moduleId | UUID | ID module chứa |
| sortOrder | int | Thứ tự |
| durationMinutes | int | Thời lượng (phút) |
| status | String | `PUBLISHED` |
| title / description | String | Theo `lang` |

## 3. Lấy chi tiết bài học (kèm khối nội dung)
```
GET /api/v1/lessons/{id}?lang=vi
```
Trả về chi tiết bài học và `contentBlocks` (TEXT, IMAGE, CHORD_DIAGRAM, PRACTICE_INSTRUCTION, …) dùng để render trang học.

## Nội dung bài học hợp âm mẫu
- Hợp âm Am, Em, G, C, D (kèm biểu đồ vách ngón từ `music.chords`)
