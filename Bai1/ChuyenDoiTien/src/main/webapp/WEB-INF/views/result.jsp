<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Chuyển Đổi Tiền Tệ</title>
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
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4), 0 0 30px rgba(16, 185, 129, 0.1);
        }

        .header {
            text-align: center;
            margin-bottom: 24px;
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

        .result-box {
            background: rgba(15, 23, 42, 0.7);
            border: 1px solid rgba(255, 255, 255, 0.08);
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 24px;
        }

        .result-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px dashed rgba(255, 255, 255, 0.1);
        }

        .result-row:last-child {
            border-bottom: none;
            padding-top: 14px;
            margin-top: 4px;
        }

        .result-label {
            font-size: 14px;
            color: #94a3b8;
        }

        .result-value {
            font-size: 15px;
            font-weight: 600;
            color: #f8fafc;
        }

        .result-value.highlight {
            font-size: 22px;
            font-weight: 700;
            color: #10b981;
            text-shadow: 0 0 12px rgba(16, 185, 129, 0.3);
        }

        .btn-back {
            display: block;
            width: 100%;
            padding: 14px;
            text-align: center;
            text-decoration: none;
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            border-radius: 10px;
            color: #ffffff;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            box-shadow: 0 4px 14px rgba(37, 99, 235, 0.4);
            transition: all 0.25s ease;
        }

        .btn-back:hover {
            background: linear-gradient(135deg, #60a5fa 0%, #3b82f6 100%);
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(37, 99, 235, 0.5);
        }

        .btn-back:active {
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
            <div class="icon">✅</div>
            <h1>Kết Quả Quy Đổi</h1>
            <p>Chi tiết chuyển đổi từ USD sang VNĐ</p>
        </div>

        <div class="result-box">
            <div class="result-row">
                <span class="result-label">Số lượng USD:</span>
                <span class="result-value">
                    <fmt:formatNumber value="${usd}" type="number" maxFractionDigits="2"/> USD
                </span>
            </div>

            <div class="result-row">
                <span class="result-label">Tỉ giá áp dụng:</span>
                <span class="result-value">
                    <fmt:formatNumber value="${rate}" type="number" maxFractionDigits="2"/> VNĐ/USD
                </span>
            </div>

            <div class="result-row">
                <span class="result-label">Tổng tiền VNĐ:</span>
                <span class="result-value highlight">
                    <fmt:formatNumber value="${vnd}" type="number" maxFractionDigits="2"/> VNĐ
                </span>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/" class="btn-back">&larr; Thực hiện chuyển đổi khác</a>

        <div class="footer">
            Spring MVC 6.2.6 &bull; Jakarta EE 10 &bull; Tomcat 10.1+
        </div>
    </div>
</body>
</html>
