<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/letter.js"></script>
<div class="letter-main background-black">
	<div class="letter-div1">
		<img src="${pageContext.request.contextPath}/assets/images/mail.png">
	</div>
	<div class="font-white bold-title align-center top-10 font-2">나의 아티스트와의 소통</div>
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
	<div class="font-white bold-title align-center width-20 font-1 top-3">잔여 발송 가능 편지 ${letter_limit}/3</div>
	<div class="space-10vw"></div>
	<div class="letter-btn-div vertical-center">
		<button class="letter-btn font-white bold-title align-center font-0_8" onclick="location.href='${pageContext.request.contextPath}/letter/list?artist_num=${artist.user_num}'">보낸편지</button>
		<button class="letter-btn font-white bold-title align-center font-0_8"  onclick="location.href='${pageContext.request.contextPath}/letter/reply?artist_num=${artist.user_num}'">아티스트의 답장</button>
		<c:if test="${letter_limit > 0}">
		<button class="write-btn bold-title align-center left-50" onclick="location.href='${pageContext.request.contextPath}/letter/write?artist_num=${artist.user_num}'">편지 작성</button>
		</c:if>
		<c:if test="${letter_limit == 0}">
		<button id="reject_letter" class="write-btn bold-title align-center left-50">편지 작성</button>
		</c:if>
	</div>
	<div class="letter-body">
		<div class="letter-box">
			<div id="letter_item" class="letter-item font-white bold-title font-1 left-5 vertical-center">
					<div class="width-80">제목</div>
					<div class="width-10 align-center">발송일</div>
					<div class="width-10 align-center">답장 여부</div>
			</div>
			<hr>
		<c:forEach var="letter" items="${letters}">
				<a href="${pageContext.request.contextPath}/letter/detail?letter_num=${letter.letter_num}">
				<div id="letter_item" class="letter-item font-white font-1 left-5 vertical-center">
					<div class="width-80">${letter.letter_title}</div>
					<div class="width-10 align-center">${letter.post_date}</div>
					<c:if test="${letter.replied == 0}">
					<div class="width-10 align-center">X</div>
					</c:if>
					<c:if test="${letter.replied == 1}">
					<div class="width-10 align-center">O</div>
					</c:if>
				</div>
				</a>
				<hr>
		</c:forEach>
		</div>
		<div class="page-box space-10vw align-center">${page}</div>
	</div>
	<div class="space-10vw align-center"></div>
</div>