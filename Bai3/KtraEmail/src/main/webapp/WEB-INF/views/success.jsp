<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả kiểm tra</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .card {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            padding: 40px;
            width: 100%;
            max-width: 420px;
            text-align: center;
        }
        .icon {
            width: 64px;
            height: 64px;
            background: #d1fae5;
            color: #059669;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-size: 32px;
            margin-bottom: 20px;
        }
        h2 {
            color: #065f46;
            margin-bottom: 12px;
            font-size: 22px;
        }
        p {
            color: #4b5563;
            font-size: 15px;
            margin-bottom: 8px;
        }
        .email-display {
            font-weight: 600;
            color: #1f2937;
            background: #f3f4f6;
            padding: 8px 12px;
            border-radius: 6px;
            display: inline-block;
            margin: 10px 0 24px 0;
            word-break: break-all;
        }
        .btn-back {
            display: inline-block;
            background: #059669;
            color: #ffffff;
            text-decoration: none;
            padding: 10px 24px;
            font-size: 15px;
            font-weight: 600;
            border-radius: 8px;
            transition: background 0.2s, transform 0.1s;
        }
        .btn-back:hover {
            background: #047857;
        }
        .btn-back:active {
            transform: scale(0.98);
        }
    </style>
</head>
<body>
    <div class="card">
        <div class="icon">✓</div>
        <h2>Email Hợp Lệ!</h2>
        <p>Địa chỉ email bạn vừa nhập là:</p>
        <div class="email-display">${email}</div>
        <div>
            <a href="${pageContext.request.contextPath}/" class="btn-back">Quay lại</a>
        </div>
    </div>
</body>
</html>
