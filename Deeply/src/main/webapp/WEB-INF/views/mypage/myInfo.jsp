<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!-- 마이페이지 시작 -->
<div class="page-main">
	<div class="myInfo">
		<img src="${pageContext.request.contextPath}/" width="100"
			height="100" class="my-photo" style="item-align: center;">
		<div class="align-center">
			<c:if test="${member.auth==0}">
				<p>일반회원</p>
			</c:if>
			<c:if test="${member.auth==1}">
				<p>유료회원</p>
			</c:if>
		</div>
		<table class="myTable">
			<tr>
				<td class="align-center"><b>아이디 </b>${member.name}</td>
				<td class="align-center"><b>연락처 </b>${member.phone}</td>
			</tr>
			<tr>
				<td class="align-center"><b>닉네임 </b>${member.nick_name}</td>
				<c:if test="${!empty member.user_bal}">
					<td class="align-center"><b>예치금 </b>${member.user_bal}원</td>
				</c:if>
				<c:if test="${empty member.user_bal}">
					<td class="align-center"><b>예치금 </b>0원</td>
				</c:if>
			</tr>
			<tr>
				<td class="align-center"><b>이메일 </b>${member.email}</td>
				<c:if test="${!empty member.social_name}">
					<td class="align-center"><b>소셜연결 </b>${member.social_name}</td>
				</c:if>
			</tr>
			<tr>
				<td class="align-center"><b>주소 </b>${member.zipcode}
					${member.address1} ${member.address2}</td>
			</tr>
		</table>
	</div>
</div>
