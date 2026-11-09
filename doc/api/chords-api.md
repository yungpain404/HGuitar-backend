# API — Hợp âm (Chords)

Base path: `GET /api/v1/chords`

## 1. Lấy danh sách hợp âm
```
GET /api/v1/chords?guitarType=ACOUSTIC
```
Query params:
- `guitarType` (tùy chọn): `ACOUSTIC` | `CLASSICAL`

Trả về: `List<ChordResponse>`

### ChordResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID hợp âm |
| name | String | Tên hợp âm (vd: "Am", "C", "G") |
| guitarType | String | `ACOUSTIC` | `CLASSICAL` |
| description | String | Mô tả hợp âm |
| positions | List<ChordPositionResponse> | Các vách ngón |

### ChordPositionResponse
| Trường | Kiểu | Mô tả |
|--------|------|-------|
| id | UUID | ID vách ngón |
| positionNumber | int | Số thứ tự vách |
| baseFret | int | Phím bắt đầu của biểu đồ |
| fingersJson | String | JSON mảng vị trí ngón: `[{string, fret, finger}]` (string 1–6, fret 0 = buông, -1 = không gảy) |

## 2. Lấy chi tiết 1 hợp âm
```
GET /api/v1/chords/{id}
```
Trả về: `ChordResponse`

## 3. Lấy vách ngón của một hợp âm
```
GET /api/v1/chords/{id}/positions
```
Trả về: `List<ChordPositionResponse>`

## Ví dụ hợp âm đã seed (viết lại cho frontend)
- Am, Em, G, C, D (cơ bản)
- F, Dm, E, A, Em7, Cadd9, Dsus4, A7sus4, G7, Bm, F#m (bổ sung để chơi bài dễ)
