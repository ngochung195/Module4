<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Từ Điển Đơn Giản - Simple Dictionary</title>
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
            font-size: 26px;
            color: #2d3748;
            margin-bottom: 10px;
            font-weight: 700;
        }

        .card p.subtitle {
            color: #718096;
            font-size: 14px;
            margin-bottom: 25px;
        }

        .search-form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .input-group {
            position: relative;
            text-align: left;
        }

        .input-group label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: #4a5568;
            margin-bottom: 8px;
        }

        .input-group input {
            width: 100%;
            padding: 14px 16px;
            font-size: 16px;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            outline: none;
            transition: all 0.3s ease;
        }

        .input-group input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
        }

        .btn-submit {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #ffffff;
            font-size: 16px;
            font-weight: 600;
            padding: 14px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.35);
        }

        .btn-submit:active {
            transform: translateY(0);
        }

        .sample-words {
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid #edf2f7;
            text-align: left;
        }

        .sample-words h4 {
            font-size: 13px;
            color: #a0aec0;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 10px;
        }

        .word-chips {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }

        .chip {
            background: #f7fafc;
            border: 1px solid #e2e8f0;
            color: #4a5568;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 13px;
            cursor: pointer;
            transition: all 0.2s ease;
        }

        .chip:hover {
            background: #667eea;
            color: #ffffff;
            border-color: #667eea;
        }
    </style>
</head>
<body>

<div class="card">
    <h1>📖 Từ Điển Anh - Việt</h1>
    <p class="subtitle">Tra cứu từ vựng tiếng Anh nhanh chóng và chính xác</p>

    <form class="search-form" action="${pageContext.request.contextPath}/search" method="post">
        <div class="input-group">
            <label for="keyword">Nhập từ tiếng Anh cần tra:</label>
            <input type="text" id="keyword" name="keyword" placeholder="Ví dụ: hello, computer, banana..." required autofocus autocomplete="off">
        </div>
        <button type="submit" class="btn-submit">🔍 Tra cứu</button>
    </form>

    <div class="sample-words">
        <h4>Từ mẫu có sẵn trong từ điển:</h4>
        <div class="word-chips">
            <span class="chip" onclick="selectWord('hello')">hello</span>
            <span class="chip" onclick="selectWord('banana')">banana</span>
            <span class="chip" onclick="selectWord('computer')">computer</span>
            <span class="chip" onclick="selectWord('book')">book</span>
            <span class="chip" onclick="selectWord('apple')">apple</span>
            <span class="chip" onclick="selectWord('dog')">dog</span>
        </div>
    </div>
</div>

<script>
    function selectWord(word) {
        const input = document.getElementById('keyword');
        input.value = word;
        input.focus();
    }
</script>

</body>
</html>
