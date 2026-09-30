<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kiểm tra Email</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
        h2 {
            color: #333333;
            margin-bottom: 24px;
            font-size: 24px;
        }
        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #555555;
            font-size: 14px;
            font-weight: 600;
        }
        input[type="text"] {
            width: 100%;
            padding: 12px 16px;
            border: 1px solid #cccccc;
            border-radius: 8px;
            font-size: 15px;
            outline: none;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        input[type="text"]:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.25);
        }
        .btn-submit {
            width: 100%;
            background: #667eea;
            color: #ffffff;
            border: none;
            padding: 12px;
            font-size: 16px;
            font-weight: 600;
            border-radius: 8px;
            cursor: pointer;
            transition: background 0.2s, transform 0.1s;
        }
        .btn-submit:hover {
            background: #5a6fd1;
        }
        .btn-submit:active {
            transform: scale(0.98);
        }
        .error-message {
            margin-top: 16px;
            padding: 10px 14px;
            background-color: #fee2e2;
            color: #b91c1c;
            border: 1px solid #f87171;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="card">
        <h2>Kiểm tra định dạng Email</h2>
        <form action="${pageContext.request.contextPath}/validate" method="post">
            <div class="form-group">
                <label for="email">Địa chỉ Email:</label>
                <input type="text" id="email" name="email" placeholder="example@domain.com" required autocomplete="off"/>
            </div>
            <button type="submit" class="btn-submit">Kiểm tra (Validate)</button>
        </form>
        <c:if test="${not empty message}">
            <div class="error-message">
                ${message}
            </div>
        </c:if>
    </div>
</body>
</html>
