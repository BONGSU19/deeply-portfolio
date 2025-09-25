<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="page-main">
    <div class="join-fan">
        <h2>나의 예치금</h2>
        ${fan.user_bal}
        <hr>
        <form id="charge_money" action="chargeMoney" method="post">
            <input type="hidden" id="pay_price" name="pay_price">
            <button type="button" onclick="submitForm(5000)">5000원</button>
            <button type="button" onclick="submitForm(10000)">10000원</button>
            <button type="button" onclick="submitForm(15000)">15000원</button>
            <button type="button" onclick="submitForm(20000)">20000원</button>
            <button type="button" onclick="submitForm(30000)">30000원</button>
            <button type="button" onclick="submitForm(50000)">50000원</button>
            <button type="button" onclick="submitForm(100000)">100000원</button>
            <button type="button" onclick="submitForm(150000)">150000원</button>
        </form>
    </div>
</div>

<script>
    function submitForm(amount) {
        document.getElementById('pay_price').value = amount;
        document.getElementById('charge_money').submit();
    }
</script>