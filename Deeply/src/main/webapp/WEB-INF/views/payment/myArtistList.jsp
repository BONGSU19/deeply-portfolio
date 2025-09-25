<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/fan.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<div class="page-main">
	<div class="main-div">
		<h2>나의 멤버십 구독 아티스트</h2>
		<hr size="3px">
		<div class="myArtistList">
			<c:if test="${count == 0}">
				<div class="result-display">구독하는 아티스트가 없습니다.</div>
			</c:if>
			<c:if test="${count > 0}">
				<table class="profile-list" data-header="${_csrf.headerName}"
					data-token="${_csrf.token}">
					<c:forEach var="myArtistList" items="${myArtistList}">
						<div class="profile-item">
							<img src="/member/photoView2?user_num=${myArtistList.fan_artist}"
								class="my-photo2"><br>
							<a class="membership-blue-btn" href='${pageContext.request.contextPath}/fan/selectArtist?user_num=${myArtistList.fan_artist}'>멤버십</a>
							<div class="profile-text">
								<div class="group-name"><b>${myArtistList.group_name}</b></div>
								<div class="artist-name"><b>${myArtistList.name}</b></div>
							</div>
							<br>
							<br>
						</div>
					</c:forEach>
				</table>
				<div class="align-center">${page}</div>
			</c:if>
		</div>
	</div>
</div>