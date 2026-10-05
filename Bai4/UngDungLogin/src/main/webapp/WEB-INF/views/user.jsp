<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin người dùng - Spring User Model</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Plus Jakarta Sans', sans-serif;
        }

        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #0f172a 0%, #064e3b 50%, #022c22 100%);
            padding: 20px;
            color: #f8fafc;
        }

        .profile-card {
            width: 100%;
            max-width: 480px;
            background: rgba(30, 41, 59, 0.85);
            backdrop-filter: blur(16px);
            border: 1px solid rgba(52, 211, 153, 0.25);
            border-radius: 24px;
            padding: 40px;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5), 0 0 40px rgba(16, 185, 129, 0.2);
            animation: fadeIn 0.6s ease-out;
            text-align: center;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .avatar {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            background: linear-gradient(135deg, #10b981, #059669);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 36px;
            box-shadow: 0 10px 25px rgba(16, 185, 129, 0.35);
        }

        .welcome-title {
            font-size: 24px;
            font-weight: 700;
            color: #ffffff;
            margin-bottom: 6px;
        }

        .welcome-subtitle {
            font-size: 14px;
            color: #34d399;
            margin-bottom: 28px;
            font-weight: 500;
        }

        .info-table {
            width: 100%;
            text-align: left;
            border-collapse: collapse;
            background: rgba(15, 23, 42, 0.6);
            border-radius: 16px;
            overflow: hidden;
            border: 1px solid rgba(148, 163, 184, 0.15);
            margin-bottom: 28px;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            padding: 14px 20px;
            border-bottom: 1px solid rgba(148, 163, 184, 0.1);
        }

        .info-row:last-child {
            border-bottom: none;
        }

        .info-label {
            font-size: 13px;
            font-weight: 600;
            color: #94a3b8;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .info-value {
            font-size: 14px;
            font-weight: 600;
            color: #f8fafc;
        }

        .badge-age {
            background: rgba(16, 185, 129, 0.2);
            color: #34d399;
            padding: 3px 10px;
            border-radius: 9999px;
            font-size: 12px;
        }

        .action-btn {
            display: inline-block;
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            border: none;
            border-radius: 12px;
            color: #ffffff;
            font-size: 15px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 10px 20px rgba(16, 185, 129, 0.3);
        }

        .action-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 14px 28px rgba(16, 185, 129, 0.45);
        }
    </style>
</head>
<body>

<div class="profile-card">
    <div class="avatar">👤</div>
    <h1 class="welcome-title">Xin chào, ${user.name}!</h1>
    <p class="welcome-subtitle">Đăng nhập thành công vào hệ thống</p>

    <div class="info-table">
        <div class="info-row">
            <span class="info-label">Tài khoản</span>
            <span class="info-value">${user.account}</span>
        </div>
        <div class="info-row">
            <span class="info-label">Họ và tên</span>
            <span class="info-value">${user.name}</span>
        </div>
        <div class="info-row">
            <span class="info-label">Email</span>
            <span class="info-value">${user.email}</span>
        </div>
        <div class="info-row">
            <span class="info-label">Tuổi</span>
            <span class="info-value badge-age">${user.age} tuổi</span>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/home" class="action-btn">Đăng xuất</a>
</div>

</body>
</html>
