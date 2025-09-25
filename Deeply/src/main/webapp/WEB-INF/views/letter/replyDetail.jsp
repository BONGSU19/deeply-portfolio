<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/letter.js"></script>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>
<div class="letter-main background-black">
	<div class="letter-div1">
		<img src="${pageContext.request.contextPath}/assets/images/mail.png">
	</div>
	<div class="font-white bold-title align-center top-10 font-2">
		<c:if test="${!empty principal.memberVO}">
			나의 아티스트와의 소통
		</c:if>
		<c:if test="${!empty principal.artistVO}">
			환영합니다! ${artist.name}님
		</c:if>
	</div>
	<div class="letter-aname align-center top-10">
		<hr class="width-60 background-darkblue">
		<c:if test="${artist.name == artist.group_name}">
			<div class="aname-div bold-title font-2 align-center">${artist.name}</div>
		</c:if>
		<c:if test="${artist.name != artist.group_name}">
			<div class="aname-div bold-title font-2 align-center">${artist.name} - ${artist.group_name}</div>
		</c:if>
		<hr class="width-60 background-darkblue">
	</div>
	<div>
		<div class="font-white bold-title align-center width-20 font-2 top-10">MY LETTER</div>
		<div class="width-80 background-darkblue height-0_2 top-2"></div>
	</div>
	<div class="font-white bold-title align-center width-20 font-1 top-3">잔여 발송 가능 편지 2/3</div>
	<div class="space-10vw"></div>
	<div id="letter_form" class="letter-btn-div vertical-center">
		<c:if test="${!empty principal.memberVO}">
		<button class="letter-btn font-white bold-title align-center font-1" onclick="location.href='${pageContext.request.contextPath}/letter/list?artist_num=${artist.user_num}'">보낸편지</button>
		<button class="letter-btn font-white bold-title align-center font-1"  onclick="location.href='${pageContext.request.contextPath}/letter/reply?artist_num=${artist.user_num}'">아티스트의 답장</button>
		<button class="write-btn bold-title align-center left-50" onclick="location.href='${pageContext.request.contextPath}/letter/list?artist_num=${artist.user_num}'">편지 목록</button>
		</c:if>
		<c:if test="${!empty principal.artistVO}">
		<button class="letter-btn font-white bold-title align-center font-0_8" onclick="location.href='${pageContext.request.contextPath}/letter/artist_list?artist_num=${artist.user_num}'">받은 편지</button>
		<button class="letter-btn font-white bold-title align-center font-0_8"  onclick="location.href='${pageContext.request.contextPath}/letter/artist_reply?artist_num=${artist.user_num}'">보낸 답장</button>
		<button class="write-btn bold-title align-center left-50" onclick="location.href='${pageContext.request.contextPath}/letter/artist_list?artist_num=${artist.user_num}'">편지 목록</button>
		</c:if>
	</div>
	<div class="letter-body">
	<div class="letter-box">
		<div class="font-white height-3 bold-white font-1 vertical-center left-3 z-3">To. ${reply.nick_name}</div>
		<div class="letter-background">
			<c:if test="${!empty reply.img}">
			<img class="preview-img" src="${pageContext.request.contextPath}/assets/upload/${reply.img}">
			</c:if>
			<c:if test="${empty reply.img}">
			<img class="preview-img" src="${pageContext.request.contextPath}/assets/images/default_img.png">
			</c:if>
			<div class="letter-title height-5 vertical-center left-3 font-1_5">
				${reply.letter_title}
			</div>
			<hr class="width-90">
			<div class="letter-content letter-detail left-3 top-1">
				${reply.letter_content}
			</div>
		</div>
		<div class="align-right top-1">
		<c:if test="${!empty principal.artistVO}">
			<button class="letter-buttons">삭제</button>
		</c:if>
		</div>
	</div>
	<h2 class="font-white bold-title">${artist.name}의 선물</h2>
	<div class="upload-files-div vertical-center">
		<c:forEach var="name" items="${file_name}">
			<div class="upload-item-container">
				<img class="upload-files-img" src="${pageContext.request.contextPath}/assets/upload/${name}">
				<button class="download-btn" onclick="location.href='${pageContext.request.contextPath}/download/file?file_name=${name}'"><img src="${pageContext.request.contextPath}/assets/images/download.svg"></button>
			</div>
		</c:forEach>
	</div>
		<div class="page-box space-10vw align-center">${page}</div>
	
	</div>
	<div class="space-30vw align-center"></div>

</div>