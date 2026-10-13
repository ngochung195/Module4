USE product_province_data_repository;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE customers;
TRUNCATE TABLE provinces;
SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;

-- Dữ liệu mẫu Tỉnh thành (Provinces)
INSERT INTO provinces (id, name) VALUES 
(1, 'Hà Nội'),
(2, 'TP. Hồ Chí Minh'),
(3, 'Đà Nẵng'),
(4, 'Hải Phòng'),
(5, 'Cần Thơ');

-- Dữ liệu mẫu Khách hàng (Customers)
INSERT INTO customers (id, firstName, lastName, email, province_id) VALUES 
(1, 'Nguyễn Văn', 'An', 'an.nguyen@gmail.com', 1),
(2, 'Trần Thị', 'Mai', 'mai.tran@gmail.com', 1),
(3, 'Lê Hoàng', 'Nam', 'nam.le@gmail.com', 2),
(4, 'Phạm Minh', 'Tuấn', 'tuan.pham@gmail.com', 2),
(5, 'Hoàng Thùy', 'Linh', 'linh.hoang@gmail.com', 3),
(6, 'Vũ Quốc', 'Anh', 'anh.vu@gmail.com', 3),
(7, 'Đỗ Bích', 'Phương', 'phuong.do@gmail.com', 4),
(8, 'Ngô Đình', 'Trọng', 'trong.ngo@gmail.com', 5),
(9, 'Bùi Thanh', 'Hằng', 'hang.bui@gmail.com', 5),
(10, 'Đặng Quang', 'Huy', 'huy.dang@gmail.com', 2);
