<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>관리자 - 1대1 문의 관리</title>
    <style>
        /* 기본 스타일 및 추가 스타일 */
        body {
            font-family: Nunito Sans, sans-serif;
            background-color: #f5f6fa;
            padding: 20px;
        }
        .inquiry-container {
            background-color: #fff;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .header-wrapper {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .page-title {
            color: #000;
            font-size: 28px;
            font-weight: 700;
        }
        .search-container {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        .search-container select, .search-container input[type="text"] {
            padding: 8px;
            border: 1px solid #d5d5d5;
            border-radius: 4px;
            font-size: 14px;
        }
        .search-container button {
            padding: 8px 16px;
            background-color: #3498db;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #eee;
            text-align: left;
        }
        th {
            background-color: #f5f6fa;
        }
        .action-buttons button {
            margin-right: 5px;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
        }
        .view-button {
            background-color: #3498db;
            color: #fff;
        }
        .delete-button {
            background-color: #e74c3c;
            color: #fff;
        }
        .pagination {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="inquiry-container">
        <div class="header-wrapper">
            <h1 class="page-title">1대1 문의 관리</h1>
            <!-- 추가적인 버튼 필요 시 여기에 삽입 -->
        </div>

        <div class="search-container">
            <form action="${pageContext.request.contextPath}/contact/admin/list" method="get" style="display: flex; gap: 10px;">
                <select name="category">
                    <option value="">전체 카테고리</option>
                    <option value="Payment">Payment</option>
                    <option value="Technical Issues">Technical Issues</option>
                    <option value="User Reports">User Reports</option>
                    <option value="Board Related">Board Related</option>
                    <!-- 필요에 따라 카테고리 추가 -->
                </select>
                <input type="text" name="keyword" placeholder="검색어 입력" />
                <button type="submit">검색</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>사용자 번호</th>
                    <th>카테고리</th>
                    <th>제목</th>
                    <th>상태</th>
                    <th>작성일</th>
                    <th>액션</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="contact" items="${contacts}">
                    <tr>
                        <td>${contact.inquiryId}</td>
                        <td>${contact.userNum}</td>
                        <td>${contact.title}</td>
                        <td>${contact.title}</td>
                        <td>
                            <c:choose>
                                <c:when test="${contact.status == 0}">대기중</c:when>
                                <c:when test="${contact.status == 1}">답변완료</c:when>
                                <c:when test="${contact.status == 2}">처리중</c:when>
                                <c:otherwise>알 수 없음</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${contact.createdAt}</td>
                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/contact/admin/view?id=${contact.inquiryId}">
                                <button type="button" class="view-button">보기</button>
                            </a>
                            <form action="${pageContext.request.contextPath}/contact/admin/delete" method="post" style="display: inline;">
                                <input type="hidden" name="id" value="${contact.inquiryId}" />
                                <button type="submit" class="delete-button" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination">
            <span>총 문의 수: ${contacts.size()}</span>
            <!-- 페이징 로직 필요 시 여기에 추가 -->
            <!-- 예시: 이전 페이지, 다음 페이지 버튼 또는 페이지 번호 표시 -->
        </div>
    </div>
</body>
</html>
