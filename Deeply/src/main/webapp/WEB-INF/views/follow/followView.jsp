<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/myFollow.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<div class="main-div">
	<h2>나의 구독 아티스트</h2>
	<hr>
	<div class="myInfo">
		<div class="mypage-1" style="margin: 0 auto; text-align: center;">
			<div class="profile-image" style="align-items:center; text-align: center;">
				<img src="${pageContext.request.contextPath}/member/photoView"
					width="200" height="200" class="my-profile-img" id="photo_btn">
			</div>
		<c:if test="${member.auth==0}">
			<p style="text-align: center; font-size: 18px; color: #00A2CF; margin: 15px 0 40px 0;">
				<b>일반회원</b>
			</p>
		</c:if>
		<c:if test="${member.auth==1}">
			<p style="text-align: center; font-size: 18px; color: #00A2CF; margin: 15px 0 40px 0;">
				<b>유료회원</b>
			</p>
		</c:if>
		</div>
		<div>
		<table class="myTable">
			<tr>
				<td><b>아이디 </b>${member.name}</td>
				<td><b>연락처 </b>${member.phone}</td>
			</tr>
			<tr>
				<td><b>닉네임 </b>${member.nick_name}</td>
				<td><b>예치금 </b>${member.user_bal}원 <a
					href="${pageContext.request.contextPath}/charge/userBal"><img
						src="${pageContext.request.contextPath}/assets/images/hr2/Discount Star 4.svg"
						width="20px" height="20px"></a></td>
			</tr>
			<tr>
				<td><b>이메일 </b>${member.email}</td>
				<c:if test="${!empty member.social_name}">
					<td><b>소셜연결 </b>${member.social_name}</td>
				</c:if>
			</tr>
			<tr>
				<td colspan="2"><b>주소 </b>${member.zipcode} ${member.address1}
					${member.address2}  <a href="${pageContext.request.contextPath}/member/myPage1"><img
						src="${pageContext.request.contextPath}/assets/images/hr2/Group 97.svg"
						width="20px" height="20px"></a></td>
			</tr>
		</table>
		</div>
	</div>
	<div class="followList">
		<c:if test="${count == 0}">
			<div class="result-display">팔로우하는 아티스트가 없습니다.</div>
		</c:if>
		<c:if test="${count > 0}">
			<div class="profile-list" data-header="${_csrf.headerName}" data-token="${_csrf.token}">
				<c:forEach var="followList" items="${followList}">
					<div class="profile-item">
						<img src="/member/photoView2?user_num=${followList.user_num}" class="my-photo2">
						<img class="my-photo" src="${pageContext.request.contextPath}/assets/images/hr2/follow.svg">
                        <div class="profile-text"><b>${followList.group_name}<br>${followList.name}</b></div>
					</div>
				</c:forEach>
			</div>
			<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>