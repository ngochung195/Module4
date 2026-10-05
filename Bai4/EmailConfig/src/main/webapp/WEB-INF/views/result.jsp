<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configuration Result</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
        }

        body {
            background-color: #f4f6f9;
            color: #333333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }

        .card {
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            width: 100%;
            max-width: 520px;
            padding: 32px;
            border: 1px solid #e2e8f0;
        }

        .alert-success {
            background-color: #f0fff4;
            color: #22543d;
            border: 1px solid #9ae6b4;
            padding: 14px 18px;
            border-radius: 6px;
            margin-bottom: 24px;
            font-size: 15px;
            font-weight: 500;
        }

        .card-header {
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #edf2f7;
        }

        .card-header h2 {
            color: #1a202c;
            font-size: 22px;
            font-weight: 600;
        }

        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 28px;
        }

        .info-table tr {
            border-bottom: 1px solid #edf2f7;
        }

        .info-table th {
            text-align: left;
            padding: 12px 0;
            color: #718096;
            font-weight: 500;
            width: 40%;
            font-size: 14px;
        }

        .info-table td {
            text-align: left;
            padding: 12px 0;
            color: #2d3748;
            font-weight: 600;
            font-size: 15px;
        }

        .btn-link {
            display: inline-block;
            width: 100%;
            text-align: center;
            padding: 12px;
            background-color: #2b6cb0;
            color: #ffffff;
            text-decoration: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            transition: background-color 0.2s;
        }

        .btn-link:hover {
            background-color: #2c5282;
        }
    </style>
</head>
<body>

<div class="card">
    <c:if test="${not empty message}">
        <div class="alert-success">
            ✓ ${message}
        </div>
    </c:if>

    <div class="card-header">
        <h2>Cấu hình hòm thư hiện tại</h2>
    </div>

    <table class="info-table">
        <tr>
            <th>Language:</th>
            <td>${emailConfig.language}</td>
        </tr>
        <tr>
            <th>Page Size:</th>
            <td>${emailConfig.pageSize} email per page</td>
        </tr>
    </table>

    <a href="${pageContext.request.contextPath}/config" class="btn-link">Chỉnh sửa cấu hình (Edit Configuration)</a>
</div>

</body>
</html>
