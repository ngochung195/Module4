<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ Thống Tính Lương Theo Ca</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #4f46e5;
            --primary-hover: #4338ca;
            --primary-light: #eef2ff;
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
            margin-bottom: 28px;
        }

        .badge {
            display: inline-block;
            background: var(--primary-light);
            color: var(--primary);
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
            margin-bottom: 8px;
        }

        .header p {
            font-size: 14px;
            color: var(--text-muted);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .shift-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 12px;
            margin-top: 8px;
        }

        .shift-card {
            background: #f8fafc;
            border: 1.5px solid var(--border);
            border-radius: 12px;
            padding: 12px;
            text-align: center;
            transition: all 0.2s ease;
        }

        .shift-card:focus-within {
            border-color: var(--primary);
            background: #ffffff;
            box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.15);
        }

        .shift-card label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: var(--text-muted);
            margin-bottom: 6px;
        }

        .shift-card .shift-icon {
            font-size: 18px;
            margin-bottom: 4px;
            display: block;
        }

        .shift-card input {
            width: 100%;
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 8px 6px;
            font-size: 15px;
            font-weight: 700;
            color: var(--text-main);
            text-align: center;
            outline: none;
            background: #ffffff;
        }

        .shift-card input:focus {
            border-color: var(--primary);
        }

        .rate-select-group {
            margin-top: 20px;
        }

        .form-label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: var(--text-main);
            margin-bottom: 8px;
        }

        .select-wrapper {
            position: relative;
        }

        select {
            width: 100%;
            padding: 12px 16px;
            font-size: 15px;
            font-weight: 600;
            color: var(--text-main);
            background: #f8fafc;
            border: 1.5px solid var(--border);
            border-radius: 12px;
            outline: none;
            appearance: none;
            cursor: pointer;
            transition: all 0.2s ease;
        }

        select:focus {
            background: #ffffff;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.15);
        }

        .select-icon {
            position: absolute;
            right: 16px;
            top: 50%;
            transform: translateY(-50%);
            pointer-events: none;
            color: var(--text-muted);
            font-size: 12px;
        }

        .btn-submit {
            width: 100%;
            padding: 14px 20px;
            background: var(--primary);
            color: #ffffff;
            border: none;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 700;
            cursor: pointer;
            margin-top: 24px;
            transition: all 0.2s ease;
            box-shadow: 0 4px 12px rgba(79, 70, 229, 0.25);
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }

        .btn-submit:hover {
            background: var(--primary-hover);
            transform: translateY(-1px);
            box-shadow: 0 6px 16px rgba(79, 70, 229, 0.35);
        }

        .btn-submit:active {
            transform: translateY(0);
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
        <span class="badge">Spring MVC 6.2.6</span>
        <h1>Bảng Tính Tiền Lương</h1>
        <p>Nhập số giờ làm việc theo ca và chọn mức lương theo giờ</p>
    </div>

    <form action="${pageContext.request.contextPath}/calculate" method="POST">
        <div class="form-group">
            <span class="form-label">Số giờ làm việc theo từng ca:</span>
            <div class="shift-grid">
                <div class="shift-card">
                    <span class="shift-icon">🌅</span>
                    <label for="shift1">Ca Sáng</label>
                    <input type="number" id="shift1" name="shiftHours" step="0.5" min="0" max="24" value="4" required>
                </div>
                <div class="shift-card">
                    <span class="shift-icon">☀️</span>
                    <label for="shift2">Ca Chiều</label>
                    <input type="number" id="shift2" name="shiftHours" step="0.5" min="0" max="24" value="4" required>
                </div>
                <div class="shift-card">
                    <span class="shift-icon">🌙</span>
                    <label for="shift3">Ca Tối</label>
                    <input type="number" id="shift3" name="shiftHours" step="0.5" min="0" max="24" value="2" required>
                </div>
            </div>
        </div>

        <div class="form-group rate-select-group">
            <label class="form-label" for="hourlyRate">Mức lương theo giờ (VNĐ/giờ):</label>
            <div class="select-wrapper">
                <select id="hourlyRate" name="hourlyRate" required>
                    <option value="20000">20.000 VNĐ / giờ (Part-time cơ bản)</option>
                    <option value="30000" selected>30.000 VNĐ / giờ (Nhân viên tiêu chuẩn)</option>
                    <option value="50000">50.000 VNĐ / giờ (Ca đêm / Lễ)</option>
                    <option value="100000">100.000 VNĐ / giờ (Quản lý / Chuyên gia)</option>
                </select>
                <span class="select-icon">▼</span>
            </div>
        </div>

        <button type="submit" class="btn-submit" id="btnCalculate">
            <span>⚡</span> Tính Lương Ngay
        </button>
    </form>

    <div class="footer">
        © 2026 CodeGym • Jakarta EE 10 & Tomcat 10.1+
    </div>
</div>

</body>
</html>
