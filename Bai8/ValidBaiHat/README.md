# Ứng Dụng Quản Lý & Validate Thông Tin Bài Hát (Spring MVC)
> **CodeGym - Module 4: Bài 8 (Validation)**

Ứng dụng web được xây dựng chuẩn theo mô hình **Spring MVC** (Java Configuration + Thymeleaf + JSR-380 Bean Validation / Hibernate Validator), đóng gói dạng `WAR` và có thể triển khai trên Tomcat hoặc chạy trực tiếp bằng lệnh Maven.

---

## 🛠️ Kiến Trúc Công Nghệ Chuẩn Spring MVC

* **Framework:** Spring MVC 5.3.x (`spring-webmvc`, `spring-context`)
* **View Engine:** Thymeleaf Spring 5 (`thymeleaf-spring5`) với bộ phân giải view tại `/WEB-INF/views/`
* **Validation:** Hibernate Validator 6.2.x (`javax.validation` / JSR-380 API)
* **Packaging:** `war` (chuẩn web application deploy lên Tomcat)
* **Cấu hình Spring:** Java-based Config (`AppConfiguration` & `AppInitializer` thay thế cho `web.xml`)

---

## 🛡️ Ràng Buộc Validate (Validation Rules)

Dự án áp dụng đầy đủ và nghiêm ngặt các quy tắc validate theo yêu cầu của bài tập:

| Trường thông tin | Yêu cầu validate | Triển khai trong `SongDto.java` |
| :--- | :--- | :--- |
| **1. Tên bài hát (`name`)** | • Bắt buộc nhập (không để trống)<br>• Tối đa 800 ký tự<br>• Không chứa ký tự đặc biệt (`@ ; , . = - + ...`) | `@NotBlank(message = "Tên bài hát không được để trống")`<br>`@Size(max = 800, message = "Tên bài hát không được vượt quá 800 ký tự")`<br>`@Pattern(regexp = "^[\\p{L}\\p{N}\\s]+$", message = "Tên bài hát không được chứa các ký tự đặc biệt (@, ;, ,, ., =, -, +, ...)")` |
| **2. Nghệ sĩ thể hiện (`artist`)** | • Bắt buộc nhập (không để trống)<br>• Tối đa 300 ký tự<br>• Không chứa ký tự đặc biệt (`@ ; , . = - + ...`) | `@NotBlank(message = "Nghệ sĩ thể hiện không được để trống")`<br>`@Size(max = 300, message = "Nghệ sĩ thể hiện không được vượt quá 300 ký tự")`<br>`@Pattern(regexp = "^[\\p{L}\\p{N}\\s]+$", message = "Nghệ sĩ thể hiện không được chứa các ký tự đặc biệt (@, ;, ,, ., =, -, +, ...)")` |
| **3. Thể loại nhạc (`genre`)** | • Nhập thông tin theo dạng text<br>• Bắt buộc nhập (không để trống)<br>• Tối đa 1000 ký tự<br>• **Chỉ cho phép dấu phẩy `,`** để phân cách giữa các thể loại | `@NotBlank(message = "Thể loại nhạc không được để trống")`<br>`@Size(max = 1000, message = "Thể loại nhạc không được vượt quá 1000 ký tự")`<br>`@Pattern(regexp = "^[\\p{L}\\p{N}\\s,]+$", message = "Thể loại nhạc không được chứa các ký tự đặc biệt ngoại trừ dấu phẩy (\",\")")` |

---

## 📂 Cấu trúc dự án Spring MVC

```
ValidBaiHat/
├── pom.xml                                    # Cấu hình Spring MVC, Thymeleaf, Hibernate Validator, war packaging
├── src/main/java/com/codegym/validsong/
│   ├── MainApp.java                           # Bộ chạy Tomcat nhúng để chạy nhanh bằng Maven
│   ├── configuration/
│   │   ├── AppConfiguration.java              # Cấu hình WebMvcConfigurer, Thymeleaf TemplateResolver, ResourceHandlers
│   │   └── AppInitializer.java                # AbstractAnnotationConfigDispatcherServletInitializer & UTF-8 Filter
│   ├── controller/
│   │   └── SongController.java               # Controller xử lý CRUD và bắt lỗi BindingResult
│   ├── dto/
│   │   └── SongDto.java                      # Form DTO chứa các annotation javax.validation
│   ├── model/
│   │   └── Song.java                         # Model bài hát
│   └── service/
│       ├── ISongService.java                 # Interface service
│       └── SongService.java                  # Quản lý danh sách bài hát & seed data mẫu
└── src/main/webapp/
    ├── WEB-INF/views/song/
    │   ├── list.html                         # Giao diện danh sách bài hát & Live Search
    │   ├── create.html                       # Giao diện thêm mới & bảng quy định validate
    │   └── edit.html                         # Giao diện chỉnh sửa bài hát
    └── static/
        ├── css/style.css                     # Giao diện Dark Glassmorphism, animations
        └── js/main.js                        # Kiểm tra lỗi thời gian thực, đếm ký tự, xem trước
```

---

## 🚀 Hướng Dẫn Chạy Ứng Dụng

### Cách 1: Chạy trực tiếp bằng dòng lệnh (Terminal / PowerShell)
Trong thư mục dự án `d:\CodeGym\Module4\Bai8\ValidBaiHat`, gõ lệnh:
```bash
mvn exec:java
```
Ứng dụng sẽ khởi động Tomcat và có thể truy cập tại: **[http://localhost:8080](http://localhost:8080)** (hoặc `http://localhost:8080/songs`).

### Cách 2: Chạy trong IntelliJ IDEA với Tomcat Server
1. Mở dự án trong IntelliJ IDEA (`Open` -> chọn thư mục `ValidBaiHat`).
2. Nhấn `Run` -> `Edit Configurations...` -> Thêm mới `Tomcat Server` -> `Local`.
3. Trong tab **Deployment**: Nhấn dấu `+` -> Chọn `Artifact...` -> Chọn `valid-bai-hat:war exploded`.
4. Đặt Application context là `/` hoặc `/songs`.
5. Nhấn **Apply** và **Run**.
