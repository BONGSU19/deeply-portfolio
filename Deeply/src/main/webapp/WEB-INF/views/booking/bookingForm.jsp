<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/booking.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/seat.js"></script>

<div class="booking-artist-container font-white bold-title background-black align-center">${group_name}</div>
<div class="booking-title bold-title">예매 정보 입력</div>
<div class="booking-info" style="background-image: url('${pageContext.request.contextPath}/assets/upload/${event.perf_photo}');">
	<div class="booking-div1 left-5">
		<!-- 아티스트 이름 -->
		<div class="booking-artist width-40">
			<div class="bold-title font-white font-1_5">아티스트</div>
			<hr>
			<div class="bold-title font-white font-1_5 top-5">${group_name}</div>
		</div>
		<!-- 공연 장소 -->
		<div class="booking-place width-60">
			<div class="bold-title font-white font-1_5">공연 장소</div>
			<hr>
			<div class="bold-title font-white font-1 top-5">${event.hall_name}</div>
			<div class="font-white font-0_8 top-3">${event.location}</div>
		</div>
	</div>
	<!-- 공연 정보 -->
	<div class="booking-div2 left-5">
		<div class="bold-title font-white font-1_5">${event.perf_title}</div>
		<hr>
		<div class="font-white font-0_8 top-3">${event.perf_desc}</div>
	</div>
</div>
<div class="booking-main">
	<form:form modelAttribute="bookingVO" action="book"
		id="booking_process" enctype="multipart/form-data">
		<form:hidden path="perf_num" value="${event.perf_num}"/>
		<form:hidden path="user_num" value="${member.user_num}"/>
		<div class="book-form-style">
			<p>
				<form:label path="booked_seat">예약 인원</form:label>
				<form:input class="small-input" id="book_member" path="booked_seat" type="number" min="1" max="2"/>
				<form:errors path="booked_seat" cssClass="error-color"/>
			</p>
			<hr>
			<!-- 예매자 정보 -->
			<div class="width-50">
				<div class="p_info" id="book1">
					<p class="bold-title font-1 left-1 height-3 vertical-center">예매자 1</p>
					<p>
						<form:label class="left-1" path="name">이름</form:label>
						<form:input class="medium-input" path="name"/>
						<form:errors path="name" cssClass="error-color"/>
					</p>
					<p>
						<form:label class="left-1" path="phone">전화번호</form:label>
						<form:input class="medium-input" path="phone" />
						<form:errors path="phone" cssClass="error-color"/>
					</p>
				<hr>
				</div>
				<div class="p_info" id="book2">
					<p class="bold-title font-1 left-1 height-3 vertical-center">예매자 2</p>
					<p>
						<form:label class="left-1" path="name2">이름</form:label>
						<form:input class="medium-input" path="name2" />
						<form:errors path="name2" cssClass="error-color"/>
					</p>
					<p>
						<form:label class="left-1" path="phone2">전화번호</form:label>
						<form:input class="medium-input" path="phone2" />
						<form:errors path="phone2" cssClass="error-color"/>
					</p>
				<hr>
				</div>
			</div>
			<!-- 배송지 정보 -->
			<div>
				<h2 class="top-5">배송지 정보</h2>
				<p>
					<form:label class="top-3" path="deliver_name">이름</form:label>
					<form:input class="medium-input" path="deliver_name"/>
					<form:errors path="deliver_name" cssClass="error-color"/>
					
				</p>
				<p>
					<form:label path="zipcode">우편번호</form:label>
					<form:input path="zipcode" id="zipcode" class="Authentication2 medium-input"
						type="text"/>
					<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"
						class="default-btn2">
					<form:errors path="zipcode" cssClass="error-color" />
				</p>
				<p>
					<form:label path="address1">주소</form:label>
					<form:input path="address1" class="medium-input" id="address1" type="text"/>
					<form:errors path="address1" cssClass="error-color" />
				</p>
				<p>
					<form:label path="address2">상세주소</form:label>
					<form:input path="address2" class="medium-input" type="text"/>
					<form:errors path="address2" cssClass="error-color" />
				</p>
				<p>
					<form:label class="top-3" path="request">요청사항</form:label>
					<form:input class="big-input" path="request"/>
				</p>
			</div>
			<hr class="top-5">
			<!-- 좌석 정보 -->
			<div class="seat-div">
				<h2 class="top-3">좌석 정보</h2>
				<h5>${event.hall_name}</h5>
				<div class="seat-box">
					<%-- <div class="stage-div">무대</div>
					<!-- 좌석 데이터 반복 -->
					<c:forEach var="seat" items="${seats}">
						<div class="seat-item cursor-pointer shadow-effect" data-price="${seat.price}"> ${seat.seat_num}</div>
					</c:forEach> --%>
					<!-- 섹터 1 -->
					<div class="sector1">
						<c:forEach var="seat" items="${seats}">
							<c:if test="${seat.srow == 'A'}">
								<c:if test="${seat.status == 'AVAILABLE'}">
									<div class="seat-item align-center available-color" data-row="${seat.srow}" data-price="${seat.price}" data-number="${seat.seat_num}">${seat.seat_num}</div>
								</c:if>
								<c:if test="${seat.status == 'BOOKED'}">
									<div class="seat-item align-center booked-color" data-row="${seat.srow}" data-price="${seat.price}" data-number="${seat.seat_num}">${seat.seat_num}</div>
								</c:if>
								
							</c:if>
						</c:forEach>
					</div>

					<!-- 무대와 섹터 2/3 -->
					<div class="align-center top-3">
						<div class="sector2">
							<c:forEach var="seat" items="${seats}">
								<c:if test="${seat.srow == 'B'}">
									<c:if test="${seat.status == 'AVAILABLE'}">
										<div class="seat-item align-center available-color" data-row="${seat.srow}" data-price="${seat.price}" data-number="${seat.seat_num}">${seat.seat_num}</div>
									</c:if>
									<c:if test="${seat.status == 'BOOKED'}">
										<div class="seat-item align-center booked-color" data-row="${seat.srow}" data-price="${seat.price}" data-number="${seat.seat_num}">${seat.seat_num}</div>
									</c:if>
								</c:if>
							</c:forEach>
						</div>

						<div class="stage-div">무대</div>

						<div class="sector3">
							<c:forEach var="seat" items="${seats}">
								<c:if test="${seat.srow == 'C'}">
									<c:if test="${seat.status == 'AVAILABLE'}">
										<div class="seat-item align-center vip-color" data-row="${seat.srow}" data-price="${seat.price}" data-number="${seat.seat_num}">${seat.seat_num}</div>
									</c:if>
									<c:if test="${seat.status == 'BOOKED'}">
										<div class="seat-item align-center booked-color" data-row="${seat.srow}" data-price="${seat.price}" data-number="${seat.seat_num}">${seat.seat_num}</div>
									</c:if>
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="space-10vw"></div>

				</div>
				<div class="seat-info-container">
					<div class="align-center">
						<div class="seat-type shadow-effect" id="stype1"></div>
						<div class="seat-desc">선택한 좌석</div>
					</div>
					<div class="align-center">
						<div class="seat-type shadow-effect" id="stype2"></div>
						<div class="seat-desc">빈 좌석</div>
					</div>
					<div class="align-center">
						<div class="seat-type shadow-effect" id="stype3"></div>
						<div class="seat-desc">예매된 좌석</div>
					</div>
					<div class="align-center">
						<div class="seat-type shadow-effect" id="stype4"></div>
						<div class="seat-desc">VIP 좌석</div>
					</div>

					<!-- 숨겨진 필드와 에러 메시지 -->
					<form:hidden id="seat_n1" path="seat_num1" />
					<form:hidden id="seat_n2" path="seat_num2" />
					<form:errors path="seat_num1" cssClass="error-color" />
					<form:errors path="seat_num2" cssClass="error-color" />
				</div>
			</div>

			<!-- 선택 좌석 -->
			<div>
				<h2>선택 좌석</h2>
				<table class="receipt" width="100%" border="1">
					<thead>
					<tr>
						<td>좌석 번호</td>
						<td>좌석 금액</td>
						<td>총액</td>
					</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
				<form:hidden id="seat_price" path="total_price"/>
			</div>
		</div>
		
		<div class="align-center">
			<form:button class="book-submit-btn shadow-effect top-5">결제</form:button>
		</div>
	</form:form>
	<div class="space-10vw"></div>
