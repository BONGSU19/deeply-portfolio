<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>1대1 문의 작성</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.css">
    <style>
        /* 간단한 스타일 추가 */
        body {
            font-family: Nunito Sans, sans-serif;
            background-color: #f5f6fa;
            padding: 20px;
        }
        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            max-width: 600px;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: 600;
            margin-bottom: 5px;
        }
        input[type="text"], select, textarea, input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #d5d5d5;
            border-radius: 4px;
            font-size: 14px;
        }
        button {
            background-color: #000;
            color: #fff;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .success-message {
            color: green;
            margin-bottom: 15px;
            text-align: center;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>1대1 문의 작성</h2>
        <c:if test="${param.success == 'true'}">
            <div class="success-message">문의가 성공적으로 제출되었습니다.</div>
        </c:if>
        <c:if test="${param.error == 'upload_failed'}">
            <div class="error-message">파일 업로드에 실패했습니다. 다시 시도해주세요.</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/contact/submitInquiry" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="category">카테고리</label>
                <select id="category" name="category" required>
                    <option value="">선택하세요</option>
                    <option value="Payment">Payment</option>
                    <option value="Technical Issues">Technical Issues</option>
                    <option value="User Reports">User Reports</option>
                    <option value="Board Related">Board Related</option>
                    <!-- 필요에 따라 카테고리 추가 -->
                </select>
            </div>
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" required />
            </div>
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" rows="5" required></textarea>
            </div>
            <div class="form-group">
                <label for="file">파일 업로드 (선택)</label>
                <input type="file" id="file" name="file" accept="image/*,application/pdf" />
            </div>
            <button type="submit">문의 제출</button>
        </form>
    </div>
</body>
</html>
