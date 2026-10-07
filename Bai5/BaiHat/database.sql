-- Tạo cơ sở dữ liệu music_db
CREATE DATABASE IF NOT EXISTS music_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE music_db;

-- Tạo bảng songs
CREATE TABLE IF NOT EXISTS songs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    file_path VARCHAR(255) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Thêm một số dữ liệu mẫu (nếu muốn)
INSERT INTO songs (name, artist, genre, file_path) VALUES
('Chúng Ta Của Hiện Tại', 'Sơn Tùng M-TP', 'Pop', 'chung_ta_cua_hien_tai.mp3'),
('Nàng Thơ', 'Hoàng Dũng', 'Ballad', 'nang_tho.mp3'),
('Bật Tình Yêu Lên', 'Tăng Duy Tân x Hòa Minzy', 'Pop / Dance', 'bat_tinh_yeu_len.mp3');