</div>

<!-- 다음 우편번호 API 시작 -->
<div id="layer"
	style="display: none; position: fixed; overflow: hidden; z-index: 1; -webkit-overflow-scrolling: touch;">
	<img
		src="//t1.daumcdn.net/localimg/localimages/07/postcode/320/close.png"
		id="btnCloseLayer"
		style="cursor: pointer; position: absolute; right: -3px; top: -3px; z-index: 1"
		onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	// 우편번호 찾기 화면을 넣을 element
	var element_layer = document.getElementById('layer');

	function closeDaumPostcode() {
		// iframe을 넣은 element를 안보이게 한다.
		element_layer.style.display = 'none';
	}

	function execDaumPostcode() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						// 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var fullAddr = data.address; // 최종 주소 변수
						var extraAddr = ''; // 조합형 주소 변수

						// 기본 주소가 도로명 타입일때 조합한다.
						if (data.addressType === 'R') {
							//법정동명이 있을 경우 추가한다.
							if (data.bname !== '') {
								extraAddr += data.bname;
							}
							// 건물명이 있을 경우 추가한다.
							if (data.buildingName !== '') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr
									+ ')' : '');
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
						document.getElementById('address1').value = fullAddr;
						//document.getElementById('sample2_addressEnglish').value = data.addressEnglish;

						// iframe을 넣은 element를 안보이게 한다.
						// (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
						element_layer.style.display = 'none';
					},
					width : '100%',
					height : '100%',
					maxSuggestItems : 5
				}).embed(element_layer);

		// iframe을 넣은 element를 보이게 한다.
		element_layer.style.display = 'block';

		// iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
		initLayerPosition();
	}

	// 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
	// resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
	// 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
	function initLayerPosition() {
		var width = 300; //우편번호서비스가 들어갈 element의 width
		var height = 400; //우편번호서비스가 들어갈 element의 height
		var borderWidth = 5; //샘플에서 사용하는 border의 두께

		// 위에서 선언한 값들을 실제 element에 넣는다.
		element_layer.style.width = width + 'px';
		element_layer.style.height = height + 'px';
		element_layer.style.border = borderWidth + 'px solid';
		// 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
		element_layer.style.left = (((window.innerWidth || documentcumentElement.clientWidth) - width) / 2 - borderWidth)
				+ 'px';
		element_layer.style.top = (((window.innerHeight || documentcumentElement.clientHeight) - height) / 2 - borderWidth)
				+ 'px';
	}
</script>
<!-- 다음 우편번호 API 끝 -->