<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin tờ khai y tế</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: linear-gradient(135deg, #eef2f7 0%, #dbe4ef 100%);
            min-height: 100vh;
            padding: 40px 20px;
            color: #2c3e50;
        }
        .container {
            max-width: 780px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            overflow: hidden;
        }
        .header {
            background: linear-gradient(135deg, #198754 0%, #157347 100%);
            color: #ffffff;
            padding: 28px 32px;
            text-align: center;
        }
        .header h1 {
            font-size: 24px;
            font-weight: 700;
            letter-spacing: 0.5px;
            text-transform: uppercase;
            margin-bottom: 6px;
        }
        .header p {
            font-size: 14px;
            opacity: 0.9;
        }
        .content {
            padding: 32px;
        }
        .section-title {
            font-size: 16px;
            font-weight: 600;
            color: #198754;
            border-bottom: 2px solid #e9ecef;
            padding-bottom: 8px;
            margin-top: 15px;
            margin-bottom: 16px;
        }
        .info-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px 24px;
            margin-bottom: 16px;
        }
        .info-item {
            display: flex;
            flex-direction: column;
        }
        .info-item.full-width {
            grid-column: span 2;
        }
        .info-label {
            font-size: 13px;
            font-weight: 600;
            color: #6c757d;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 4px;
        }
        .info-value {
            font-size: 15px;
            font-weight: 500;
            color: #212529;
            background-color: #f8fafc;
            padding: 10px 14px;
            border-radius: 6px;
            border: 1px solid #e2e8f0;
            white-space: pre-wrap;
        }
        .badge-list {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }
        .badge {
            background-color: #e8f5e9;
            color: #2e7d32;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 13px;
            font-weight: 600;
            border: 1px solid #c8e6c9;
        }
        .badge.none {
            background-color: #f1f3f5;
            color: #6c757d;
            border-color: #dee2e6;
        }
        .actions {
            display: flex;
            justify-content: flex-end;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #e9ecef;
        }
        .btn-update {
            background: #0d6efd;
            color: #ffffff;
            font-size: 15px;
            font-weight: 600;
            padding: 12px 32px;
            text-decoration: none;
            border-radius: 6px;
            display: inline-block;
            transition: background 0.2s ease;
        }
        .btn-update:hover {
            background: #0b5ed7;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>Thông tin tờ khai y tế</h1>
        <p>Chi tiết thông tin khai báo y tế đã được ghi nhận</p>
    </div>

    <div class="content">
        <div class="section-title">1. Thông tin cá nhân</div>
        <div class="info-grid">
            <div class="info-item">
                <span class="info-label">Họ và tên</span>
                <span class="info-value"><c:out value="${medicalDeclaration.fullName}"/></span>
            </div>
            <div class="info-item">
                <span class="info-label">Năm sinh</span>
                <span class="info-value"><c:out value="${medicalDeclaration.birthYear}"/></span>
            </div>
            <div class="info-item">
                <span class="info-label">Giới tính</span>
                <span class="info-value"><c:out value="${medicalDeclaration.gender}"/></span>
            </div>
            <div class="info-item">
                <span class="info-label">Quốc tịch</span>
                <span class="info-value"><c:out value="${medicalDeclaration.nationality}"/></span>
            </div>
            <div class="info-item full-width">
                <span class="info-label">Số CMND/CCCD/Hộ chiếu</span>
                <span class="info-value"><c:out value="${medicalDeclaration.idCard}"/></span>
            </div>
        </div>

        <div class="section-title">2. Lịch trình di chuyển &amp; Dịch tễ</div>
        <div class="info-grid">
            <div class="info-item full-width">
                <span class="info-label">Thông tin đi lại (14 ngày qua)</span>
                <div class="info-value"><c:out value="${medicalDeclaration.travelInformation}"/></div>
            </div>

            <div class="info-item full-width">
                <span class="info-label">Triệu chứng</span>
                <div class="info-value">
                    <c:choose>
                        <c:when test="${not empty medicalDeclaration.symptoms}">
                            <div class="badge-list">
                                <c:forEach var="symptom" items="${medicalDeclaration.symptoms}">
                                    <span class="badge"><c:out value="${symptom}"/></span>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <span class="badge none">Không có triệu chứng ghi nhận</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="info-item full-width">
                <span class="info-label">Tiền sử bệnh</span>
                <div class="info-value">
                    <c:choose>
                        <c:when test="${not empty medicalDeclaration.medicalHistory}">
                            <div class="badge-list">
                                <c:forEach var="history" items="${medicalDeclaration.medicalHistory}">
                                    <span class="badge"><c:out value="${history}"/></span>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <span class="badge none">Không có tiền sử bệnh</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="section-title">3. Thông tin liên hệ</div>
        <div class="info-grid">
            <div class="info-item full-width">
                <span class="info-label">Địa chỉ &amp; Số điện thoại liên hệ</span>
                <div class="info-value"><c:out value="${medicalDeclaration.contactInformation}"/></div>
            </div>
        </div>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/declaration" class="btn-update">Update</a>
        </div>
    </div>
</div>

</body>
</html>
