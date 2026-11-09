# Tài liệu API — HGuitar Backend

Tài liệu này mô tả các API backend mà frontend gọi để xây dựng trang học guitar cho người mới bắt đầu (hợp âm, bài học, bài hát dễ đánh, cách đánh công thức, phương pháp học).

## Cơ sở
- Base URL: `http://localhost:5501` (cổng cấu hình trong `application.yml`, key `server.port`)
- Swagger UI: `http://localhost:5501/swagger-ui.html`
- OpenAPI JSON: `http://localhost:5501/api-docs`
- Đầu vào/ra định dạng `application/json`.
- Các ID là UUID.
- Ngôn ngữ nội dung: tham số `lang` (`vi` mặc định, `en`).

## Danh sách nhóm API

| Nhóm | File | Mô tả |
|------|------|-------|
| Hợp âm | [chords-api.md](./chords-api.md) | Thư viện hợp âm guitar (Am, C, G, D, F, …) và vách ngón |
| Cách đánh | [strumming-patterns-api.md](./strumming-patterns-api.md) | Công thức đánh đàn (↓ ↓↑↑ ↓ ↑, …) cho người mới |
| Bài hát | [songs-api.md](./songs-api.md) | Thư viện bài hát dễ đánh (VN + nước ngoài), kèm hợp âm & phân khúc |
| Bài học | [lessons-api.md](./lessons-api.md) | Khóa học, bài học, khối nội dung (TEXT, CHORD_DIAGRAM, …) |
| Phương pháp học | [learning-methods-api.md](./learning-methods-api.md) | Phương pháp học phổ biến (4 hợp âm vàng, công thức đánh, …) |

## Quy tắc gọi API phổ biến
- Lấy nội dung hai ngôn ngữ: gửi `?lang=vi` hoặc `?lang=en`.
- Lọc theo loại đàn: `?guitarType=ACOUSTIC` hoặc `CLASSICAL`.
- Lọc bài hát: `?source=VIETNAMESE` hoặc `FOREIGN`, `?difficulty=BEGINNER`.
- Lỗi trả về định dạng Spring `ProblemDetail` (status, title, detail, errors khi validation).

## Cơ sở dữ liệu
- SQL Server, quản lý schema bởi Flyway (`src/main/resources/db/migration`).
- Migration mới nhất thêm: `V13` (cách đánh), `V14` (bài hát), `V15` (phương pháp học), `V16–V19` (seed dữ liệu mẫu), `V20` (hướng dẫn đánh chi tiết cho cách đánh).
