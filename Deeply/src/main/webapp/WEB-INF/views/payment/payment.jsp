<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--  -->
<script
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="https://cdn.portone.io/v2/browser-sdk.js"></script>

<div class="payment-1">
	<c:if test="${!empty booking_num}">
	<input type="hidden" id="booking_info" data-book-num="${booking_num}">
	</c:if>
	<c:if test="${!empty order_num}">
	<input type="hidden" id="order_info" data-order-num="${order_num}">
	<input type="hidden" id="item_quantity" data-order-num="${item_quantity}">
	</c:if>
	<hr>
	<h2>결제 방식</h2>
	<form>
		<div style="display: none;">
			<label for="usePoints">사용할 포인트: </label> <input type="number"
				step="1000" id="usePoints" value="0" min="0" /> 원
		</div>
		<h4>주의사항</h4>

		<h5>- 무통장 입금시 이체실수에는 책임지지 않습니다.</h5>
		<h5>- 체결 후 결제 정보 변경은 불가능하며 분할 납부 변경은 카드사 문의 바랍니다. 단, 카드사별 정책에 따라
			분할 납부 변경 시 수수료가 발생할 수 있습니다.</h5>
		<h4>주문 정보</h4>
		<div class="payment-2">
			<h2>합계금액</h2>
			<div id="payment-info" data-user-num="${memberVO.user_num}"></div>
			<div style="width: 300px; display: none;">

				<div style="float: left;" id="originalPrice">${payPrice}원</div>
			</div>

			<p style="clear: both; display: none;">
				사용한 포인트: <span id="usedPoints">0</span> 원
			</p>
			<p style="clear: both;">
				최종 결제 금액: <span id="finalPrice">${payPrice}</span> 원
			</p>


		</div>
		<div class="submit-1">결제하기</div>
	</form>
</div>

<script>

var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
const userNum = parseInt(document.querySelector("#payment-info").getAttribute("data-user-num"));
const bookingInfoElement = document.querySelector("#booking_info");
const orderInfoElement = document.querySelector("#order_info");
const itemQuantityElement = document.querySelector("#item_quantity");

const bookingNum = bookingInfoElement ? bookingInfoElement.getAttribute("data-book-num") : null;
const orderNum = orderInfoElement ? orderInfoElement.getAttribute("data-order-num") : null;
const itemQuantity = itemQuantityElement ? itemQuantityElement.getAttribute("data-order-num") : null;
console.log(userNum,orderNum);  // 제대로 값이 출력되는지 확인

	document
			.getElementById('usePoints')
			.addEventListener(
					'input',
					function() {
						// 기본 결제 금액과 포인트 입력값을 가져옵니다.
						const originalPrice = parseInt(document
								.getElementById('originalPrice').textContent
								.replace("원", "").trim());
						let usePoints = parseInt(document
								.getElementById('usePoints').value);

						// 포인트가 기본 결제 금액을 넘지 않도록 제한
						if (usePoints > originalPrice) {
							usePoints = originalPrice; // 포인트를 결제 금액 이상으로 입력하지 못하게 함
							document.getElementById('usePoints').value = usePoints;
							alert("포인트는 결제 금액을 넘을 수 없습니다.");
						}

						// 최종 결제 금액 계산 (기본 금액에서 사용한 포인트 차감)
						const finalPrice = originalPrice - usePoints;

						// 최종 결제 금액과 사용 포인트를 업데이트
						document.getElementById('finalPrice').textContent = finalPrice;
						document.getElementById('usedPoints').textContent = usePoints;
					});

	document.querySelector('.submit-1').addEventListener('click', async function () {
	    const finalPrice = parseInt(document.getElementById('finalPrice').textContent, 10);

	    if (finalPrice === 0) {
	        alert("결제 금액이 0원입니다. 다시 확인해주세요.");
	        return;
	    }

	    try {
	        // PortOne 결제 요청
	        const response = await PortOne.requestPayment({
	            storeId: "store-e9d5634a-49fe-4954-a880-a860d3b70483",
	            channelKey: "channel-key-dc06b7ca-d864-444d-987b-349a97f1be61",
	            paymentId: crypto.randomUUID(),
	            orderName: "Deeply카드결제",
	            totalAmount: finalPrice,
	            currency: "CURRENCY_KRW",
	            payMethod: "CARD",
	            customer: {
	                customerId: "customer-12345",
	                fullName: "John Doe",
	                phoneNumber: "010-1234-5678",
	                email: "test@portone.io",
	            },
	        });

	        // 결제 성공 여부 확인
	   if (response.code !== undefined) {
		   alert("결제가 실패했습니다. 다시 시도해주세요.");
    
	        } else {
	        	const bookingElement = document.querySelector("#booking_info");
	            const orderElement = document.querySelector("#order_info");
	            const itemElement = document.querySelector("#item_quantity");

	            const bookingNum = bookingElement ? bookingElement.getAttribute("data-book-num") : null;
	            const orderNum = orderElement ? orderElement.getAttribute("data-order-num") : null;
	            const itemQuantity = itemElement ? itemElement.getAttribute("data-order-num") : null;

	            const endpoint = bookingNum
	                ? `${pageContext.request.contextPath}/booking/complete`
	                : orderNum
	                ? `${pageContext.request.contextPath}/item/complete`
	                : `${pageContext.request.contextPath}/charge/complete`;



	        	            const body = {
	        	                totalAmount: finalPrice,
	        	                user_num: userNum
	        	                
	        	            };

	        	            if (bookingNum!=null) {
	        	                body.booking_num = parseInt(bookingNum, 10);
	        	            }else if(orderNum!=null){
	        	            	body.order_num = parseInt(orderNum, 10);
	        	            	body.item_quantity = parseInt(itemQuantity, 10);
	        	            }
	        	
	        	            

	        	            const notified = await fetch(endpoint, {
	        	                method: "POST",
	        	                headers: {
	        	                    "Content-Type": "application/json",
	        	                    "X-CSRF-TOKEN": csrfToken,
	        	                },
	        	                body: JSON.stringify(body),
	        	            });

	        	            if (notified.ok) {
	        	                alert("결제가 성공적으로 완료되었습니다.");
	        	                window.location.href = "/main/main";
	        	            } else {
	        	                const errorResponse = await notified.json();
	        	                console.error("결제 처리 실패:", errorResponse);
	        	                alert("결제 처리 중 문제가 발생했습니다.");
	        	            }
	        }
	    } catch (error) {
	        console.error("결제 요청 중 오류 발생:", error);
	        alert("결제 요청 중 문제가 발생했습니다. 다시 시도해주세요.");
	    }
	});
	console.log(finalPrice, userNum); // 실제 값이 어떻게 전달되는지 확인
</script>