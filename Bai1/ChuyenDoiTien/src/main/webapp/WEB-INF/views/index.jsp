<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ứng Dụng Chuyển Đổi Tiền Tệ</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: linear-gradient(135deg, #0f172a 0%, #1e293b 50%, #0f172a 100%);
            color: #f8fafc;
            padding: 20px;
        }

        .card {
            background: rgba(30, 41, 59, 0.85);
            backdrop-filter: blur(12px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 16px;
            padding: 36px 32px;
            width: 100%;
            max-width: 440px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4), 0 0 30px rgba(59, 130, 246, 0.1);
        }

        .header {
            text-align: center;
            margin-bottom: 28px;
        }

        .header .icon {
            font-size: 42px;
            margin-bottom: 8px;
            display: inline-block;
        }

        .header h1 {
            font-size: 24px;
            font-weight: 700;
            color: #ffffff;
            margin-bottom: 6px;
        }

        .header p {
            font-size: 14px;
            color: #94a3b8;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: #cbd5e1;
            margin-bottom: 8px;
        }

        .input-wrapper {
            position: relative;
            display: flex;
            align-items: center;
        }

        .input-wrapper input {
            width: 100%;
            padding: 12px 16px;
            padding-right: 64px;
            background: rgba(15, 23, 42, 0.6);
            border: 1.5px solid rgba(255, 255, 255, 0.12);
            border-radius: 10px;
            font-size: 15px;
            color: #f8fafc;
            transition: all 0.25s ease;
            outline: none;
        }

        .input-wrapper input:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.25);
            background: rgba(15, 23, 42, 0.85);
        }

        .input-unit {
            position: absolute;
            right: 14px;
            font-size: 13px;
            font-weight: 600;
            color: #64748b;
            pointer-events: none;
        }

        .btn-submit {
            width: 100%;
            padding: 14px;
            margin-top: 10px;
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            border: none;
            border-radius: 10px;
            color: #ffffff;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            box-shadow: 0 4px 14px rgba(37, 99, 235, 0.4);
            transition: all 0.25s ease;
        }

        .btn-submit:hover {
            background: linear-gradient(135deg, #60a5fa 0%, #3b82f6 100%);
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(37, 99, 235, 0.5);
        }

        .btn-submit:active {
            transform: translateY(0);
        }

        .footer {
            margin-top: 24px;
            text-align: center;
            font-size: 12px;
            color: #64748b;
        }
    </style>
</head>
<body>
    <div class="card">
        <div class="header">
            <div class="icon">💱</div>
            <h1>Chuyển Đổi Tiền Tệ</h1>
            <p>Quy đổi nhanh từ USD sang VNĐ</p>
        </div>

        <form action="${pageContext.request.contextPath}/convert" method="POST">
            <div class="form-group">
                <label for="rate">Tỉ giá (VNĐ / USD)</label>
                <div class="input-wrapper">
                    <input type="number" id="rate" name="rate" step="any" min="0" placeholder="Ví dụ: 25000" value="25000" required>
                    <span class="input-unit">VNĐ</span>
                </div>
            </div>

            <div class="form-group">
                <label for="usd">Số lượng USD</label>
                <div class="input-wrapper">
                    <input type="number" id="usd" name="usd" step="any" min="0" placeholder="Ví dụ: 100" required>
                    <span class="input-unit">USD</span>
                </div>
            </div>

            <button type="submit" class="btn-submit">Chuyển Đổi Ngay</button>
        </form>

        <div class="footer">
            Spring MVC 6.2.6 &bull; Jakarta EE 10 &bull; Tomcat 10.1+
        </div>
    </div>
</body>
</html>
