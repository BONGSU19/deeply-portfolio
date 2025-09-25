<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%><!-- 로그인 관련 태그 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	let result = '${result}';
	if (result == 'success') {
		alert('글 등록이 완료되었습니다.');
	}
</script>



	
		<c:if test="${!empty principal && empty principal.memberVO}">
			<input type="button" class="box-button4" value="등록하기" onclick="location.href='/item/write'">
		</c:if>
	<div class="listcontent-container item-container">
		<c:if test="${count == 0}">
			<div class="result-display">표시할 게시물이 없습니다.</div>
		</c:if>
			<c:set var="group_cnt" value="0"/>
			<c:set var="user_num" value="0"/>
		    <c:forEach items="${group}" var="group">
		    <c:set var="loop_flag" value="true"/>
				<div class="main-items1">
					<div class="group-text">${group.group_name}</div>
					<div class="value-list">
						<c:forEach items="${list}" var="item" varStatus="status" begin="${group_cnt}">
							<c:if test="${loop_flag}">
								<div class="item-card1">
									<a href="${pageContext.request.contextPath}/item/detail?item_num=${item.item_num}">
									<img src="${pageContext.request.contextPath}/assets/upload/${item.filename}" class="main-item-img">
									</a>
									<hr class="custom-hr" noshade="noshade" width="100%">
									<span class="item-name list-text">${item.item_name}</span>
									<span class="item-name list-price">${item.item_price}</span>
								</div>
								<c:if test="${(item.rnum==4 and item.group_cnt >= 4) or (item.rnum==item.group_cnt and item.group_cnt < 4)}">
									<c:set var="group_cnt" value="${group_cnt + status.count}"/>
									<c:set var="loop_flag" value="false"/>	
									<c:set var="user_num" value="${item.user_num}"/>
								</c:if>
							</c:if>
						</c:forEach>
					</div>
					<a class="arrow1" href="list?user_num=${group.group_num}">전체보기 →</a>
				</div>
			</c:forEach>
	</div>
