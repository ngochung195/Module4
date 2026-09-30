<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Tính Lương</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #4f46e5;
            --primary-hover: #4338ca;
            --primary-light: #eef2ff;
            --success: #10b981;
            --success-light: #ecfdf5;
            --bg-gradient: linear-gradient(135deg, #f0f4ff 0%, #e0e7ff 50%, #f5f3ff 100%);
            --card-bg: rgba(255, 255, 255, 0.95);
            --text-main: #1e293b;
            --text-muted: #64748b;
            --border: #e2e8f0;
            --shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.08), 0 8px 10px -6px rgba(0, 0, 0, 0.04);
            --radius: 16px;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
            background: var(--bg-gradient);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px;
            color: var(--text-main);
        }

        .container {
            width: 100%;
            max-width: 520px;
            background: var(--card-bg);
            backdrop-filter: blur(12px);
            border: 1px solid rgba(255, 255, 255, 0.8);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 36px;
            animation: fadeIn 0.4s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(12px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .header {
            text-align: center;
            margin-bottom: 24px;
        }

        .badge {
            display: inline-block;
            background: var(--success-light);
            color: var(--success);
            font-size: 13px;
            font-weight: 700;
            padding: 6px 14px;
            border-radius: 9999px;
            margin-bottom: 12px;
            letter-spacing: 0.5px;
            text-transform: uppercase;
        }

        .header h1 {
            font-size: 24px;
            font-weight: 800;
            color: var(--text-main);
            margin-bottom: 6px;
        }

        .salary-highlight-card {
            background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
            border-radius: 14px;
            padding: 24px;
            text-align: center;
            color: #ffffff;
            margin-bottom: 24px;
            box-shadow: 0 10px 20px -5px rgba(79, 70, 229, 0.4);
        }

        .salary-highlight-card .label {
            font-size: 13px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            opacity: 0.85;
            margin-bottom: 6px;
        }

        .salary-highlight-card .amount {
            font-size: 32px;
            font-weight: 800;
            letter-spacing: -0.5px;
        }

        .details-card {
            background: #f8fafc;
            border: 1px solid var(--border);
            border-radius: 14px;
            padding: 20px;
            margin-bottom: 24px;
        }

        .detail-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px dashed var(--border);
            font-size: 14px;
        }

        .detail-row:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }

        .detail-row:first-child {
            padding-top: 0;
        }

        .detail-label {
            color: var(--text-muted);
            font-weight: 500;
        }

        .detail-val {
            font-weight: 700;
            color: var(--text-main);
        }

        .shift-breakdown {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 8px;
            margin-top: 14px;
            padding-top: 14px;
            border-top: 1px solid var(--border);
        }

        .shift-item {
            background: #ffffff;
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 8px;
            text-align: center;
        }

        .shift-item .s-name {
            font-size: 11px;
            font-weight: 600;
            color: var(--text-muted);
            margin-bottom: 2px;
        }

        .shift-item .s-val {
            font-size: 14px;
            font-weight: 700;
            color: var(--primary);
        }

        .btn-back {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            width: 100%;
            padding: 14px 20px;
            background: #ffffff;
            color: var(--primary);
            border: 2px solid var(--primary);
            border-radius: 12px;
            font-size: 15px;
            font-weight: 700;
            text-decoration: none;
            transition: all 0.2s ease;
        }

        .btn-back:hover {
            background: var(--primary);
            color: #ffffff;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(79, 70, 229, 0.25);
        }

        .footer {
            margin-top: 24px;
            text-align: center;
            font-size: 12px;
            color: var(--text-muted);
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <span class="badge">✓ Tính Toán Thành Công</span>
        <h1>Kết Quả Tiền Lương</h1>
    </div>

    <!-- Total Salary Highlight Card -->
    <div class="salary-highlight-card">
        <div class="label">Tổng Tiền Lương Nhận Được</div>
        <div class="amount">
            <fmt:formatNumber value="${totalSalary}" type="number" groupingUsed="true"/> VNĐ
        </div>
    </div>

    <!-- Detailed breakdown -->
    <div class="details-card">
        <div class="detail-row">
            <span class="detail-label">Mức lương theo giờ:</span>
            <span class="detail-val">
                <fmt:formatNumber value="${hourlyRate}" type="number" groupingUsed="true"/> VNĐ / giờ
            </span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Tổng số giờ làm việc:</span>
            <span class="detail-val">${totalHours} giờ</span>
        </div>

        <c:if test="${not empty shiftHours}">
            <div class="shift-breakdown">
                <div class="shift-item">
                    <div class="s-name">Ca Sáng</div>
                    <div class="s-val">${shiftHours[0]}h</div>
                </div>
                <div class="shift-item">
                    <div class="s-name">Ca Chiều</div>
                    <div class="s-val">${shiftHours[1]}h</div>
                </div>
                <div class="shift-item">
                    <div class="s-name">Ca Tối</div>
                    <div class="s-val">${shiftHours[2]}h</div>
                </div>
            </div>
        </c:if>
    </div>

    <a href="${pageContext.request.contextPath}/" class="btn-back">
        ← Quay lại trang nhập liệu
    </a>

    <div class="footer">
        © 2026 CodeGym • Jakarta EE 10 & Tomcat 10.1+
    </div>
</div>

</body>
</html>
