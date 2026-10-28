# Ứng Dụng Hỗ Trợ Mượn Sách & AOP Logging (Spring MVC)

Ứng dụng quản lý mượn/trả sách thư viện được xây dựng theo kiến trúc Spring MVC kết hợp Spring Data JPA, Hibernate, Thymeleaf và Aspect-Oriented Programming (AOP).

---

## 1. Cấu Trúc Dự Án

```
d:\CodeGym\Module4\Bai9\ExpMuonSach/
├── build.gradle
├── settings.gradle
└── src/
    ├── main/
    │   ├── java/com/codegym/
    │   │   ├── aspect/
    │   │   │   ├── BookStateAspect.java      # Ghi log khi trạng thái sách thay đổi (@AfterReturning, @AfterThrowing)
    │   │   │   └── VisitorCountAspect.java   # Đếm & ghi log lượt ghé thăm (@Before)
    │   │   ├── configuration/
    │   │   │   ├── AppConfiguration.java     # Cấu hình Spring MVC, JPA, Thymeleaf, @EnableAspectJAutoProxy
    │   │   │   └── AppInit.java              # Cấu hình DispatcherServlet & UTF-8 Filter
    │   │   ├── controller/
    │   │   │   └── BookController.java       # Điều hướng & xử lý ngoại lệ @ExceptionHandler
    │   │   ├── exception/
    │   │   │   ├── OutOfStockException.java          # Khi số lượng sách = 0
    │   │   │   └── InvalidBorrowCodeException.java   # Khi mã mượn sai hoặc đã trả
    │   │   ├── model/
    │   │   │   ├── Book.java                 # Thực thể đầu sách (id, title, author, quantity, description)
    │   │   │   └── BorrowRecord.java         # Bản ghi mượn sách (borrowCode 5 số, book, borrowDate, returned)
    │   │   ├── repository/
    │   │   │   ├── IBookRepository.java
    │   │   │   └── IBorrowRecordRepository.java
    │   │   └── service/
    │   │       ├── IBookService.java
    │   │       └── BookServiceImpl.java      # Nghiệp vụ mượn sách, trả sách, sinh mã 5 số ngẫu nhiên
    │   └── webapp/WEB-INF/views/
    │       ├── layout.html                   # Giao diện chung (Bootstrap 5, badge lượt ghé thăm)
    │       ├── books/
    │       │   ├── list.html                 # Danh sách sách và danh sách mã mượn đang lưu hành
    │       │   ├── detail.html               # Chi tiết sách & nút mượn sách
    │       │   ├── borrow-success.html       # Cấp và hiển thị mã mượn 5 chữ số
    │       │   ├── return.html               # Màn hình điền mã 5 chữ số để trả sách
    │       │   └── return-success.html       # Báo trả sách thành công & cập nhật số lượng
    │       └── error/
    │           ├── out-of-stock.html         # Màn hình báo lỗi khi hết sách (OutOfStockException)
    │           ├── invalid-code.html         # Màn hình báo lỗi khi sai mã mượn (InvalidBorrowCodeException)
    │           └── general-error.html        # Màn hình lỗi chung
    └── test/java/com/codegym/
        ├── BookServiceTest.java              # Kiểm thử nghiệp vụ mượn/trả sách & ném ngoại lệ
        └── AspectLoggingTest.java            # Kiểm thử hoạt động của AOP Aspect
```

---

## 2. Chi Tiết Các Chức Năng Đạt Được

### A. Quản lý kho sách và Mượn sách
1. **Liệt kê danh sách**: Xem tất cả các đầu sách kèm số lượng tồn kho (quantity).
2. **Chi tiết & Mượn sách**: 
   - Đi đến trang chi tiết sách (`/books/{id}`) và nhấn "Xác Nhận Mượn Cuốn Sách Này".
   - Số lượng sách (`quantity`) giảm đi 1.
   - Hệ thống tự động sinh một mã số mượn sách ngẫu nhiên gồm 5 chữ số (`10000` - `99999`).
   - Màn hình chúc mừng hiển thị mã mượn sách nổi bật để người dùng lưu lại.
3. **Xử lý hết sách (`OutOfStockException`)**:
   - Nếu cuốn sách có số lượng bằng 0 (ví dụ cuốn "Số Đỏ"), hệ thống sẽ kích hoạt ngoại lệ `OutOfStockException`.
   - Controller bắt ngoại lệ qua `@ExceptionHandler(OutOfStockException.class)` và chuyển tới màn hình lỗi `error/out-of-stock.html`.

### B. Trả sách
1. **Điền mã trả sách**: Người dùng vào màn hình `/books/return` và nhập mã mượn 5 chữ số.
2. **Xử lý trả sách**:
   - Nếu mã hợp lệ: Số lượng cuốn sách tương ứng được cộng thêm 1, trạng thái mã mượn chuyển thành đã hoàn tất.
   - Chuyển tới màn hình báo trả sách thành công (`books/return-success.html`).
3. **Xử lý sai mã (`InvalidBorrowCodeException`)**:
   - Nếu mã không tồn tại hoặc đã được trả trước đó, hệ thống ném ngoại lệ `InvalidBorrowCodeException`.
   - Controller bắt ngoại lệ qua `@ExceptionHandler(InvalidBorrowCodeException.class)` và chuyển tới màn hình lỗi `error/invalid-code.html`.

### C. AOP Logging (Lập trình hướng khía cạnh)
1. **Ghi log thay đổi trạng thái sách**:
   - Được cài đặt trong `BookStateAspect.java` với `@AfterReturning` trên phương thức `borrowBook()` và `returnBook()`.
   - Ghi lại chi tiết: tên sách, mã mượn, số lượng còn lại, thời gian thực hiện.
   - `@AfterThrowing` ghi log khi có thao tác mượn/trả bị ném ngoại lệ.
2. **Ghi log số lượng người đã ghé thăm/thao tác thư viện**:
   - Được cài đặt trong `VisitorCountAspect.java` sử dụng Advice `@Before` (một Join Point khác với `@AfterThrowing` theo đúng gợi ý của đề bài).
   - Pointcut bắt tất cả các thao tác trên `BookController.*(..)`.
   - Tăng biến đếm `AtomicInteger` và ghi log: tổng lượt ghé thăm, thao tác vừa thực hiện, thời gian thực hiện.
   - Số lượt ghé thăm cũng được đưa lên giao diện thanh điều hướng navbar để người dùng dễ theo dõi trực quan.

---

## 3. Cấu Hình Cơ Sở Dữ Liệu MySQL
File cấu hình tại [AppConfiguration.java](file:///d:/CodeGym/Module4/Bai9/ExpMuonSach/src/main/java/com/codegym/configuration/AppConfiguration.java):
- **Database URL**: `jdbc:mysql://localhost:3306/book_borrow_db?createDatabaseIfNotExist=true`
- **Username**: `root`
- **Password**: `123456`
- **Tự động khởi tạo dữ liệu mẫu**: Khi khởi chạy lần đầu, ứng dụng tự động nạp 5 đầu sách kinh điển (trong đó có cuốn có số lượng 0 để test hết sách ngay).

---

## 4. Kiểm Thử Dự Án
Chạy lệnh kiểm thử tự động:
```powershell
.\gradlew test
```
Tất cả các bài kiểm thử unit test cho nghiệp vụ mượn sách, trả sách, phát sinh ngoại lệ và khía cạnh AOP đều hoàn thành thành công 100%.
