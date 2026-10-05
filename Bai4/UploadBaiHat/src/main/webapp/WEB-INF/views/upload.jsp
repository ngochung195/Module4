<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Song</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: #f0f2f5;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }
        .card {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            width: 100%;
            max-width: 540px;
            padding: 32px;
        }
        .header {
            text-align: center;
            margin-bottom: 24px;
        }
        .header h1 {
            font-size: 26px;
            color: #1e293b;
            font-weight: 700;
        }
        .header p {
            font-size: 14px;
            color: #64748b;
            margin-top: 6px;
        }
        .alert-error {
            background-color: #fee2e2;
            border: 1px solid #f87171;
            color: #b91c1c;
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .form-group {
            margin-bottom: 18px;
        }
        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            font-weight: 600;
            color: #475569;
        }
        .form-group input[type="text"],
        .form-group input[type="file"] {
            width: 100%;
            padding: 10px 14px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.2s, box-shadow 0.2s;
            outline: none;
            background: #fff;
        }
        .form-group input[type="text"]:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
        }
        .form-group input[type="file"] {
            padding: 8px 10px;
            cursor: pointer;
        }
        .hint {
            font-size: 12px;
            color: #64748b;
            margin-top: 4px;
        }
        .btn-submit {
            width: 100%;
            background: #2563eb;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s, transform 0.1s;
            margin-top: 8px;
        }
        .btn-submit:hover {
            background: #1d4ed8;
        }
        .btn-submit:active {
            transform: scale(0.99);
        }
        .footer-links {
            text-align: center;
            margin-top: 20px;
        }
        .footer-links a {
            color: #2563eb;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
        }
        .footer-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="card">
    <div class="header">
        <h1>Upload Song</h1>
        <p>Nhập thông tin bài hát và chọn file âm thanh để tải lên</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert-error">
            <span>⚠</span>
            <span><c:out value="${errorMessage}"/></span>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/songs/upload" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">Tên bài hát</label>
            <input type="text" id="name" name="name" value="<c:out value='${song.name}'/>" placeholder="Ví dụ: Em Của Ngày Hôm Qua" required>
        </div>

        <div class="form-group">
            <label for="artist">Nghệ sĩ thể hiện</label>
            <input type="text" id="artist" name="artist" value="<c:out value='${song.artist}'/>" placeholder="Ví dụ: Sơn Tùng M-TP" required>
        </div>

        <div class="form-group">
            <label for="genre">Thể loại nhạc</label>
            <input type="text" id="genre" name="genre" value="<c:out value='${song.genreFormatted}'/>" placeholder="Ví dụ: Pop, Ballad, Rock" required>
            <div class="hint">Nhập các thể loại phân cách bằng dấu phẩy (Ví dụ: Pop, Ballad, Rock)</div>
        </div>

        <div class="form-group">
            <label for="file">File bài hát</label>
            <input type="file" id="file" name="file" accept=".mp3,.wav,.ogg,.m4p" required>
            <div class="hint">Chỉ chấp nhận file .mp3, .wav, .ogg, .m4p</div>
        </div>

        <button type="submit" class="btn-submit">Upload</button>
    </form>

    <div class="footer-links">
        <a href="${pageContext.request.contextPath}/songs">← Quay lại danh sách bài hát</a>
    </div>
</div>

</body>
</html>
