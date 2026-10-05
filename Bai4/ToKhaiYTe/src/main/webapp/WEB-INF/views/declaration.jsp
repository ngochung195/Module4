<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tờ khai y tế</title>
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
            background: linear-gradient(135deg, #0d6efd 0%, #0b5ed7 100%);
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
        .form-content {
            padding: 32px;
        }
        .section-title {
            font-size: 16px;
            font-weight: 600;
            color: #0b5ed7;
            border-bottom: 2px solid #e9ecef;
            padding-bottom: 8px;
            margin-top: 10px;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-row {
            display: flex;
            gap: 20px;
        }
        .form-col {
            flex: 1;
        }
        label.field-label {
            display: block;
            font-weight: 600;
            font-size: 14px;
            margin-bottom: 6px;
            color: #34495e;
        }
        .required {
            color: #dc3545;
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 10px 14px;
            font-size: 14px;
            border: 1.5px solid #cbd5e1;
            border-radius: 6px;
            outline: none;
            transition: all 0.2s ease-in-out;
            background-color: #f8fafc;
        }
        .form-control:focus {
            background-color: #ffffff;
            border-color: #0d6efd;
            box-shadow: 0 0 0 3px rgba(13, 110, 253, 0.15);
        }
        textarea.form-control {
            min-height: 80px;
            resize: vertical;
        }
        .error {
            display: block;
            color: #dc3545;
            font-size: 13px;
            font-weight: 500;
            margin-top: 5px;
        }
        .options-group {
            display: flex;
            flex-wrap: wrap;
            gap: 16px;
            padding: 6px 0;
        }
        .option-item {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            font-size: 14px;
            cursor: pointer;
            user-select: none;
        }
        .option-item input {
            accent-color: #0d6efd;
            cursor: pointer;
            width: 16px;
            height: 16px;
        }
        .actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #e9ecef;
        }
        .btn-submit {
            background: #0d6efd;
            color: #ffffff;
            font-size: 15px;
            font-weight: 600;
            padding: 12px 32px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s ease;
        }
        .btn-submit:hover {
            background: #0b5ed7;
        }
        .view-link {
            color: #0d6efd;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
            gap: 4px;
        }
        .view-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>Tờ khai y tế</h1>
        <p>Vui lòng điền đầy đủ và trung thực các thông tin dưới đây</p>
    </div>

    <div class="form-content">
        <form:form modelAttribute="medicalDeclaration" method="post" action="${pageContext.request.contextPath}/declaration">
            
            <div class="section-title">1. Thông tin cá nhân</div>

            <div class="form-group">
                <label class="field-label" for="fullName">Họ và tên <span class="required">*</span></label>
                <form:input path="fullName" id="fullName" cssClass="form-control" placeholder="Ví dụ: Nguyễn Văn A" />
                <form:errors path="fullName" cssClass="error" />
            </div>

            <div class="form-row">
                <div class="form-col form-group">
                    <label class="field-label" for="birthYear">Năm sinh <span class="required">*</span></label>
                    <form:input path="birthYear" id="birthYear" cssClass="form-control" placeholder="Ví dụ: 1995" />
                    <form:errors path="birthYear" cssClass="error" />
                </div>

                <div class="form-col form-group">
                    <label class="field-label">Giới tính <span class="required">*</span></label>
                    <div class="options-group">
                        <label class="option-item">
                            <form:radiobutton path="gender" value="Nam" /> Nam
                        </label>
                        <label class="option-item">
                            <form:radiobutton path="gender" value="Nữ" /> Nữ
                        </label>
                        <label class="option-item">
                            <form:radiobutton path="gender" value="Khác" /> Khác
                        </label>
                    </div>
                    <form:errors path="gender" cssClass="error" />
                </div>
            </div>

            <div class="form-row">
                <div class="form-col form-group">
                    <label class="field-label" for="nationality">Quốc tịch <span class="required">*</span></label>
                    <form:input path="nationality" id="nationality" cssClass="form-control" placeholder="Ví dụ: Việt Nam" />
                    <form:errors path="nationality" cssClass="error" />
                </div>

                <div class="form-col form-group">
                    <label class="field-label" for="idCard">Số CMND/CCCD/Hộ chiếu <span class="required">*</span></label>
                    <form:input path="idCard" id="idCard" cssClass="form-control" placeholder="Ví dụ: 012345678901" />
                    <form:errors path="idCard" cssClass="error" />
                </div>
            </div>

            <div class="section-title">2. Lịch trình di chuyển &amp; Dịch tễ</div>

            <div class="form-group">
                <label class="field-label" for="travelInformation">Thông tin đi lại trong vòng 14 ngày qua <span class="required">*</span></label>
                <form:textarea path="travelInformation" id="travelInformation" cssClass="form-control" placeholder="Phương tiện di chuyển, nơi đi, nơi đến, số chuyến bay/tàu/xe..." />
                <form:errors path="travelInformation" cssClass="error" />
            </div>

            <div class="form-group">
                <label class="field-label">Triệu chứng xuất hiện trong 14 ngày qua (nếu có)</label>
                <div class="options-group">
                    <c:forEach var="symptom" items="${symptomList}">
                        <label class="option-item">
                            <form:checkbox path="symptoms" value="${symptom}" /> ${symptom}
                        </label>
                    </c:forEach>
                </div>
                <form:errors path="symptoms" cssClass="error" />
            </div>

            <div class="form-group">
                <label class="field-label">Tiền sử bệnh mạn tính (nếu có)</label>
                <div class="options-group">
                    <c:forEach var="history" items="${medicalHistoryList}">
                        <label class="option-item">
                            <form:checkbox path="medicalHistory" value="${history}" /> ${history}
                        </label>
                    </c:forEach>
                </div>
                <form:errors path="medicalHistory" cssClass="error" />
            </div>

            <div class="section-title">3. Thông tin liên hệ</div>

            <div class="form-group">
                <label class="field-label" for="contactInformation">Địa chỉ liên hệ &amp; Số điện thoại <span class="required">*</span></label>
                <form:textarea path="contactInformation" id="contactInformation" cssClass="form-control" placeholder="Địa chỉ nơi cư trú, số điện thoại liên hệ, email..." />
                <form:errors path="contactInformation" cssClass="error" />
            </div>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/declaration/view" class="view-link">View current declaration &rarr;</a>
                <button type="submit" class="btn-submit">Submit</button>
            </div>

        </form:form>
    </div>
</div>

</body>
</html>
