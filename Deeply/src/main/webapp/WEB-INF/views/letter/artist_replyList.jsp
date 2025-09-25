<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/letter.js"></script>
<div class="letter-main background-black">
	<div class="letter-div1">
		<img src="${pageContext.request.contextPath}/assets/images/mail.png">
	</div>
	<div class="font-white bold-title align-center top-10 font-2">환영합니다! ${artist.name}님</div>
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
	<div class="space-10vw"></div>
	<div id="letter_form" class="letter-btn-div vertical-center">
		<button class="letter-btn font-white bold-title align-center font-1" onclick="location.href='${pageContext.request.contextPath}/letter/artist_list?artist_num=${artist.user_num}'">받은 편지</button>
		<button class="letter-btn font-white bold-title align-center font-1"  onclick="location.href='${pageContext.request.contextPath}/letter/artist_reply?artist_num=${artist.user_num}'">보낸 답장</button>
	</div>
	<div class="letter-body">
		<div class="letter-box">
			<div id="letter_item"
				class="letter-item font-white bold-title font-1 left-5 vertical-center">
				<div class="width-10">아티스트</div>
				<div class="width-80">제목</div>
				<div class="width-10 align-center">발송일</div>
				<div class="width-10 align-center">수신자</div>
			</div>
			<hr>
			<c:if test="${count <= 0}">
				<div class="no-reply font-white bold-title align-center">작성한 답장이 없습니다</div>
			</c:if>
			<c:if test="${count > 0}">
				<c:forEach var="reply" items="${replies}">
					<div id="letter_item" class="letter-item font-white font-1 left-5 vertical-center">
						<div class="width-10">${artist.name}</div>
						<div class="width-80"><a href="${pageContext.request.contextPath}/letter/reply_detail?reply_num=${reply.reply_num}">${reply.letter_title}</a></div>
						<div class="width-10 align-center">${reply.post_date}</div>
						<div class="width-10 align-center">${reply.nick_name}</div>
					</div>
					<hr>
				</c:forEach>
			</c:if>

		</div>
		<div class="page-box space-10vw align-center">${page}</div>
	</div>
	<div class="space-10vw align-center"></div>
</div>