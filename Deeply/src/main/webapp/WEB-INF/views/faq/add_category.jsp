<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 내부 CSS 스타일링 -->
<style>
    .add-category-container {
        border-radius: 8px;
        background-color: #fff;
        box-shadow: 0 3px 14px rgba(0, 0, 0, 0.05);
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 60%;
        padding: 40px 30px;
        font-family: 'Noto Sans', sans-serif;
        margin: 50px auto;
    }

    .add-category-container h1 {
        font-size: 28px;
        font-weight: 700;
        color: #1f2937;
        margin-bottom: 30px;
    }

    .form-group {
        width: 100%;
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
    }

    .form-group label {
        font-size: 14px;
        font-weight: 600;
        color: #4b5563;
        margin-bottom: 8px;
    }

    .form-group input[type="text"],
    .form-group textarea {
        padding: 12px 15px;
        border: 1px solid #d1d5db;
        border-radius: 6px;
        font-size: 14px;
        color: #1f2937;
        outline: none;
        transition: border-color 0.3s ease, box-shadow 0.3s ease;
    }

    .form-group input[type="text"]:focus,
    .form-group textarea:focus {
        border-color: #2563eb;
        box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
    }

    .button-group {
        display: flex;
        justify-content: flex-end;
        gap: 15px;
        width: 100%;
        margin-top: 20px;
    }

    .btn-submit {
        background-color: #2563eb;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s ease;
    }

    .btn-submit:hover {
        background-color: #1d4ed8;
    }

    .btn-cancel {
        background-color: #d1d5db;
        color: #1f2937;
        padding: 10px 20px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s ease;
        text-decoration: none;
        display: inline-block;
        text-align: center;
    }

    .btn-cancel:hover {
        background-color: #b1b5bb;
    }

    /* 반응형 디자인 */
    @media (max-width: 768px) {
        .add-category-container {
            width: 90%;
            padding: 30px 20px;
        }
    }
</style>

<!-- 카테고리 추가 폼 -->
<div class="add-category-container">
    <h1>카테고리 추가</h1>
    <form action="${pageContext.request.contextPath}/faq/categories/add" method="post" onsubmit="return validateForm();">
        <!-- CSRF 토큰 -->
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <!-- 카테고리명 입력 -->
        <div class="form-group">
            <label for="categoryName">카테고리명:</label>
            <input type="text" id="categoryName" name="categoryName" maxlength="50" required />
        </div>

        <!-- 설명 입력 -->
        <div class="form-group">
            <label for="description">설명:</label>
            <textarea id="description" name="description" maxlength="200" rows="4" placeholder="카테고리에 대한 설명을 입력하세요..."></textarea>
        </div>

        <!-- 버튼 그룹 -->
        <div class="button-group">
            <button type="submit" class="btn-submit">추가</button>
            <a href="${pageContext.request.contextPath}/faq/categories" class="btn-cancel">취소</a>
        </div>
    </form>
</div>

<!-- JavaScript 유효성 검사 -->
<script type="text/javascript">
    // 간단한 폼 유효성 검사
    function validateForm() {
        var categoryName = document.getElementById('categoryName').value.trim();
        if (categoryName === "") {
            alert("카테고리명을 입력해 주세요.");
            document.getElementById('categoryName').focus();
            return false;
        }
        return true;
    }
</script>
