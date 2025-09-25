<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Frequently Asked Questions (FAQ)</title>
<style>
/* 기존 스타일 유지 또는 개선 */
.faq-container {
  background-color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.faq-header {
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center; /* Vertical center alignment */
  align-items: center; /* Horizontal center alignment */
  width: 100%; /* Same width as faq-item */
  max-width: 1025px; /* Same max width as faq-item */
  font-weight: 700;
  padding: 20px 40px;
  margin-top: 20px;
}

.faq-title {
  color: #000;
  text-align: center;
  font: 64px/1 'Red Hat Display', sans-serif;
}

.contact-link {
  margin-left: auto; /* Move to the right end */
  color: #b0adad;
  font: 16px 'Manrope', sans-serif;
}

.faq-item {
  border-radius: 8px;
  background-color: #090909;
  display: flex;
  flex-direction: column;
  width: 95%;
  max-width: 900px;
  color: #fff;
  padding: 20px;
  font-size: 20px; /* 24px -> 20px로 축소 */
  margin-top: 20px;
  cursor: pointer;
}

.faq-icon {
  width: 25px;
  margin-right: 10px;
  transition: transform 0.3s ease;
}

.faq-question h2 {
  margin: 0; /* h2 기본 마진 제거 */
  font-size: 1em; /* 상위 요소 폰트 사이즈 상속 */
  flex-grow: 1; /* 남는 공간 채우기 */
}

.faq-question {
  display: flex;
  align-items: center;
  gap: 15px; /* 요소 간 간격 축소 */
  padding: 10px 0; /* 내부 패딩 조정 */
}

.faq-answer {
  display: none; /* Hidden by default */
  margin-top: 15px;
  padding: 10px;
  background-color: #333;
  border-radius: 8px;
  font-size: 16px;
  line-height: 1.5;
}

.faq-answer.visible {
  display: block;
}

.admin-actions {
  flex-shrink: 0; /* 관리자 버튼 영역 고정 */
  white-space: nowrap; /* 버튼 줄바꿈 방지 */
}

.admin-actions a {
    color: #ff0000;
    margin-right: 10px;
    text-decoration: none;
}

/* 공통 스타일 */
.admin-link {
    display: inline-block;
    margin: 10px 5px; /* 여백 */
    padding: 8px 16px; /* 내부 공간 */
    font-size: 14px; /* 글자 크기 */
    font-weight: bold; /* 글자 굵기 */
    color: #ffffff; /* 글자 색상 (흰색) */
    background-color: #000; /* 기본 배경색 (파란색) */
    text-decoration: none; /* 밑줄 제거 */
    border-radius: 4px; /* 모서리 둥글게 */
    transition: background-color 0.3s ease, transform 0.2s ease; /* 호버 효과 */
}

/* 호버 효과 */
.admin-link:hover {
    background-color: #0056b3; /* 어두운 파란색 */
    transform: translateY(-2px); /* 약간 위로 이동 */
}

/* 활성화 상태 */
.admin-link:active {
    background-color: #003f7f; /* 더 어두운 파란색 */
    transform: translateY(0); /* 원래 위치 */
}



</style>
</head>
<body>
<div class="faq-container">
  <!-- FAQ Header -->
  <div class="faq-header">
    <h1 class="faq-title">자주 묻는 질문</h1>
    <a href="${pageContext.request.contextPath}/contact/inquiryForm" class="contact-link">1대1 문의 하러가기</a>
   	<div class="admin-button">
   		<c:if test="${isAdmin}">
    		<a href="${pageContext.request.contextPath}/faq/add" class="admin-link">Add FAQ</a>
			<a href="${pageContext.request.contextPath}/faq/categories" class="admin-link">Manage Categories</a>
		</c:if>
	</div>
  </div>

  <!-- Grouping FAQs by Category -->
  <c:forEach var="category" items="${categories}">
    <div class="faq-category">
      <h2>${category.categoryName}</h2>
      <c:forEach var="faq" items="${faqs}">
        <c:if test="${faq.categoryId == category.categoryId}">
          <!-- FAQ Item -->
          <div class="faq-item">
            <div class="faq-question">
              <img
                src="https://cdn.builder.io/api/v1/image/assets/TEMP/12b36863eb113cecbe74e8f24b9197734fb736dd08b58a5c542ce6cdc22a1f5c"
                alt="Icon"
                class="faq-icon"
              />
              <h2>${faq.question}</h2>
              <c:if test="${isAdmin}">
                  <div class="admin-actions">
                      <a href="${pageContext.request.contextPath}/faq/edit/${faq.faqId}">수정</a>
                      <a href="${pageContext.request.contextPath}/faq/delete/${faq.faqId}" onclick="return confirm('Are you sure you want to delete this FAQ?');">삭제</a>
                  </div>
             </c:if>
            </div>
            <div class="faq-answer">
              <p>${faq.answer}</p>
            </div>
          </div>
        </c:if>
      </c:forEach>
    </div>
  </c:forEach>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
  const faqItems = document.querySelectorAll(".faq-item");

  faqItems.forEach((item) => {
    const question = item.querySelector(".faq-question");
    const answer = item.querySelector(".faq-answer");
    const icon = item.querySelector(".faq-icon");

    question.addEventListener("click", () => {
      const isVisible = answer.classList.contains("visible");

      // Reset all FAQs
      document.querySelectorAll(".faq-answer").forEach((ans) => ans.classList.remove("visible"));
      document.querySelectorAll(".faq-icon").forEach((ic) => {
        ic.src =
          "https://cdn.builder.io/api/v1/image/assets/TEMP/12b36863eb113cecbe74e8f24b9197734fb736dd08b58a5c542ce6cdc22a1f5c";
      });

      if (!isVisible) {
        answer.classList.add("visible");
        icon.src =
          "https://cdn.builder.io/api/v1/image/assets/TEMP/7b42bb8c3c2581d45372b7ca3d972196219c939457af4b9bd4e6a739575a970f";
      }
    });
  });
});
</script>
</body>
</html>
