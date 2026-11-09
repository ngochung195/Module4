<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>jQuery Effects - Slide Demo</title>
    <!-- Nhúng jQuery 3.7.1 từ CDN Google -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: #ffffff;
            padding: 24px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        .btn-group {
            margin-bottom: 16px;
        }
        button {
            padding: 10px 20px;
            margin-right: 8px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            color: #fff;
        }
        #btn-show {
            background-color: #28a745;
        }
        #btn-show:hover {
            background-color: #218838;
        }
        #btn-hide {
            background-color: #dc3545;
        }
        #btn-hide:hover {
            background-color: #c82333;
        }
        #notification-box {
            padding: 20px;
            background-color: #e7f3fe;
            border-left: 6px solid #2196F3;
            color: #0c5460;
            border-radius: 4px;
            line-height: 1.6;
        }
    </style>
    <script>
        $(document).ready(function () {
            $("#btn-hide").click(function () {
                $("#notification-box").slideUp(400);
            });

            $("#btn-show").click(function () {
                $("#notification-box").slideDown(400);
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h2>Minh họa Hiệu ứng jQuery (slideUp / slideDown)</h2>
    <div class="btn-group">
        <button id="btn-show" type="button">Show</button>
        <button id="btn-hide" type="button">Hide</button>
        <a href="${pageContext.request.contextPath}/animate-demo" style="display:inline-block; padding:10px 20px; background-color:#17a2b8; color:#fff; text-decoration:none; border-radius:4px; font-size:16px;">Chuyển sang Animate Demo</a>
    </div>
    <div id="notification-box">
        <strong>Thông báo:</strong> Nếu bạn nhấn vào nút "Hide", hộp văn bản này sẽ trượt lên (slideUp) và ẩn đi. Nếu bạn nhấn vào nút "Show", hộp văn bản sẽ trượt xuống (slideDown) và hiển thị trở lại.
    </div>
</div>
</body>
</html>

