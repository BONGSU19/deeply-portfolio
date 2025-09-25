<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/booking.js"></script>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>
<!-- Flatpickr -->
<link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<div>
	<div class="booking-artist-container font-white bold-title background-black align-center">
		${group_name}
	</div>
	<div class="booking-main">
		<div class="booking-title bold-title vertical-center">공연 예매</div>
		<div class="booking-list-container">
			<form id="booking_filter" action="list" method="get">
			<input type="hidden" id="group_num" name="group_num" value="${artist_num}">
			<div class="booking-date-container vertical-center">
				<img class="left-1" id="calendar_img" src="${pageContext.request.contextPath}/assets/image_bundle/calendar.svg">
				<div class="booking-date left-1">
				<input type="text" id="date-range" name="dateRange" value="${dateRange}" class="dateSelect-btn align-center" placeholder="${dateRange}">
				</div>
				<div class="booking-category left-3">
					<button class="booking-btn white-btn left-1" data-value="before">예매 전</button>
					<button class="booking-btn white-btn left-1" data-value="ongoing">예매 중</button>
					<button class="booking-btn white-btn left-1" data-value="over">마감/종료</button>
					<button class="booking-btn white-btn left-1" data-value="membership">선예매 중</button>
					<input type="hidden" id="event_status" name="status">
				</div>
				<c:if test="${!empty principal.artistVO && principal.artistVO.group_name == group_name}">
				<button class="register-event-btn align-center bold-title" onclick="location.href='manage?group_num=${artist_num}'">공연 관리</button>
				<button class="register-event-btn align-center bold-title" onclick="location.href='register?group_num=${artist_num}'">공연 등록</button>
				</c:if>
			</div>
			</form>
			<div class="booking-list top-1">
				<div class="booking-items height-90">
					<c:if test="${count > 0}">
						<c:forEach var="list" items="${list}">
						<div class="booking-item" data-num="${list.perf_num}" data-val="${list.isMembership}">
							<img src="${pageContext.request.contextPath}/assets/upload/${list.perf_photo}">
							<div class="perf-status font-white bold-title right-align">
								${list.status_name}
							</div>
							<div class="perf-title font-white bold-title align-center">${list.perf_title}</div>
							<div class="perf-date font-white bold-title align-center">${list.perf_date}</div>
						</div>
					</c:forEach>
					</c:if>
					<c:if test="${count == 0}">
						<div class="align-center font-white bold-title width-100">
							등록된 이벤트가 없습니다
						</div>
					</c:if>
				</div>
				<div class="page-div align-center height-10 font-white">
					${page}
				</div>
			</div>
		</div>
		<hr class="hr-2 background-darkblue">
		<div class="event-detail top-10">
			<div class="event-title bold-title">공연 정보</div>
			<div class="event-desc top-5">
				<div class="desc-div1 height-50">
					<img class="e-img">
					<div class="e-title vertical-center left-3 bold-title"></div>
					<div class="e-info1 vertical-center left-3">
						<div class="e-date bold-title">xxxx.xx.xx</div>
						<div class="e-time left-5 bold-title">xx:xx ~ xx:xx</div>
					</div>
					<div class="e-info2 top-5">
						<ul>
							<li><label>예매 기간</label><span class="e-period">xx.xx.xx ~ xx.xx.xx</span></li>
							<li class="top-1"><label>선예매 날짜</label><span class="e-membership">xx.xx.xx</span></li>
							<li class="top-1"><label>공연 장소</label><span class="e-place">공연 장소 이름</span></li>
							<li><label></label><span class="e-address">공연 장소 상세 주소</span></li>
						</ul>
					</div>
				</div>
				<div class="desc-div2 height-50">
					<div class="e-info3 vertical-center top-3">
						<div class="width-50 left-3">상세<hr></div>
						<div class="width-50 left-10">금액<hr></div>
					</div>
					<div class="e-info4 vertical-center">
						<div class="e-desc width-50 left-3">상세 내용</div>
						<div class="width-50 left-3">
							<ul>
								<li><label>일반석</label><span class="e-price">???????원</span></li>
								<li><label class="top-1">VIP석</label><span class="e-vip-price">???????원</span></li>
							</ul>
						</div>
					</div>
					<c:if test="${!empty principal.memberVO}">
					<div class="e-submit right-align vertical-center top-5">
						<button id="booking_submit_btn" class="white-btn">예매</button>
					</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>