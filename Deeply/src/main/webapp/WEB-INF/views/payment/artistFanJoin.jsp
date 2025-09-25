<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<%@ include file="fanModal.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<div class="page-main" style="width:80%;">
	<div class="my-bal">
		<h2 style="font-size:20px;">나의 예치금</h2>
		<h2 style="text-align:center; font-size:20px;">현재 금액<span style="font-size:35px; margin:0 3px 0 17px;"><b>${user_bal}</b></span>원</h2>
	</div>
	<hr size="3px">
	<div class="join-fan">
	<c:if test="${artist.group_name == artist.name}">
			<h2 class="fan-title">
				아티스트 <span class="blue-artiname">${artist.name}</span>님의 멤버십
			</h2>
		</c:if>
		<c:if test="${artist.group_name != artist.name}">
			<h2 class="fan-title" style="font-size:20px;">
				아티스트 <span class="blue-artiname" style="font-size:27px; margin:0 2px;">${artist.group_name} ${artist.name}</span>님의 멤버십
			</h2>
		</c:if>
		<img src="/member/photoView2?user_num=${artist.user_num}" class="my-photo2">
		<h2 class="blue-artiname" style="font-size:18px;"> 일반회원 → 유료회원</h2>
		<h2 style="font-size:20px;">월 <span >6,500</span>원</h2>
		<input type="button" class="blue-fan-btn" value="멤버십 가입" onclick="showModal('joinFan')">
		<br>
		<input type="button" class="grey-fan-btn" value="멤버십 해지" onclick="showModal('removeFan')">
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/fan.js"></script>