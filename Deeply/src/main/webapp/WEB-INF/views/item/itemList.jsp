<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	 function goHistory(n) {
	        if (window.history.length > Math.abs(n)) {
	            history.go(n);
	        } else {
	            // 이동 가능한 단계가 없을 경우 기본 페이지로 이동
	            window.location.href = '/item/main';
	        }
	    }
</script>



<div class="content-container">
	<div class="button page-action">
		<c:if test="${!empty principal.artistVO}">
			<input type="button" class="nonbox-button" value="등록하기" onclick="location.href='/item/write'">
		</c:if>
			<a href="javascript:history.go(-1);" class="btn-back">이전으로</a>
	</div>
		<div class="listcontent-container2 item-container">
			<!-- <div class="artist-name">반갑습니다. </div> -->
		<c:if test="${count == 0}">
			<div class="result-display">표시할 게시물이 없습니다.</div>
			</c:if>
			<c:if test="${count > 0}">
				<c:forEach var="item" items="${list}" varStatus="status">
					<div class="item-cards">
						<a href="${pageContext.request.contextPath}/item/detail?item_num=${item.item_num}">
							<img src="${pageContext.request.contextPath}/assets/upload/${item.filename}" class="item-img">
						</a>
						<hr class="custom-hr3" noshade="noshade" width="100%">
						<span class="item-name list-text">${item.item_name}</span>
						<span class="item-name list-price">${item.item_price}</span>
						
					</div>
					
					<c:if test="${(status.index + 1) % 4 == 0}">
						<hr class="custom-hr" noshade="noshade" width="100%">
					</c:if>
				</c:forEach>
			</c:if>
		</div>
		<!-- 페이징 -->
				<div class=list-paging>
					${page}
				</div>
</div>