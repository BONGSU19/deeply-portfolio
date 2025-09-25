<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/booking.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<div class="booking-artist-container font-white bold-title background-black align-center">${group.group_name}</div>
<div class="bold-title left-10 font-2 top-5">공연 일정 등록</div>
<div class="space-10vw"></div>
<div class="event-register-main">
	<div class="left-10">
	<form:form modelAttribute="eventVO" action="register" id="register_event" enctype="multipart/form-data">
	<form:hidden path="artist_num" value="${group.group_num}"/>
	<div class="register-date">
		<div class="vertical-center">
			<div class="font-1_5 width-15vw bold-title height-3 border-bottom vertical-center">공연 일정</div>
			<button class="dur-btn left-13" id="event_dur1">1일 공연</button>
			<button class="dur-btn left-1" id="event_dur2">기간 공연</button>
		</div>
		<div class="top-5">
			<p class="height-2"><form:label class="width-10vw bold-title" path="perf_date">공연 시작일</form:label><form:input type="date" class="white-btn height-2 width-15vw" path="perf_date"/><form:errors path="perf_date" cssClass="error-color"/></p>
			<p class="event-end-date height-2"><form:label class="width-10vw bold-title" path="perf_date">공연 종료일</form:label><form:input type="date" class="white-btn height-2 width-15vw" path="end_date"/><form:errors path="end_date" cssClass="error-color"/></p>
			<p class="height-2"><form:label class="width-10vw bold-title" path="perf_time">공연 시작시간</form:label><form:input class="white-btn height-2 width-15vw" path="perf_time"/><form:errors path="perf_time" cssClass="error-color"/></p>
			<p class="height-2"><form:label class="width-10vw bold-title" path="end_time">공연 종료시간</form:label><form:input class="white-btn height-2 width-15vw" path="end_time"/><form:errors path="end_time" cssClass="error-color"/></p>
			<div class="height-2 vertical-center">
				<form:label class="width-10vw bold-title" path="hall_num">공연 장소</form:label>
				<div class="dropdown">
				<button class="white-btn dropdown-toggle height-2 width-15vw" id="drop_title" type="button" data-bs-toggle="dropdown" aria-expanded="false">공연 장소</button>
					<ul class="dropdown-menu width-15vw">
						<li><a class="dropdown-item width-15vw" href="#" data-value="1">테스트 홀</a></li>
						<li><a class="dropdown-item width-15vw" href="#" data-value="2">서울 체육관</a></li>
						<li><a class="dropdown-item width-15vw" href="#" data-value="3">수도권 경기장</a></li>
						<li><a class="dropdown-item width-15vw" href="#" data-value="4">월드컵 돔</a></li>
					</ul>
				</div>
			</div>
			<form:hidden path="hall_num" id="hall_num"/> 
			<form:errors path="hall_num" cssClass="error-color"/>
			
		</div>
	</div>
	<div class="register-detail top-5">
		<div class="font-1_5 width-15vw bold-title border-bottom height-3">공연 정보</div>
		<div>
			<div class="vertical-center top-1">
				<form:label class="vertical-center bold-title width-10vw height-2" path="perf_title">공연 이름</form:label>
				<form:input class="white-btn height-2 width-15vw shadow-effect" path="perf_title"/>
				<form:errors path="perf_title" cssClass="error-color"/>
			</div>
			<div class="vertical-center top-1">
				<form:label class="vertical-center bold-title width-10vw" path="perf_desc">공연 상세</form:label>
				<form:textarea class="perf-desc-textarea white-btn width-30vw shadow-effect" path="perf_desc"/>
				<form:errors path="perf_desc" cssClass="error-color"/>
			</div>
			<div>
				<form:label class="vertical-center bold-title width-10vw top-3" path="upload">공연 이미지</form:label>
				<div class="border-black perf-register-previewImg"><img id="preview_perfImg"></div>
				<button id="perf_img_btn" class="perf-img-btn top-1">이미지 등록</button>
				<input id="event_upload" type="file" name="upload"><form:errors path="upload" cssClass="error-color"/>
			</div>
			
			
		</div>
	</div>
	<div class="register-booking top-5">
		<div class="font-1_5 width-15vw bold-title height-3 border-bottom vertical-center">예매 정보</div>
		<div>
			<p><form:label class="width-10vw bold-title top-5" path="book_date">예매 시작일</form:label><form:input type="date" class="white-btn height-2 width-15vw" path="book_date"/><form:errors path="book_date" cssClass="error-color"/></p>
			<p><form:label class="width-10vw bold-title" path="booking_deadline">예매 종료일</form:label><form:input type="date" class="white-btn height-2 width-15vw" path="booking_deadline"/><form:errors path="booking_deadline" cssClass="error-color"/></p>
			<p><form:label class="width-10vw bold-title" path="mem_date">선예매 시작일</form:label><form:input type="date" class="white-btn height-2 width-15vw" path="mem_date"/><form:errors path="mem_date" cssClass="error-color"/></p>
			<p><form:label class="width-10vw bold-title" path="ticket_price">티켓 금액</form:label><form:input class="white-btn height-2 width-15vw" path="ticket_price"/><form:errors path="ticket_price" cssClass="error-color"/></p>
			<p class="font-red">VIP좌석 금액은 기본 좌석 금액 기준 1.4배로 측정됩니다</p>
		</div>
	</div>
	<div class="align-center space-10vw">
		<form:button>등록</form:button>
	</div>
	</form:form>
	</div>
</div>

