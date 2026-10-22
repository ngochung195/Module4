USE blog_management;
SET NAMES 'utf8mb4';

-- Thêm danh mục mẫu
INSERT INTO categories (id, name) VALUES 
(1, 'Lập trình Java & Spring'),
(2, 'Công nghệ thông tin'),
(3, 'Cơ sở dữ liệu & SQL'),
(4, 'Frontend & Thiết kế Web'),
(5, 'Kinh nghiệm lập trình')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Gán danh mục cho bài viết cũ nếu chưa có
UPDATE blogs SET category_id = 1 WHERE id = 1;

-- Thêm các bài viết mẫu
INSERT INTO blogs (title, summary, content, createdAt, category_id) VALUES
('Làm quen với Spring Boot và JPA trong Java', 
 'Hướng dẫn cơ bản cách cấu hình Spring Data JPA và Hibernate để kết nối cơ sở dữ liệu MySQL.', 
 'Spring Boot kết hợp cùng Spring Data JPA giúp các nhà phát triển đơn giản hóa tầng truy xuất dữ liệu (Data Access Layer). Bạn chỉ cần định nghĩa các interface kế thừa từ JpaRepository là có thể sử dụng các thao tác CRUD mà không cần viết câu lệnh SQL thủ công.', 
 DATE_SUB(NOW(), INTERVAL 5 DAY), 
 1),

('Tìm hiểu về kiến trúc MVC trong Spring Framework', 
 'Phân tích chi tiết mô hình Model - View - Controller và cách Spring Web MVC xử lý HTTP Request.', 
 'Mô hình MVC (Model-View-Controller) tách biệt rõ ràng giữa dữ liệu nghiệp vụ, giao diện người dùng và luồng điều khiển. DispatcherServlet đóng vai trò là Front Controller tiếp nhận request, chuyển tiếp tới HandlerMapping và trả kết quả cho ViewResolver hiển thị cho người dùng.', 
 DATE_SUB(NOW(), INTERVAL 4 DAY), 
 1),

('Tối ưu hóa truy vấn trong cơ sở dữ liệu MySQL', 
 'Các kỹ thuật đánh chỉ mục (Index), phân tích Explain plan và tối ưu hóa câu lệnh truy vấn phức tạp.', 
 'Khi kích thước dữ liệu tăng cao, các truy vấn không dùng chỉ mục (Index) sẽ gây hiện tượng Full Table Scan làm chậm hệ thống nghiêm trọng. Hãy luôn sử dụng EXPLAIN để kiểm tra execution plan, đồng thời tối ưu câu lệnh JOIN và tránh dùng SELECT * trong môi trường production.', 
 DATE_SUB(NOW(), INTERVAL 3 DAY), 
 3),

('Xu hướng công nghệ AI và lập trình viên năm 2026', 
 'Trí tuệ nhân tạo đang thay đổi cách lập trình viên làm việc và các kỹ năng cần trang bị trong kỷ nguyên mới.', 
 'Sự phát triển vượt bậc của các mô hình AI agentic và coding assistant đang giúp lập trình viên tăng năng suất gấp nhiều lần. Thay vì lo sợ bị thay thế, các lập trình viên hiện đại đang học cách làm chủ các công cụ AI để thiết kế kiến trúc hệ thống nhanh hơn và chính xác hơn.', 
 DATE_SUB(NOW(), INTERVAL 2 DAY), 
 2),

('Hướng dẫn sử dụng Thymeleaf với layout Bootstrap', 
 'Tích hợp Thymeleaf fragment và template engine để tạo giao diện website nhất quán và chuyên nghiệp.', 
 'Thymeleaf là template engine chuẩn được khuyên dùng cùng Spring. Nhờ cơ chế Natural Templating, bạn có thể xem trước giao diện HTML tĩnh trên trình duyệt mà không cần chạy server. Việc phối hợp Thymeleaf với Bootstrap 5 giúp thiết kế các trang admin nhanh chóng.', 
 DATE_SUB(NOW(), INTERVAL 1 DAY), 
 4),

('Lời khuyên cho lập trình viên mới bắt đầu học Backend', 
 'Những bài học đúc kết về tư duy logic, cấu trúc dữ liệu, giải thuật và xây dựng dự án thực tế.', 
 'Để trở thành một backend developer giỏi, hãy tập trung nắm vững các khái niệm nền tảng: OOP, cấu trúc dữ liệu, mạng máy tính (HTTP/HTTPS, REST), và cơ sở dữ liệu quan hệ trước khi nhảy vào các framework phức tạp. Hãy kiên trì xây dựng từng dự án từ đơn giản đến nâng cao.', 
 NOW(), 
 5);
