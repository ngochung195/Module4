# Spring Boot Weather Forecast Webservice

Webservice dự báo thời tiết sử dụng **Spring Boot**, **Spring Data JPA (H2 Database)** và **Spring Security**.

## Chạy ứng dụng

```bash
mvn spring-boot:run
```

## Endpoint

`GET /weather/{username}/{password}/{location}`

### Tài khoản mặc định (khởi tạo sẵn trong database)

| Username | Password | Role |
|---|---|---|
| `user` | `123456` | `ROLE_USER` |
| `admin` | `admin123` | `ROLE_ADMIN` |

### Danh sách địa phương có sẵn

`hanoi`, `hochiminh`, `danang`, `hue`, `nhatrang`, `dalat`, `haiphong`, `cantho`

### Ví dụ gọi API

- Thành công (`200 OK`):
  ```bash
  curl -i http://localhost:8080/weather/user/123456/hanoi
  ```
- Địa phương không tồn tại (`400 Bad Request`):
  ```bash
  curl -i http://localhost:8080/weather/user/123456/saigon
  ```
- Sai thông tin xác thực (`401 Unauthorized`):
  ```bash
  curl -i http://localhost:8080/weather/user/wrongpass/hanoi
  ```

## H2 Console

Truy cập `http://localhost:8080/h2-console` với JDBC URL `jdbc:h2:mem:weatherdb`, Username `sa`, Password để trống.
