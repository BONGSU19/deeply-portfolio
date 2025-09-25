<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hj.css">


<div class="order-container">
	<div class="text-title3">공연 예매 내역</div>
	<hr class="custom-hr4" noshade="noshade">

	<c:forEach var="book" items="${list}">
		<div class="text-title4">예매일 : ${book.booking_date}</div>
		<div class="text-title5">받으시는 분 : ${book.deliver_name}</div>
		<div class="order-date-section">
			<hr class="custom-hr2" noshade="noshade" width="100%">
			<div class="order-quantity-container2">
				<img class="booking-list-img"
					src="${pageContext.request.contextPath}/assets/upload/${book.perf_photo}">
				<div class="quantity-name2">
					<span class="quantity-name3"><b>${book.perf_title}</b></span> <span
						class="quantity-name3">수량 ${book.booked_seat}개</span> <span
						class="quantity-name4">가격 ${book.total_price}원</span>
				</div>
			</div>
			<hr class="custom-hr2" noshade="noshade" width="100%">
		</div>
		<hr class="custom-hr4" noshade="noshade">
	</c:forEach>

</div>