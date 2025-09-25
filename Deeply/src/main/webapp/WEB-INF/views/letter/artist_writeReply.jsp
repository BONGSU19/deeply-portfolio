<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/letter.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


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
		<form:form modelAttribute="replyVO" action="artist_write" id="write_reply" enctype="multipart/form-data">
		<form:hidden path="user_num" value="${letter.user_num}"/>
		<form:hidden path="artist_num" value="${artist.user_num}"/>
		<form:hidden path="letter_num" value="${letter.letter_num}"/>
		<div class="letter-box">
			<div class="letter-background">
			<img class="preview-img">
			<div class="letter-photo">
				<button class="letterPhoto-btn" id="letterPhoto_btn">사진 첨부</button>
				<input type="file" class="display-none" name="upload" id="letterPhoto_upload">
				<button class="letterPhoto-btn2" id="letterFiles_btn">파일 등록</button>
				<input type="file" class="display-none" multiple="multiple" name="file_upload" id="letterFiles_upload">
			</div>
			
			<div class="letter-title">
				<form:input path="letter_title" class="ltitle-input" placeholder="편지 제목 입력"/>
			</div>
			<div class="letter-content z-3">
				<form:textarea path="letter_content" class="lcontent-input" vplaceholder="편지 내용 입력"/>
				
			</div>
			<div class="left-3"><form:errors class="left-3" path="letter_content" cssClass="error-color"/></div>
			</div>
		</div>
		<form:button>전송</form:button>
		</form:form>
	</div>
	<div class="space-10vw align-center"></div>
</div>