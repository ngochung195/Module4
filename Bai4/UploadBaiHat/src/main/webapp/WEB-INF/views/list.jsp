<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Song List</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: #f0f2f5;
            color: #333;
            padding: 40px 20px;
        }
        .container {
            max-width: 960px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            padding: 32px;
        }
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
            padding-bottom: 16px;
            border-bottom: 1px solid #e2e8f0;
        }
        .top-bar h1 {
            font-size: 24px;
            color: #1e293b;
            font-weight: 700;
        }
        .btn-upload {
            background: #2563eb;
            color: #ffffff;
            text-decoration: none;
            padding: 10px 18px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 6px;
            transition: background 0.2s;
        }
        .btn-upload:hover {
            background: #1d4ed8;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 12px;
        }
        thead th {
            background-color: #f8fafc;
            color: #475569;
            font-weight: 600;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            padding: 12px 14px;
            text-align: left;
            border-bottom: 2px solid #e2e8f0;
        }
        tbody td {
            padding: 14px;
            border-bottom: 1px solid #f1f5f9;
            font-size: 14px;
            color: #334155;
            vertical-align: middle;
        }
        tbody tr:hover {
            background-color: #f8fafc;
        }
        .badge-genre {
            display: inline-block;
            background-color: #e0e7ff;
            color: #4338ca;
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
            margin-right: 4px;
            margin-bottom: 2px;
        }
        .path-code {
            font-family: 'Consolas', 'Courier New', monospace;
            font-size: 12px;
            background: #f1f5f9;
            padding: 4px 8px;
            border-radius: 4px;
            color: #475569;
            word-break: break-all;
        }
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #64748b;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="top-bar">
        <h1>Song List</h1>
        <a href="${pageContext.request.contextPath}/songs/upload" class="btn-upload">+ Upload new song</a>
    </div>

    <c:choose>
        <c:when test="${empty songs}">
            <div class="empty-state">
                <p>Chưa có bài hát nào được tải lên.</p>
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th style="width: 50px;">STT</th>
                        <th>Tên bài hát</th>
                        <th>Nghệ sĩ</th>
                        <th>Thể loại</th>
                        <th>File</th>
                        <th>Đường dẫn</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="song" items="${songs}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td><strong><c:out value="${song.name}"/></strong></td>
                            <td><c:out value="${song.artist}"/></td>
                            <td>
                                <c:forEach var="g" items="${song.genre}">
                                    <span class="badge-genre"><c:out value="${g}"/></span>
                                </c:forEach>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty song.path}">
                                        <c:set var="fileName" value="${song.path}" />
                                        <c:if test="${fn:contains(song.path, '/')}">
                                            <c:set var="fileName" value="${fn:substringAfter(song.path, '/')}" />
                                        </c:if>
                                        <span><c:out value="${fileName}"/></span>
                                    </c:when>
                                    <c:otherwise>
                                        <em>N/A</em>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="path-code"><c:out value="${song.path}"/></span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
