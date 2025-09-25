<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 내부 CSS 스타일링 -->
<style>
    /* 기본 스타일 설정 */
    .category-management-container {
        max-width: 900px;
        margin: 50px auto;
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        font-family: 'Noto Sans', sans-serif;
    }

    /* 제목 스타일 */
    .category-management-container h1 {
        text-align: center;
        color: #1f2937;
        margin-bottom: 30px;
        font-size: 28px;
        font-weight: 700;
    }

    /* 버튼 스타일 */
    .add-category-btn {
        display: inline-block;
        margin-bottom: 20px;
        padding: 10px 20px;
        background-color: #2563eb;
        color: #ffffff;
        text-decoration: none;
        border-radius: 6px;
        transition: background-color 0.3s ease;
    }

    .add-category-btn:hover {
        background-color: #1d4ed8;
    }

    /* 테이블 스타일 */
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    table thead {
        background-color: #f3f4f6;
    }

    table th, table td {
        padding: 12px 15px;
        text-align: left;
        border: 1px solid #d1d5db;
    }

    table th {
        font-weight: 600;
        color: #4b5563;
    }

    table tbody tr:nth-child(even) {
        background-color: #f9fafb;
    }

    /* 관리 버튼 스타일 */
    .action-links a {
        margin-right: 10px;
        color: #2563eb;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    .action-links a:hover {
        color: #1d4ed8;
    }

    /* 반응형 디자인 */
    @media (max-width: 768px) {
        .category-management-container {
            width: 90%;
            padding: 20px 25px;
        }

        .category-management-container h1 {
            font-size: 24px;
        }

        table th, table td {
            padding: 10px 12px;
        }

        .add-category-btn {
            padding: 8px 16px;
        }
    }

    /* 성공/오류 메시지 스타일 */
    .alert {
        padding: 15px;
        margin-bottom: 20px;
        border-radius: 4px;
        font-size: 14px;
    }

    .alert-success {
        background-color: #d1fae5;
        color: #065f46;
    }

    .alert-error {
        background-color: #fee2e2;
        color: #b91c1c;
    }
</style>

<!-- 카테고리 관리 컨테이너 -->
<div class="category-management-container">
    <h1>FAQ 카테고리 관리</h1>

    <!-- 성공 메시지 -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <!-- 오류 메시지 -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <!-- 카테고리 추가 버튼 -->
    <a href="${pageContext.request.contextPath}/faq/categories/add" class="add-category-btn">카테고리 추가</a>

    <!-- 카테고리 목록 테이블 -->
    <table>
        <thead>
            <tr>
                <th>카테고리 ID</th>
                <th>카테고리명</th>
                <th>설명</th>
                <th>관리</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.categoryId}</td>
                    <td>${category.categoryName}</td>
                    <td>${category.description}</td>
                    <td class="action-links">
                        <a href="${pageContext.request.contextPath}/faq/categories/edit/${category.categoryId}">수정</a>
                        <a href="${pageContext.request.contextPath}/faq/categories/delete/${category.categoryId}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
