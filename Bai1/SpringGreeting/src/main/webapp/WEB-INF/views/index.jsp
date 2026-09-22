<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spring Greeting</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .card {
            background: #ffffff;
            padding: 40px 50px;
            border-radius: 16px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 480px;
            width: 100%;
        }
        h1 {
            color: #2c3e50;
            font-size: 2rem;
            margin-bottom: 16px;
        }
        .greeting-message {
            color: #4a5568;
            font-size: 1.2rem;
            margin-top: 12px;
        }
        .greeting-message strong {
            color: #2b6cb0;
        }
    </style>
</head>
<body>
    <div class="card">
        <h1>Hello Spring MVC</h1>
        <c:if test="${not empty name}">
            <p class="greeting-message">Xin chào, <strong>${name}</strong>!</p>
        </c:if>
    </div>
</body>
</html>
