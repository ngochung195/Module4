<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Spring User Model</title>
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
            background: linear-gradient(135deg, #0f172a 0%, #1e1b4b 50%, #311042 100%);
            padding: 20px;
            color: #f8fafc;
        }

        .login-card {
            width: 100%;
            max-width: 440px;
            background: rgba(30, 41, 59, 0.75);
            backdrop-filter: blur(16px);
            border: 1px solid rgba(255, 255, 255, 0.12);
            border-radius: 24px;
            padding: 40px;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5), 0 0 40px rgba(99, 102, 241, 0.2);
            animation: fadeIn 0.6s ease-out;
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

        .header {
            text-align: center;
            margin-bottom: 32px;
        }

        .logo-icon {
            width: 56px;
            height: 56px;
            margin: 0 auto 16px;
            background: linear-gradient(135deg, #6366f1, #a855f7);
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            box-shadow: 0 10px 25px rgba(99, 102, 241, 0.4);
        }

        .header h1 {
            font-size: 24px;
            font-weight: 700;
            color: #ffffff;
            letter-spacing: -0.5px;
        }

        .header p {
            font-size: 14px;
            color: #94a3b8;
            margin-top: 6px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: #cbd5e1;
            margin-bottom: 8px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .form-input {
            width: 100%;
            padding: 14px 16px;
            background: rgba(15, 23, 42, 0.6);
            border: 1px solid rgba(148, 163, 184, 0.2);
            border-radius: 12px;
            font-size: 15px;
            color: #f8fafc;
            outline: none;
            transition: all 0.25s ease;
        }

        .form-input:focus {
            border-color: #6366f1;
            background: rgba(15, 23, 42, 0.85);
            box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.2);
        }

        .form-input::placeholder {
            color: #64748b;
        }

        .submit-btn {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
            border: none;
            border-radius: 12px;
            color: #ffffff;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 10px 20px rgba(99, 102, 241, 0.3);
            margin-top: 10px;
        }

        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 14px 28px rgba(99, 102, 241, 0.45);
        }

        .submit-btn:active {
            transform: translateY(0);
        }

        .hint-box {
            margin-top: 24px;
            padding: 14px;
            background: rgba(15, 23, 42, 0.5);
            border: 1px dashed rgba(148, 163, 184, 0.25);
            border-radius: 12px;
            font-size: 12px;
            color: #94a3b8;
            line-height: 1.6;
        }

        .hint-box strong {
            color: #a5b4fc;
        }

        .account-list {
            margin-top: 6px;
            display: flex;
            flex-direction: column;
            gap: 2px;
            font-family: monospace;
            font-size: 11.5px;
            color: #e2e8f0;
        }
    </style>
</head>
<body>

<div class="login-card">
    <div class="header">
        <div class="logo-icon">🔐</div>
        <h1>Đăng nhập hệ thống</h1>
        <p>Spring MVC + Jakarta EE 10</p>
    </div>

    <form:form action="${pageContext.request.contextPath}/login" method="post" modelAttribute="login">
        <div class="form-group">
            <form:label path="account" cssClass="form-label">Tài khoản (Account)</form:label>
            <form:input path="account" cssClass="form-input" placeholder="Nhập tài khoản (vd: john, bill, mike)" required="required" autocomplete="username"/>
        </div>

        <div class="form-group">
            <form:label path="password" cssClass="form-label">Mật khẩu (Password)</form:label>
            <form:password path="password" cssClass="form-input" placeholder="Nhập mật khẩu" required="required" autocomplete="current-password"/>
        </div>

        <button type="submit" class="submit-btn">Đăng nhập</button>
    </form:form>

    <div class="hint-box">
        <strong>Tài khoản mẫu để thử nghiệm:</strong>
        <div class="account-list">
            <div>• john / 123456 (John, 10 tuổi)</div>
            <div>• bill / 123456 (Bill, 15 tuổi)</div>
            <div>• mike / 123456 (Mike, 16 tuổi)</div>
        </div>
    </div>
</div>

</body>
</html>
