<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%><!-- 로그인 관련 태그 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/cart.js"></script>

	<%-- 장바구니에 상품 없을 때 --%>
		<c:if test="${empty cart}">
			<div class="cart-div0">
				<hr class="custom-hr2" noshade="noshade" width="100%">
					<div class="empty-div"></div>
						<span>장바구니가 비었습니다.</span><br>
						<span><img class="cart-img" src="${pageContext.request.contextPath}/assets/images/hj/cart.png"></span><br>
						<a href="${pageContext.request.contextPath}/item/main" class="shop-link">지금 shop으로 이동하기 →</a>
					<div class="empty-div"></div>
				<hr class="custom-hr2" noshade="noshade" width="100%">
			</div>
		</c:if>
	<%-- 장바구니에 상품 있을 때 --%>
	<div class="cart-div5">
		<c:if test="${!empty cart}">
		
			<div class="item-info4">
			<div class="top-5"></div>
			<c:forEach var="cart_item" items="${cart}">
			<div class="vertical-center cart-div-container">
				<div class="cart-img-div">
					<img src="${pageContext.request.contextPath}/assets/upload/${cart_item.filename}">
				</div>
				<div>
					<div class="item-grade vertical-center">
						<div class="only-sell">단독판매</div>
						<c:if test="${cart_item.isPremium == 1 && cart_item.isMember > 0}">
						<div class="premium-sell">구독회원전용</div>
						</c:if>
					</div>
					<div class="item-cart-name">${cart_item.item_name}</div>
					<div class="item-cart-box q-box">
						<div class="item-sub-name">${cart_item.item_name}</div>
						<div class="item-cart-area v-center">
							<div class="quantity-box">
								<button class="quantity-btn a-center cminus_btn">-</button>
								<span class="quantity-number a-center quantity quan_amount" id="quantity" data-value="${cart_item.order_quantity}">${cart_item.order_quantity}</span>
								<button class="quantity-btn a-center cplus_btn">+</button>
							</div>
							<div class="cart-item-price price_total" data-price="${cart_item.item_price}">
								<fmt:formatNumber value="${cart_item.item_price * cart_item.order_quantity}" type="number" groupingUsed="true" />원
							</div>
						</div>
						
					</div>
					<div class="item-cart-btn quantity" data-value="${cart_item.order_quantity}">
						<button id="cart_purchase_btn_${cart_item.item_num}" class="cart-btn" data-value="${cart_item.item_num}">구매하기</button>
						<a href="${pageContext.request.contextPath}/item/delete_cart?cart_num=${cart_item.cart_num}"><img class="trash-img" src="${pageContext.request.contextPath}/assets/images/hj/trash.svg"></a>
					</div>
					<input type="hidden" name="item_num" id="item_num" class="cart-item-num" data-num="${cart_item.item_num}">
					<input type="hidden" id="this_cart_num_${cart_item.cart_num}" data-value="${cart_item.cart_num}">
				</div>
			</div>
			</c:forEach>
				<div class="result-bar">
					<div class="price-box">
						<div class="cart-total-price">
						<input type="hidden" id="totalAmount" data-value="${totalAmount}">
							<span id="cart_val"></span> + 배송비 <span id="delivery_fee"></span>
						</div>
						<div class="cart-total-price vertical-center">
							 총 상품 금액: <span id="cart_total_val"></span>
						</div>
					</div>
					<div class="submit-btn" id="cart_submit_btn">결제하기</div>
				</div>
			</div>
			<div class="hidden-check-box">
				<div class="check-title">결제 확인</div>
				<div class="hidden-close-btn">닫기</div>
				<div>
					<div class="vertical-center hidden-items">
						<c:forEach var="cart_item" items="${cart}">
							<div>
							<div class="hidden-img-div">
								<img src="${pageContext.request.contextPath}/assets/upload/${cart_item.filename}">
							</div>
							<div class="hidden-name">${cart_item.item_name}</div>
							<div class="hidden-quantity">${cart_item.order_quantity}개</div>
							</div>
						</c:forEach>
					</div>
					<hr>
					<span class="hidden-total">총 결제 금액: <span id="check_total" data-price="0"></span></span>
					<div class="hidden-submit-btn">전체 결제</div>
					
				</div>
			</div>
			
		</c:if>
</div>