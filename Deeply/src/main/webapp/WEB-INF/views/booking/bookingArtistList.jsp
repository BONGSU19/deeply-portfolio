<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hj.css">
<div class="order-container">
	<div class="font-1_5 align-center top-10 bold-title">이벤트 관리</div>
	<hr class="custom-hr4 top-2" noshade="noshade">

	<c:forEach var="event" items="${list}">
		
		<div class="order-date-section top-3">
			<div class="order-quantity-container2">
				<img class="booking-list-img"
					src="${pageContext.request.contextPath}/assets/upload/${event.perf_photo}">
				<div class="perf-title-div">${event.perf_title}</div>
				<div class="perf-date-div">
				<c:if test="${empty event.end_date}">
					공연일: ${event.perf_date}
				</c:if>
				<c:if test="${!empty event.end_date}">
					공연일: ${event.perf_date} ~ ${event.end_date}
				</c:if>
				</div>
				<div class="perf-btn-div">
					<b><a href="${pageContext.request.contextPath}/booking">수정</a></b>	
					<b><a href="${pageContext.request.contextPath}/booking/delete?perf_num=${event.perf_num}&group_num=${event.artist_num}">삭제</a></b> 
				</div>
				</div>
			

		</div>
		<hr class="custom-hr4 top-3" noshade="noshade">
	</c:forEach>
	<div class="align-center top-5">${page}</div>
	<div class="space-10vw"></div>
</div>