<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
	<style>
		/* General styling for the admin-two container */
.admin-two {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    font-family: Arial, sans-serif;
}

/* Styling for form labels */
.admin-two label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #333;
}

/* Styling for the error message */
.error-color {
    color: #ff4d4d;
    font-size: 0.9em;
    margin-bottom: 10px;
}

/* Styling for the radio buttons and labels */
.admin-two ul li {
    margin-bottom: 15px;
    list-style: none;
    font-size: 1em;
    color: #555;
}

.admin-two ul li label {
    margin-right: 10px;
}

/* Styling for the button container */
.align-center {
    text-align: center;
    margin-top: 20px;
}

.align-center form:button {
    background-color: #007bff;
    border: none;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1em;
    transition: background-color 0.3s;
}
.button2{
background-color: #007bff;
    border: none;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1em;
    transition: background-color 0.3s;}
.align-center form:button:hover {
    background-color: #0056b3;
}

.align-center input[type="button"] {
    background-color: #6c757d;
    border: none;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1em;
    transition: background-color 0.3s;
    margin-left: 10px;
}

.align-center input[type="button"]:hover {
    background-color: #5a6268;
}

/* Styling for user information */
.admin-two ul li span {
    display: inline-block;
    width: 100px;
    font-weight: bold;
    color: #333;
}

.admin-two ul li {
    padding: 5px 0;
    border-bottom: 1px solid #eaeaea;
}

/* Add responsive design */
@media (max-width: 768px) {
    .admin-two {
        padding: 15px;
    }

    .admin-two ul li span {
        display: block;
        width: auto;
        margin-bottom: 5px;
    }

    .align-center form:button,
    .align-center input[type="button"] {
        width: 100%;
        margin: 10px 0 0;
    }
}
		
	</style>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<hr>
<div class="main-div" style="height: 1000px;  display: flex;">

	<div class="admin-one" >
		<ul>
			<li><a href="/admin/admin_list">사용자 조회</a></li>
			<li><a href="admin_artist">아티스트 정보 조회</a></li>
			<li><a href="#">이벤트 관리</a></li>
			<li><a href="#">사이트 관리</a></li>
			<li><a href="#">1대1문의 관리</a></li>
		</ul>
	</div>
	<div class="admin-two" style="margin-left:200px;">

		<form:form modelAttribute="memberVO" action="admin_update"
			id="modify_form">
			<form:hidden path="user_num" />
			<form:errors element="div" cssClass="error-color" />
			<ul>
				<li><label>회원권한</label> <c:if test="${memberVO.auth < 9}">
						<form:radiobutton path="auth" value="2" />정지
				<form:radiobutton path="auth" value="0" />일반
				</c:if> <c:if test="${memberVO.auth == 9}">관리</c:if></li>
			</ul>
			<div class="align-center">
				<c:if test="${memberVO.auth != 9}">
					<form:button class="button2">전송</form:button>
				</c:if>
				<input type="button" value="회원목록"
					onclick="location.href='/admin/admin_list'">
			</div>
			<ul>
				<li><span>이름</span>${memberVO.name}</li>
				<li><span>전화번호</span>${memberVO.phone}</li>
				<li><span>이메일</span>${memberVO.email}</li>
				<li><span>우편번호</span>${memberVO.zipcode}</li>
				<li><span>주소</span> ${memberVO.address1} ${memberVO.address2}</li>
			</ul>
		</form:form>

	</div>

</div>