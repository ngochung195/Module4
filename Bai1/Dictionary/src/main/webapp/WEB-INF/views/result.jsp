<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Tra Cứu - Simple Dictionary</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .card {
            background: #ffffff;
            width: 100%;
            max-width: 500px;
            padding: 35px 30px;
            border-radius: 16px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        .card h1 {
            font-size: 24px;
            color: #2d3748;
            margin-bottom: 20px;
            font-weight: 700;
        }

        .result-box {
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 25px;
            text-align: left;
        }

        .result-success {
            background-color: #f0fdf4;
            border: 1px solid #bbf7d0;
        }

        .result-notfound {
            background-color: #fef2f2;
            border: 1px solid #fecaca;
        }

        .result-item {
            margin-bottom: 12px;
        }

        .result-item:last-child {
            margin-bottom: 0;
        }

        .result-label {
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            color: #6b7280;
            font-weight: 600;
        }

        .result-value {
            font-size: 18px;
            font-weight: 600;
            color: #1f2937;
            margin-top: 4px;
        }

        .meaning-value {
            font-size: 20px;
            font-weight: 700;
            color: #16a34a;
            margin-top: 4px;
        }

        .error-message {
            color: #dc2626;
            font-size: 16px;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .btn-back {
            display: inline-block;
            width: 100%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #ffffff;
            font-size: 16px;
            font-weight: 600;
            padding: 14px;
            text-decoration: none;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .btn-back:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.35);
        }
    </style>
</head>
<body>

<div class="card">
    <h1>📋 Kết Quả Tra Cứu</h1>

    <c:choose>
        <c:when test="${found}">
            <div class="result-box result-success">
                <div class="result-item">
                    <div class="result-label">Từ tiếng Anh</div>
                    <div class="result-value">${keyword}</div>
                </div>
                <div class="result-item" style="margin-top: 15px;">
                    <div class="result-label">Nghĩa tiếng Việt</div>
                    <div class="meaning-value">✨ ${meaning}</div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="result-box result-notfound">
                <div class="result-item" style="margin-bottom: 10px;">
                    <div class="result-label">Từ đã tra</div>
                    <div class="result-value">${empty keyword ? '(Trống)' : keyword}</div>
                </div>
                <div class="error-message">
                    <span>⚠️</span>
                    <span>${message}</span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/" class="btn-back">⬅️ Quay lại tra cứu</a>
</div>

</body>
</html>
