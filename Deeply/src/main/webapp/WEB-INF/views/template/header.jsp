<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!-- 상단 시작 -->
<header>  
	<div class="align-left header-left"> 
			<c:if test="${empty principal || principal.memberVO.auth < 9}"><%-- 사용자 화면 링크 --%>
	<a href="${pageContext.request.contextPath}/main/main"><img alt=""
			src="${pageContext.request.contextPath}/assets/images/DeeplyLoginLogo.png"></a>
	</c:if>
	<c:if test="${!empty principal && principal.memberVO.auth == 9}"><%-- 관리자 화면 링크 --%>
	<a href="${pageContext.request.contextPath}/main/main"><img alt=""
			src="${pageContext.request.contextPath}/assets/images/DeeplyLoginLogo.png"></a>
	</c:if>
	<c:if test="${!empty principal && principal.artistVO!=null}"><%-- 아티스트 화면 링크 --%>
	<a href="${pageContext.request.contextPath}/main/main"><img alt=""
			src="${pageContext.request.contextPath}/assets/images/DeeplyLoginLogo.png"></a>
	</c:if>
	</div>
	<div class="align-right header-right">
	
		<div class="second-text">
		<c:if test="${empty principal}">
			<a href="${pageContext.request.contextPath}/member/login">로그인</a>
			<a href="${pageContext.request.contextPath}/member/registerUser">회원가입</a>
		</c:if>
		<%--=============사용자 비로그인 영역 끝=========--%>
		<%--=============사용자 로그인 영역 시작=========--%>
		
		<c:if test="${!empty principal && principal.memberVO.auth < 9}">
			<a href="${pageContext.request.contextPath}/member/mypage">MY페이지</a>
		</c:if>
		<c:if test="${!empty principal && principal.artistVO!=null}">
			<a href="${pageContext.request.contextPath}/member/mypage">MY페이지</a>
		</c:if>
		<c:if test="${empty principal.artistVO && !empty principal.memberVO}">
    <a href="/item/cart?user_num=${principal.memberVO.user_num}">
        <img class="cart-img" src="${pageContext.request.contextPath}/assets/images/hj/cart.png" style="width: 1.3vw; height: 1.3vw;">
    </a>
</c:if>
		<%--=============사용자 로그인 영역 끝=========--%>
		<%--=====사용자,관리자 공통 로그인 영역 시작======--%>
		<c:if test="${!empty principal}">
			<img src="${pageContext.request.contextPath}/member/photoView"
				width="30vw" height="30vw" class="my-photo">
			<c:if test="${!empty principal.memberVO.nick_name}">
		<span class="user_name">${principal.memberVO.nick_name}</span>님
		</c:if>
			<c:if test="${!empty principal && principal.artistVO!=null}">
			<span class="user_name">${principal.artistVO.name}</span>님
			</c:if>
			<%-- 아래와 같이 폼을 만들고 post방식으로 전달해야 스프링
		     시큐리티가 지원하는 로그아웃 기능을 사용할 수 있음 --%>
			<form action="${pageContext.request.contextPath}/member/logout"
				method="post" style="display: none;" id="frm_logout">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}">
			</form>
			<a class="header-logout" href="#" id="logout">로그아웃</a>
			<%-- a 링크에서 submit 기능을 얻기위해 아래 스크립트 활요 --%>
			<script type="text/javascript">
			const logout = document.getElementById('logout');
			logout.onclick=function(event){
				document.getElementById('frm_logout').submit();
				event.preventDefault();
			}
			</script>
		</c:if>
		<%--=====사용자,관리자 공통 로그인 영역 끝========--%>
	</div>
		<%--=============사용자 비로그인 영역 시작=========--%>
		<div class="first-text">
		<%--로그인전--%>
			<c:if test="${empty principal}">
				<a href="${pageContext.request.contextPath}/artist/list">아티스트</a>
				<a href="${pageContext.request.contextPath}/item/main">shop</a>
			</c:if>
		<%--일반회원--%>
			<c:if test="${!empty principal && principal.memberVO.auth < 9}">
				<a href="${pageContext.request.contextPath}/commu/list">커뮤니티</a>
				<a href="${pageContext.request.contextPath}/artist/list">아티스트</a>
				<a href="${pageContext.request.contextPath}/notice/list">아티스트 공지</a>
				<a href="${pageContext.request.contextPath}/item/main">shop</a>
				
			</c:if>
		<%--아티스트--%>
			<c:if test="${!empty principal && principal.artistVO!=null}">
				<a href="${pageContext.request.contextPath}/item/main">shop</a>
				<a href="${pageContext.request.contextPath}/artist/list">아티스트</a>
				<a href="${pageContext.request.contextPath}/chat/chWrite">채팅</a>
			
			</c:if>
			
		
		<%--관리자--%>
			<c:if test="${!empty principal && principal.memberVO.auth == 9}">
				<a href="${pageContext.request.contextPath}/admin/admin_main">관리자</a>
	
			</c:if>
		</div>
	</div>
	<div class="clear:both"></div>
</header>
<!-- 상단 끝 -->






