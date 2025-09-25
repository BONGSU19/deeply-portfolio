<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%><!-- 로그인 관련 태그 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

	<div class="order-container">
	    <div class="text-title3">주문내역</div>
	   	<hr class="custom-hr4" noshade="noshade">
	   	<!-- <hr class="custom-hr4" noshade="noshade">
	   		 <div class="order-tabs">
	       		 <div class="order_list">주문내역</div>
	       	 </div>
	    <hr class="custom-hr4" noshade="noshade" > -->
	   
	    <!-- 주문 내역 반복 -->
	    <c:if test="${empty orderList}">
					<div class="empty-div"></div>
						<span class="not-order">주문내역이 없습니다.<br>
						<span><img class="cart-img" src="${pageContext.request.contextPath}/assets/images/hj/order-list.png"></span><br>
						<a href="${pageContext.request.contextPath}/item/main" class="shop-link">지금 shop으로 이동하기 →</a></span>
					<div class="empty-div"></div>
				<hr class="custom-hr4" noshade="noshade">
	    </c:if>
	    
	    <c:if test="${!empty orderList}">
	    <c:forEach var="order" items="${orderList}">
	            <div class="text-title4">주문 날짜 : ${order.order_date}</div>
	            	<div class="text-title5">받으시는 분 : ${order.name}</div>
	            	<div class="order-date-section">
						<hr class="custom-hr2" noshade="noshade" width="100%">
							<div class="order-quantity-container2">
								<img src="${pageContext.request.contextPath}/assets/upload/${order.filename}" class="cart-img2">
								<div class="quantity-name2">
									<span class="quantity-name3">상품명 : ${order.item_name}</span>
									<span class="quantity-name3">수량 : ${order.item_quantity}개</span>
									<span class="quantity-name4">가격 : <fmt:formatNumber value="${priceByItems}" type="number" groupingUsed="true" />원</span>
								</div>
							</div>
							<hr class="custom-hr2" noshade="noshade" width="100%">
					</div>
				<hr class="custom-hr4" noshade="noshade">
		</c:forEach>
		</c:if>
		
	 </div>
            
