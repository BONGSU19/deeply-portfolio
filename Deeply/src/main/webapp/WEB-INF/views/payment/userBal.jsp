<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
	<script
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
	
<div class="userBal">
<h1>나의 예치금</h1>
<div class="userBalForm">
<div class="myUserBal">
	<span style="font-size:20px;">현재금액</span> : <span style="font-size:25px;">${memberVO.user_bal}</span>
</div>
<form id="charge_money" action="/charge/payment" method="get">
	<input type="hidden" id="pay_price" name="pay_price">
	<button type="button" onclick="submitForm(5000)">5000원 ></button>
	<button type="button" onclick="submitForm(10000)">10000원 ></button>
	<button type="button" onclick="submitForm(15000)">15000원 ></button>
	<button type="button" onclick="submitForm(20000)">20000원 ></button>
	<button type="button" onclick="submitForm(30000)">30000원 ></button>
	<button type="button" onclick="submitForm(50000)">50000원 ></button>
	<button type="button" onclick="submitForm(100000)">100000원 ></button>
	<button type="button" onclick="submitForm(150000)">150000원 ></button>
</form>
</div>
</div>
<script>
    function submitForm(amount) {
        // pay_price 숨겨진 필드에 값 설정
        document.getElementById('pay_price').value = amount;

        // 폼 제출
        document.getElementById('charge_money').submit();
    }
</script>
