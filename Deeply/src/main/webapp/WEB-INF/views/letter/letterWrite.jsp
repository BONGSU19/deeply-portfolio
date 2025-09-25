<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/letter.js"></script>
<div class="letter-main background-black">
	<div class="letter-div1">
		<img src="${pageContext.request.contextPath}/assets/images/mail.png">
	</div>
	<div class="font-white bold-title align-center top-10 font-2">나의 아티스트와의 소통</div>
	<div class="letter-aname align-center top-10">
		<hr class="width-60 background-darkblue">
		<div class="aname-div bold-title font-2 align-center">${artist.name}</div>
		<hr class="width-60 background-darkblue">
	</div>
	<div>
		<div class="font-white bold-title align-center width-20 font-2 top-10">MY LETTER</div>
		<div class="width-80 background-darkblue height-0_2 top-2"></div>
	</div>
	<div class="font-white bold-title align-center width-20 font-1 top-3">잔여 발송 가능 편지 ${letter_limit}/3</div>
	<div class="space-10vw"></div>
	<div id="letter_form" class="letter-btn-div vertical-center">
		<div class="letter-btn font-white bold-title align-center font-1"><a href="${pageContext.request.contextPath}/letter/list?artist_num=${artist.user_num}">보낸편지</a></div>
		<div class="letter-btn font-white bold-title align-center font-1"><a href="${pageContext.request.contextPath}/letter/reply?artist_num=${artist.user_num}">아티스트의 답장</a></div>
	</div>
	<div class="letter-body">
		<form:form modelAttribute="letterVO" action="write" id="write_letter" enctype="multipart/form-data">
		<c:if test="${!empty principal.memberVO}">
		<form:hidden path="user_num" value="${principal.memberVO.user_num}"/>
		</c:if>
		<form:hidden path="artist_num" value="${artist.user_num}"/>
		<form:hidden path="replied" value="0"/>
		<div class="letter-box">
			<div class="letter-background">
			<img class="preview-img">
			
			<div class="letter-photo">
				<button class="letterPhoto-btn" id="letterPhoto_btn">사진 첨부</button>
				<input type="file" class="display-none" name="upload" id="letterPhoto_upload">
			</div>
			<div class="letter-title">
				<form:input path="letter_title" class="ltitle-input" placeholder="편지 제목 입력"/>
				<form:errors path="letter_title" cssClass="error-color"/>
			</div>
			<div class="letter-content">
				<form:textarea path="letter_content" class="lcontent-input" vplaceholder="편지 내용 입력"/>
			</div>
			<div class="left-3"><form:errors class="left-3" path="letter_content" cssClass="error-color"/></div>
			</div>
		</div>
		<div class="submit-div height-3 align-right">
			<c:if test="${letter_limit > 0}">
			<form:button class="submit-btn">전송</form:button>
			</c:if>
			<c:if test="${letter_limit == 0}">
			<form:button id="reject_letter" class="submit-btn">전송</form:button>
			</c:if>
		</div>
		</form:form>
	</div>
	<div class="space-10vw"></div>
</div>