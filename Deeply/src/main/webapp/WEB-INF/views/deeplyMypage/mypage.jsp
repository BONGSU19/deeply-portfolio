<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!-- 회원정보 시작 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- jQuery CDN을 추가 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/member.profile.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/customjs.js"></script>
<c:if test="${not empty successMessage}">
	<script type="text/javascript">
		alert('${successMessage}');
	</script>
</c:if>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/hr2.css">
<script type="text/javascript">
	var csrfToken = $('meta[name="_csrf"]').attr('content');
	var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
</script>
<c:if test="${isMemberVO}">
	<div class="page-main" style="text-align: left; width: 80%">
		<h2>회원상세정보</h2>
		<hr size="3px">
		<div class="mypage-1" style="margin: 0 auto; text-align: center;">
			<div class="profile-image" style="align-items: center; text-align: center;">
				<img src="${pageContext.request.contextPath}/member/photoView"
					width="200" height="200" class="my-profile-img" id="photo_btn">
				<div id="photo_choice" style="display: none;">
					<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
					<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
					<input type="file" id="upload"
						accept="image/gif,image/png,image/jpeg"> <br> <input
						type="button" value="전송" id="photo_submit"> <input
						type="button" value="취소" id="photo_reset">
				</div>
			</div>
			<c:if test="${member.auth==0}">
				<p
					style="text-align: center; font-size: 18px; color: #00A2CF; margin: 15px 0 40px 0;">
					<b>일반회원</b>
				</p>
			</c:if>
			<c:if test="${member.auth==1}">
				<p
					style="text-align: center; font-size: 18px; color: #00A2CF; margin: 15px 0 40px 0;">
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
		<div class="mypage-link" style="margin-bottom: 10px auto;">
			<input type="button" value="나의 멤버십" onclick="location.href='${pageContext.request.contextPath}/fan/myArtistList'"> 
			<input type="button" value="나의 예매" onclick="location.href='${pageContext.request.contextPath}'"> 
			<input type="button" value="나의 SHOP" onclick="location.href='${pageContext.request.contextPath}/item/orderList'"> 
			<input type="button" value="나의 아티스트" onclick="location.href='${pageContext.request.contextPath}/follow/followList'"> 
			<input type="button" value="작성글 목록" onclick="location.href='${pageContext.request.contextPath}/commu/myList'"> 
			<input type="button" value="나의 편지" onclick="location.href='${pageContext.request.contextPath}/letter/artist_list?artist_num=${artist.artist_num}'"> 
			<!-- 미완성 <input type="button" value="좋아요 영상" onclick="location.href='${pageContext.request.contextPath}'"> --> 
			<input type="button" value="고객센터" onclick="location.href='${pageContext.request.contextPath}/faq'">
		</div>
	</div>
</c:if>

<c:if test="${!isMemberVO}">
	<div class="page-main" style="text-align: left; width: 80%">
		<h2>회원상세정보</h2>
		<hr size="3px">
		<div class="mypage-1" style="margin: 0 auto; text-align: center;">
			<div class="profile-image" style="align-items: center; text-align: center;">
				<img src="${pageContext.request.contextPath}/member/photoView"
					width="200" height="200" class="my-profile-img" id="photo_btn">
				<div id="photo_choice" style="display: none;">
					<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
					<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
					<input type="file" id="upload"
						accept="image/gif,image/png,image/jpeg"> <br> <input
						type="button" value="전송" id="photo_submit"> <input
						type="button" value="취소" id="photo_reset">
				</div>
			</div>
			<p style="text-align: center; font-size: 18px; color: #00A2CF; margin: 15px 0 40px 0;">
					<b>아티스트</b></p>
		</div>
		<div>
			<table class="myTable">
				<tr>
					<td><b>아이디 </b>${member.id}</td>
					<td><b>닉네임 </b>${member.group_name} ${member.name}</td>
				</tr>
				<tr>
					<td><b>이메일 </b>${member.email}</td>
				</tr>
				<tr>
					<td colspan="2"><b>소개글 </b>${member.intro}
							<a href="${pageContext.request.contextPath}/member/myPage1"><img
							src="${pageContext.request.contextPath}/assets/images/hr2/follow.png"
							width="20px" height="20px"></a></td>
				</tr>
			</table>
		</div>
		<div class="mypage-link" style="margin-bottom: 10px auto;">
			<input type="button" value="공연 관리"
				onclick="location.href='${pageContext.request.contextPath}'"> <input
				type="button" value="굿즈 관리"
				onclick="location.href='${pageContext.request.contextPath}'">  <input
				type="button" value="편지 관리"
				onclick="location.href='${pageContext.request.contextPath}'"> <input
				type="button" value="채팅 관리"
				onclick="location.href='${pageContext.request.contextPath}'"> <input
				type="button" value="영상 관리"
				onclick="location.href='${pageContext.request.contextPath}/artist/videos/upload_form'"> <input
				type="button" value="공지 등록"
				onclick="location.href='${pageContext.request.contextPath}/notice/write'">
		</div>
	</div>
</c:if>
