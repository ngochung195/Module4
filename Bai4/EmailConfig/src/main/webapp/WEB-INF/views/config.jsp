<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Configuration</title>
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

        .card-header {
            margin-bottom: 24px;
            padding-bottom: 16px;
            border-bottom: 2px solid #edf2f7;
        }

        .card-header h2 {
            color: #1a202c;
            font-size: 24px;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 22px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #4a5568;
            font-size: 14px;
        }

        .select-control {
            width: 100%;
            padding: 10px 14px;
            border: 1px solid #cbd5e0;
            border-radius: 6px;
            font-size: 15px;
            color: #2d3748;
            background-color: #ffffff;
            outline: none;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        .select-control:focus {
            border-color: #3182ce;
            box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.15);
        }

        .page-size-wrapper {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .page-size-wrapper select {
            width: 120px;
        }

        .unit-label {
            color: #718096;
            font-size: 14px;
        }

        .btn-submit {
            display: inline-block;
            width: 100%;
            padding: 12px;
            background-color: #2b6cb0;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s, transform 0.1s;
        }

        .btn-submit:hover {
            background-color: #2c5282;
        }

        .btn-submit:active {
            transform: translateY(1px);
        }
    </style>
</head>
<body>

<div class="card">
    <div class="card-header">
        <h2>Email Configuration</h2>
    </div>

    <form:form method="post" action="${pageContext.request.contextPath}/config" modelAttribute="emailConfig">
        <div class="form-group">
            <label for="language">Language</label>
            <form:select path="language" id="language" cssClass="select-control">
                <form:option value="English">English</form:option>
                <form:option value="Vietnamese">Vietnamese</form:option>
                <form:option value="Japanese">Japanese</form:option>
                <form:option value="Chinese">Chinese</form:option>
            </form:select>
        </div>

        <div class="form-group">
            <label for="pageSize">Page Size</label>
            <div class="page-size-wrapper">
                <form:select path="pageSize" id="pageSize" cssClass="select-control">
                    <form:option value="5">5</form:option>
                    <form:option value="10">10</form:option>
                    <form:option value="15">15</form:option>
                    <form:option value="25">25</form:option>
                    <form:option value="50">50</form:option>
                    <form:option value="100">100</form:option>
                </form:select>
                <span class="unit-label">email per page</span>
            </div>
        </div>

        <div class="form-group" style="margin-top: 28px; margin-bottom: 0;">
            <button type="submit" class="btn-submit">Update</button>
        </div>
    </form:form>
</div>

</body>
</html>
