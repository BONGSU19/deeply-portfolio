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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/shop.js"></script>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">

<div class="detail-container">
	<div class="addCart-alert" id="addCart_alert">
	<div class="empty-div">
		<div class="x-btn" id="alert_x_btn">x</div>
		<div class="c-btn">상품을 장바구니에 추가하였습니다</div>
		<a class="c-btn2" href="${pageContext.request.contextPath}/item/cart?user_num=${principal.memberVO.user_num}">장바구니 바로가기 →</a>
	</div>
</div>
<div class="detail-container">
	<div class="addCart-alert" id="addCart_alert2">
	<div class="empty-div">
		<div class="x-btn" id="alert_x_btn2">x</div>
		<div class="c-btn">동일한 상품은 3개까지만 담을 수 있습니다</div>
		<a class="c-btn2" href="${pageContext.request.contextPath}/item/cart?user_num=${principal.memberVO.user_num}">장바구니 바로가기 →</a>
	</div>
</div>
	
	<%-- 아티스트 계정으로 로그인 시작 --%>
	<div class="button page-action">
		<c:if test="${!empty principal.artistVO && principal.artistVO.group_name.equals(agroup.group_name)}">
		<input type="button" class="nonbox-button" value="수정하기" onclick="location.href='/item/update?item_num=${item.item_num}'"/>
		<input type="button" class="nonbox-button" value="삭제하기" id="delete_btn"/>
			<script type="text/javascript">
				const delete_btn = document.getElementById('delete_btn')
				delete_btn.onclick = function() {
					const choice = confirm('삭제하시겠습니까?');
					if (choice) {
						location.replace('delete?item_num=${item.item_num}');
					}
				};
				function goHistory(n) {
			        if (window.history.length > Math.abs(n)) {
			            history.go(n);
			        } else {
			            // 이동 가능한 단계가 없을 경우 기본 페이지로 이동
			            window.location.href = '/item/main';
			        }
			    }
			</script>
			<input type="button" class="nonbox-button" value="등록하기" onclick="location.href='/item/write'">
			<input type="button" class="nonbox-button" value="이전으로" onclick="location.href='/item/main'">
		</c:if>
	</div>
	<%-- 아티스트 계정으로 로그인 끝--%>
	
	<%-- 아티스트 계정으로 로그인 시작 --%>	
		<div class="detail-content-container"> 
		<div class="adetail-content-container">
			<c:if test="${!empty principal.artistVO}">
				<div class="text-title">
						<span class="date">
						<c:if test="${!empty item.item_modifydate}">
    						수정일 : <fmt:formatDate value="${item.item_modifydate}" pattern="yyyy년 MM월 dd일" />
						</c:if>
						<c:if test="${empty item.item_modifydate}">
						    등록일 : <fmt:formatDate value="${item.item_regdate}" pattern="yyyy년 MM월 dd일" />
						</c:if>
						</span>
				</div>
			<div class="item-info">
				<div class="box-shadow">
					<img
						src="${pageContext.request.contextPath}/assets/upload/${item.filename}" class="items-img">
				</div>
					<div class="detail-item-info">
						<ul>
						<li>
								<c:choose>
						            <c:when test="${item.category == 0}">
						                <li class="normal">일반 상품</li>
						            </c:when>
						            <c:when test="${item.category == 1}">
						                <li class="premium">구독회원 전용 상품</li>
						            </c:when>
						        </c:choose>
					        </li>
							<li class="info">상품명 : ${item.item_name}</li>
							<li class="info">가격 : ${item.item_price}원</li>
							<li class="info">수량 : ${item.item_stock}개</li>
							
						</ul>
					</div>
			</div>
		</c:if>
		</div>
		<%-- 아티스트 계정으로 로그인 끝--%>

	<%-- 비로그인, 유저 계정으로 로그인 시작 --%>
		<c:if test="${empty principal.artistVO}">
			<a href="javascript:history.go(-1);" class="btn-back">이전으로</a>
			<div class="text-title1">반갑습니다.</div>
			<div class="item-info">
				<div class="box-shadow">
					<img
						src="${pageContext.request.contextPath}/assets/upload/${item.filename}"
						class="items-img">
				</div>
				<div class="item-info3">
					<ul>
						<li>단독판매</li>
						<c:if test="${item.category == 1 && isMember > 0}">
						<li class="prim">구독회원전용</li>
						</c:if>
						<li class="bold-text">상품명 : ${item.item_name}</li>
						<li class="text-price">가격 : ${item.item_price}원</li>
						<li class="max-purchase">회원당 최대 3개까지 구매가능합니다.</li>
					</ul>
					<div class="quantity-container q-box">
						<div class="quantity-name">${item.item_name}</div>
						<div class="v-center">
							<div class="quantity-box">
								<button class="quantity-btn a-center" id="minus_btn">-</button>
								<span class="quantity-number a-center" id="quantity" data-value="1">1</span>
								<button class="quantity-btn a-center" id="plus_btn">+</button>
							</div>
							<div class="price-box">
								<div class="price-div" id="price_total" data-price="${item.item_price}">
    								<fmt:formatNumber value="${item.item_price}" type="number" groupingUsed="true" />원
								</div>
							</div>
						</div>
						<input type="hidden" name="item_num" id="item_num" value="${item.item_num}" data-num="${item.item_num}">
					</div>
					<input type="button" class="box-button3" value="장바구니 추가" id="add_cart">
					
					
					<input type="button" class="box-button3" id="order_btn" value="바로 구매하기">
				</div>
			</div>
		</c:if>
	<%-- 유저 계정으로 로그인 끝 --%>
	
	<%-- 아티스트, 유저계정 공통 --%>
	<div class="des-text-title">상품설명</div>
	<div class="box-shadow note-editable">
		
	<c:if test="${fn:endsWith(item.filename,'.jpg') ||
                    fn:endsWith(item.filename,'.JPG') ||
                    fn:endsWith(item.filename,'.jpeg') ||
                    fn:endsWith(item.filename,'.JPEG') ||
                    fn:endsWith(item.filename,'.gif') ||
                    fn:endsWith(item.filename,'.GIF') ||
                    fn:endsWith(item.filename,'.png') ||
                    fn:endsWith(item.filename,'.PNG')}">
        <c:out value="${item.item_description}" escapeXml="false"/>
    </c:if>
    </div>

	<%-- 반품/교환 규정 포함--%>  		  
	
   			<div class="item-policy">
   			<div class="policy-title">반품/교환 규정</div>
     		   <%@ include file="returnPolicy.jsp" %> 
  		  </div>
	</div>

</div>