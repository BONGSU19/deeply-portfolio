<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 내부 CSS 스타일링 -->
<style>
    /* 기본 스타일 설정 */
    .faq-container {
        max-width: 600px;
        margin: 50px auto;
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        font-family: 'Noto Sans', sans-serif;
    }

    /* 제목 스타일 */
    .faq-container h1 {
        text-align: center;
        color: #1f2937;
        margin-bottom: 30px;
        font-size: 28px;
        font-weight: 700;
    }

    /* 폼 그룹 스타일 */
    .form-group {
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
    }

    .form-group label {
        margin-bottom: 8px;
        color: #4b5563;
        font-weight: 600;
        font-size: 14px;
    }

    .form-group select,
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

    .form-group select:focus,
    .form-group input[type="text"]:focus,
    .form-group textarea:focus {
        border-color: #2563eb;
        box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
    }

    /* 버튼 그룹 스타일 */
    .button-group {
        display: flex;
        justify-content: flex-end;
        gap: 15px;
        margin-top: 20px;
    }

    .btn-submit {
        background-color: #2563eb;
        color: #ffffff;
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
        text-decoration: none;
        display: inline-block;
        transition: background-color 0.3s ease;
        text-align: center;
    }

    .btn-cancel:hover {
        background-color: #b1b5bb;
    }

    /* 반응형 디자인 */
    @media (max-width: 768px) {
        .faq-container {
            width: 90%;
            padding: 20px 25px;
        }

        .faq-container h1 {
            font-size: 24px;
        }
    }
</style>

<!-- FAQ 추가 폼 컨테이너 -->
<div class="faq-container">
    <h1>FAQ 추가</h1>
    <form action="${pageContext.request.contextPath}/faq/add" method="post" onsubmit="return validateForm();">
        <!-- CSRF 토큰 -->
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <!-- 카테고리 선택 -->
        <div class="form-group">
            <label for="category">카테고리:</label>
            <select name="categoryId" id="category" required>
                <option value="">선택하세요</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
        </div>

        <!-- 질문 입력 -->
        <div class="form-group">
            <label for="question">질문:</label>
            <input type="text" id="question" name="question" maxlength="500" required />
        </div>

        <!-- 답변 입력 -->
        <div class="form-group">
            <label for="answer">답변:</label>
            <textarea id="answer" name="answer" maxlength="1000" rows="5" required></textarea>
        </div>

        <!-- 버튼 그룹 -->
        <div class="button-group">
            <button type="submit" class="btn-submit">추가</button>
            <a href="${pageContext.request.contextPath}/faq" class="btn-cancel">취소</a>
        </div>
    </form>
</div>

<!-- JavaScript 유효성 검사 -->
<script type="text/javascript">
    // 간단한 폼 유효성 검사
    function validateForm() {
        var category = document.getElementById('category').value;
        var question = document.getElementById('question').value.trim();
        var answer = document.getElementById('answer').value.trim();

        if (category === "") {
            alert("카테고리를 선택해 주세요.");
            document.getElementById('category').focus();
            return false;
        }

        if (question === "") {
            alert("질문을 입력해 주세요.");
            document.getElementById('question').focus();
            return false;
        }

        if (answer === "") {
            alert("답변을 입력해 주세요.");
            document.getElementById('answer').focus();
            return false;
        }

        return true;
    }
</script>
