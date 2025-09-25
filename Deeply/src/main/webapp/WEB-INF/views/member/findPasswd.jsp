<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 메인 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
function submitEmail() {
    var email = document.getElementById("email").value;  // 이메일 입력값 가져오기

    
    // 인증 요청 버튼을 숨기고 클릭 방지
 

 
    // 이메일 유효성 검사 (필요한 경우 추가)
    if (!email || !validateEmail(email)) {
        alert("유효한 이메일을 입력하세요.");
        return;
    }

    // AJAX 요청 보내기
    $.ajax({
        url: '/member/emailSubmit2',  // 서버의 이메일 전송 처리 URL
        type: 'POST',
        headers: {
            [csrfHeader]: csrfToken  // CSRF 토큰을 헤더에 포함
        },
        contentType: 'application/json',
        data: JSON.stringify({ "email": email }),  // JSON 형식으로 이메일 전송
        success: function(response) {
            // 성공적으로 이메일 인증 코드가 발송되면 페이지에 결과를 표시
            if(response.result=="fail"){
            	alert("중복된 이메일입니다 다른 이메일을 입력해주세요");
            	return;
            }
         alert("이메일이 전송되었습니다");
         var requestButton = document.getElementById('requestVerificationButton');
         document.getElementById('verificationSection').style.display = 'block';
            // 이메일 인증 페이지로 이동할 필요가 없으면 이 부분에서 결과를 페이지에 보여줄 수 있습니다.
        },
        error: function(xhr, status, error) {
            alert("이메일 전송 실패 .");
        }
    });
}

// 이메일 유효성 검사 함수 (필요한 경우)
function validateEmail(email) {
    var re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return re.test(email);
}

//인증 코드 검증
function verifyEmail() {
    var email = $('#email').val();  // 이메일 값 가져오기
    var code = $('#code').val();    // 인증 코드 값 가져오기

    $.ajax({
        url: '/member/verifyEmail',  // 서버의 이메일 인증 처리 URL
        headers: {
            [csrfHeader]: csrfToken  // CSRF 토큰을 헤더에 포함
        },
        type: 'POST',
        data: { 
            email: email,  // 이메일
            code: code     // 인증 코드
        },
        success: function(response) {
           
            if (response.status === 'verified') {
                alert("인증번호 일치");
                $('#result').show();
            } else {
            	alert("인증번호 불일치");
               
            }
        },
        error: function(xhr, status, error) {
            alert("인증에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

</script>
<div class="main-div">
	<div class="login">
		<img class="logo"
			src="${pageContext.request.contextPath}/assets/images/DeeplyLoginLogo.png">
		<form action="/member/requestReset" method="post" id="member_login">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
			<input type="text" name="name" id="name" placeholder="이름" autocomplete="off"/>
			
				<input  id="email" name="email" class="Authentication"
						type="text" placeholder="email" />
					<div class="inz" onclick="submitEmail()">인증요청</div>
					<div id="verificationSection">
						<input  id="code"
							class="Authentication" type="text" placeholder="인증번호" />
						<div class="inz" onclick="verifyEmail()">인증 하기</div>
					</div>
		
					
		
			<input id="result" style="display:none;" type="submit" value="비밀번호 찾기">
			 <c:if test="${not empty message}">
            <div>
                <p style="font-size:24px; margin-top:30px; width:400px;">${message}</p>
            </div>
        </c:if>
		</form>
	</div>


</div>