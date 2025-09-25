<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>문의 상세 보기</title>
    <style>
        /* 기본 스타일 */
        body {
            font-family: Nunito Sans, sans-serif;
            background-color: #f5f6fa;
            padding: 20px;
        }
        .detail-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            max-width: 800px;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .detail-group {
            margin-bottom: 20px;
        }
        label {
            font-weight: 600;
            display: block;
            margin-bottom: 5px;
        }
        .value {
            padding: 10px;
            border: 1px solid #d5d5d5;
            border-radius: 4px;
            background-color: #f9f9f9;
        }
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #d5d5d5;
            border-radius: 4px;
            font-size: 14px;
            resize: vertical;
        }
        .action-buttons {
            text-align: right;
            margin-top: 20px;
        }
        .action-buttons button, .action-buttons a {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-left: 10px;
            text-decoration: none;
            display: inline-block;
        }
        .save-button {
            background-color: #2ecc71;
            color: #fff;
        }
        .back-button {
            background-color: #3498db;
            color: #fff;
        }
    </style>
</head>
<body>
    <div class="detail-container">
        <h2>문의 상세 보기</h2>
        <div class="detail-group">
            <label>ID</label>
            <div class="value">${contact.inquiryId}</div>
        </div>
        <div class="detail-group">
            <label>사용자 번호</label>
            <div class="value">${contact.userNum}</div>
        </div>
        <div class="detail-group">
            <label>카테고리</label>
            <div class="value">${contact.title}</div>
        </div>
        <div class="detail-group">
            <label>제목</label>
            <div class="value">${contact.title}</div>
        </div>
        <div class="detail-group">
            <label>내용</label>
            <div class="value">${contact.content}</div>
        </div>
        <div class="detail-group">
            <label>첨부 파일</label>
            <c:choose>
                <c:when test="${not empty contact.fileName and contact.fileName != '없음'}">
                    <a href="/uploads/${contact.fileName}" target="_blank">${contact.fileName}</a>
                </c:when>
                <c:otherwise>
                    <div class="value">없음</div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="detail-group">
            <label>상태</label>
            <div class="value">
                <c:choose>
                    <c:when test="${contact.status == 0}">대기중</c:when>
                    <c:when test="${contact.status == 1}">답변완료</c:when>
                    <c:when test="${contact.status == 2}">처리중</c:when>
                    <c:otherwise>알 수 없음</c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="detail-group">
            <label>작성일</label>
            <div class="value">${contact.createdAt}</div>
        </div>
        <div class="detail-group">
            <label>수정일</label>
            <div class="value">
                <c:if test="${not empty contact.updatedAt}">
                    ${contact.updatedAt}
                </c:if>
                <c:if test="${empty contact.updatedAt}">
                    없음
                </c:if>
            </div>
        </div>
        <c:if test="${contact.status >= 1}">
            <div class="detail-group">
                <label>관리자 답변</label>
                <div class="value">${contact.answerContent}</div>
            </div>
            <div class="detail-group">
                <label>답변 작성일</label>
                <div class="value">
                    <c:if test="${not empty contact.answeredAt}">
                        ${contact.answeredAt}
                    </c:if>
                    <c:if test="${empty contact.answeredAt}">
                        없음
                    </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${contact.status < 1}">
            <form action="${pageContext.request.contextPath}/contact/admin/respond" method="post">
                <input type="hidden" name="id" value="${contact.inquiryId}" />
                <div class="detail-group">
                    <label for="response">답변 내용</label>
                    <textarea name="response" id="response" rows="5" placeholder="답변을 입력하세요." required></textarea>
                </div>
                <div class="action-buttons">
                    <button type="submit" class="save-button">답변 저장</button>
                    <a href="${pageContext.request.contextPath}/contact/admin/list" class="back-button">뒤로가기</a>
                </div>
            </form>
        </c:if>
    </div>
</body>
</html>
