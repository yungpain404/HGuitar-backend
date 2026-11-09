# OpenAPI / Swagger

Dự án dùng `springdoc-openapi` để tự động sinh tài liệu API từ các annotation `@RestController`, `@Operation`, `@Tag`.

- Swagger UI: `http://localhost:5501/swagger-ui.html`
- OpenAPI JSON: `http://localhost:5501/api-docs`

Cấu hình trong `com.hguitar.common.infrastructure.web.OpenApiConfig` (bearerAuth JWT).

Các controller mới đã thêm tag Swagger:
- `StrummingPatternController` → tag "Strumming Patterns"
- `SongController` → tag "Songs"
- `LearningMethodController` → tag "Learning Methods"

Frontend có thể dùng `/api-docs` để sinh client API (TypeScript/OpenAPI generator) nếu cần.
