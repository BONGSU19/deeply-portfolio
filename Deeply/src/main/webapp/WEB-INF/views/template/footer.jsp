<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 하단 시작 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<div class="align-left" style="background-color:black; padding-top:50px;">
<footer>
<div style="height:26px; margin-bottom:10px;">
	<div class="ft-1">
		
	
	<div class="link"><a href="${pageContext.request.contextPath}/artist/list">아티스트</a></div>
	<div class="link"><a href="${pageContext.request.contextPath}/item/main">shop</a></div>
	<div class="link"><a href="${pageContext.request.contextPath}/commu/list">커뮤니티</a></div>
	</div>
	<div class="ft-2">
	<c:if test="${empty principal}">
		<div class="link"><a href="${pageContext.request.contextPath}/member/login">로그인</a></div>
		<div style="display:inline-block;"><a href="${pageContext.request.contextPath}/member/registerUser">회원가입</a></div>
	</c:if>
	<c:if test="${!empty principal && principal.memberVO.auth < 9}">
			<a href="${pageContext.request.contextPath}/member/myPage1">MY페이지</a>
		</c:if>
		<c:if test="${!empty principal && principal.artistVO!=null}">
			<a href="${pageContext.request.contextPath}/member/myPage1">MY페이지</a>
		</c:if>
	</div>
</div>
<div style="height:26px; margin-bottom:10px;">
	<div class="ft-3">

	</div>
</div>
<div style="height:26px; margin-bottom:10px;">
	<div class="ft-3">

	</div>
</div>
<div class="line"></div>
<div class="footer-img"><img src="${pageContext.request.contextPath}/assets/image_bundle/footer.png"></div>
</footer>
</div>
<!-- 하단 끝 -->