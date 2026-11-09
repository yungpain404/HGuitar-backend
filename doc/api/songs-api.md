# API — Bài hát dễ đánh (Songs)

Base path: `GET /api/v1/songs`

Bao gồm bài hát Việt Nam và nước ngoài, kèm hợp âm cần biết, key, capo, cách đánh công thức và phân khúc (Intro, Verse, Chorus, …).

## 1. Lấy danh sách bài hát
```
GET /api/v1/songs?source=VIETNAMESE&difficulty=BEGINNER&lang=vi
```
Query params:
- `source` (tùy chọn): `VIETNAMESE` | `FOREIGN`
- `difficulty` (tùy chọn): `BEGINNER` | `INTERMEDIATE` | `ADVANCED`
- `lang` (mặc định `vi`): `vi` | `en`

Trả về: `List<SongResponse>`

## 2. Lấy chi tiết 1 bài hát (kèm hợp âm & phân khúc)
```
GET /api/v1/songs/{id}?lang=vi
```
Trả về: `SongResponse`

### SongResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID bài hát |
| guitarType | String | `ACOUSTIC` | `CLASSICAL` |
| difficulty | String | `BEGINNER` |
| source | String | `VIETNAMESE` | `FOREIGN` |
| originalKey | String | Key gốc (vd: "C", "G", "Am") |
| capoFret | int | Phím đệm capo (mặc định 0) |
| bpm | Integer | Nhịp độ |
| strummingPatternId | UUID | Công thức đánh đề xuất (tham khảo /api/v1/strumming-patterns) |
| isFormula | boolean | true = dùng công thức đánh người mới |
| status | String | `PUBLISHED` |
| title / artist / description | String | Tiêu đề, nghệ sĩ, mô tả theo `lang` |
| chords | List<SongChordResponse> | Hợp âm cần biết để chơi bài |
| sections | List<SongSectionResponse> | Phân khúc + dàn hợp âm |

### SongChordResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID liên kết |
| chordId | UUID | ID hợp âm (tham khảo /api/v1/chords/{id}) |
| chordName | String | Tên hợp âm (vd: "C", "Am") |
| sortOrder | int | Thứ tự |
| isRequired | boolean | Bắt buộc phải biết |

### SongSectionResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID phân khúc |
| sectionType | String | `INTRO` | `VERSE` | `CHORUS` | `BRIDGE` | `OUTRO` |
| sectionOrder | int | Thứ tự phân khúc |
| name | String | Tên phân khúc (vd: "Verse 1") |
| chordProgression | String | Dàn hợp âm (vd: "C | G | Am | F") |
| strummingPatternId | UUID | Công thức đánh riêng cho phân khúc (nếu có) |

## 3. Tạo bài hát mới (admin)
```
POST /api/v1/songs
Content-Type: application/json

{
  "guitarType": "ACOUSTIC",
  "difficulty": "BEGINNER",
  "source": "VIETNAMESE",
  "originalKey": "C",
  "capoFret": 0,
  "bpm": 95,
  "strummingPatternId": "<uuid-cong-thuc-1>",
  "isFormula": true,
  "translations": [
    {"language": "vi", "title": "Là Anh Mà", "artist": "Orange", "description": "..."},
    {"language": "en", "title": "La Anh Ma", "artist": "Orange", "description": "..."}
  ],
  "chords": [
    {"chordId": "<uuid-C>", "sortOrder": 1, "isRequired": true},
    {"chordId": "<uuid-G>", "sortOrder": 2, "isRequired": true}
  ],
  "sections": [
    {"sectionType": "VERSE", "sectionOrder": 1, "name": "Verse", "chordProgression": "C | G | Am | F", "strummingPatternId": null}
  ]
}
```
Trả về: `SongResponse`

## Bài hát mẫu đã seed
**Việt Nam:** Là Anh Mà (Orange), Cơn Mưa Ngang Qua (Trịnh Công Sơn), Nếu Như Anh Đến (Hà Anh Tuấn)
**Nước ngoài:** Stand By Me (Ben E. King), Wonderwall (Oasis, capo 2), Let It Be (The Beatles), Riptide (Vance Joy), Horse with No Name (America), Someone Like You (Adele)
