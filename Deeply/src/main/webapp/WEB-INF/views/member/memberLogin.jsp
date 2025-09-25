<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 메인 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/member.login.js"></script>
<div class="main-div" style="height:700px;">
	<!-- 로그인 컨텐츠 부분 -->
	<div class="login">
		<img class="logo"
			src="${pageContext.request.contextPath}/assets/images/DeeplyLoginLogo.png">
		<form action="/member/login" method="post" id="member_login">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<c:if test="${!empty error && error == 'error'}">
			<div class="align-center error-invalid error-color">아이디 또는 비밀번호 불일치</div>
			</c:if>
			<c:if test="${!empty error && error == 'error_noAuthority'}">
			<div class="align-center error-invalid error-color">정지 회원입니다.</div>
			</c:if>
			<input type="text" name="id" id="id" placeholder="아이디" autocomplete="off"/>
			<div id="error_id" class="error-color"></div>
			<input name="passwd_hash" id="passwd_hash" type="password" placeholder="비밀번호" />
			<div id="error_passwd" class="error-color"></div>
			<div class="line-with-text">
						<span>or</span>
					</div>
			<table class="login-button">
				<tr>
					<td><a style="text-decoration:none;" href="${pageContext.request.contextPath}/oauth2/authorization/naver"><img class="inline-img" alt=""
						src="${pageContext.request.contextPath}/assets/images/naver.png">
						<span style="text-decoration:none; color:black;" class="inline-text">네이버로 로그인하기</span></a></td>
					<td><a style="text-decoration:none;" href="${pageContext.request.contextPath}/oauth2/authorization/kakao"><img class="inline-img" alt=""
						src="${pageContext.request.contextPath}/assets/images/kakao.png">
						<span style="text-decoration:none; color:black;" class="inline-text">카카오로 로그인하기</span></a></td>
				</tr>
				<tr>
					<td><a style="text-decoration:none; color:black;" href="/member/findID">아이디 찾기</a></td>
					<td><a style="text-decoration:none; color:black;" href="/member/findPasswd">비밀번호 찾기</a></td>
				</tr>
			</table>
			<input type="submit" value="로그인">
		</form>
	</div>


</div>