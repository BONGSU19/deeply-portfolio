<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- jQuery CDN을 추가 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>

<!-- 회원가입 시작 -->

<div class="main-div" style="justify-content: center; width:1400px; margin-top: 80px;margin-bottom:185px;">
	<script type="text/javascript">

var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
function registerUser() {
	

}


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
        url: '/member/emailSubmit',  // 서버의 이메일 전송 처리 URL
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
                $('#registerSection').show();
            } else {
            	alert("인증번호 불일치");
               
            }
        },
        error: function(xhr, status, error) {
            alert("인증에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

$(function(){
	/*--------------------
	 *      회원가입
	 *--------------------*/
	
	//아이디 중복 여부 저장 변수 : 0 -> 아이디 중복 또는 중복체크 미실행
	//                        1 -> 아이디 미중복
	let checkId = 0;
	//별명 중복 여부 저장 변수 : 0 -> 별명 중복 또는 중복체크 미실행
	//                       1 -> 별명 미중복	
	let checkNick = 0;
	
	//아이디 중복 체크
	$('#confirm_id').click(function(){
		if($('#id').val().trim()==''){
			$('#message_id').css('color','red')
			                .text('아이디를 입력하세요');
	        $('#id').val('').focus();
			return;						
		}
		
		$('#message_id').text('');//메시지 초기화
		
		//서버와 통신
		$.ajax({
			url:'/member/confirmIdnNickName',
			type:'get',
			data:{id:$('#id').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'idNotFound'){
					checkId = 1;
					$('#message_id').css('color','#000')
					                .text('등록 가능 ID');
				}else if(param.result == 'idDuplicated'){
					checkId = 0;
					$('#message_id').css('color','red')
					                .text('중복된 ID');
					$('#id').val('').focus();				
				}else if(param.result == 'notMatchPattern'){
					checkId = 0;
					$('#message_id').css('color','red')
					                .text('영문,숫자 4~12 입력');
					$('#id').val('').focus();
				}
			},
			error:function(){
				checkId = 0;
				alert('ID 중복 체크 오류');
			}
		});
	});//end of click
	
	//별명 중복 체크
	$('#confirm_nick').click(function(){
		if($('#group_name').val().trim()==''){
			$('#message_nick').css('color','red')
			                  .text('별명을 입력하세요');
			$('#group_nam').val('').focus();
			return;				  
		}
		
		$('#message_nick').text(''); //메시지 초기화
		
		//서버와 통신
		$.ajax({
			url:'/member/confirmIdnNickName',
			type:'get',
			data:{group_name:$('#group_name').val()},id: $('#id').val(),  // 아이디
	        nick_name: $('#nick_name').val(),  // 별명
			dataType:'json',
			success:function(param){
				if(param.result == 'groupNameNotFound'){
					checkNick = 1;
					$('#message_nick').css('color','#000')
					                  .text('등록 가능 별명');									 
				}else if(param.result == 'groupNameDuplicated'){
					checkNick = 0;
					$('#message_nick').css('color','red')
					                  .text('중복된 별명');
					$('#nick_name').val('').focus();
				}else if(param.result == 'notMatchPattern'){
					checkNick = 0;
					$('message_nick').css('color','red')
					                 .text('한글,영문,숫자 2~10자만 가능');
					$('#nick_name').val('').focus();				 
				}else{
					checkNick=0;
					alert('별명 중복 체크 오류');
				}
			},
			error:function(){
				checkNick=0;
				alert('네트워크 오류 발생');
			}
		});
	});//end of click
	
	//아이디,별명 중복 안내 메시지 초기화 및 아이디,별명 중복 값 초기화
	$('#id,#nick_name').keydown(function(){
		if($(this).attr('id') == 'id'){
			checkId=0;
			$('#message_id').text('');
		}else if($(this).attr('id') == 'nick_name'){
			checkNick=0;
			$('#message_nick').text('');
		}
	});//end of keydown
	
	//submit 이벤트 발생시 아이디, 별명 중복 체크 여부 확인
	$('#member_register2').click(function(){
		//아이디 중복 체크 필수
		if(checkId==0){
			$('#message_id').css('color','red')
			                .text('아이디 중복 체크 필수!');
			if($('#id').val().trim()==''){
				$('#id').val('').focus();
			}				
			return false;
		}
		$.ajax({
		    url: '/artistMember/registerUser',
		    type: 'POST',
		    headers: {
		        [csrfHeader]: csrfToken  // CSRF 토큰을 헤더에 포함
		    },
		    data: $('#member_register').serialize(),
		    success: function(response) {
		        if (response.error) {
		            // 오류가 있을 경우, 해당 오류를 페이지에 표시
		            $('#errorMessage').text(response.error);
		        } else {
		            // 성공 시 처리
		            alert("회원가입이 완료되었습니다.");
		            window.location.href = "/member/login";
		        }
		    },
		    error: function(xhr, status, error) {
		        // 오류가 발생하면 화면에 표시
		        $('#errorMessage').text("서버 오류가 발생했습니다.");
		    }
		});
	});	
});











</script>
	<div class="register-wrap">
		<div class="register-image">
			<img alt="" src="${pageContext.request.contextPath}/assets/image_bundle/register.png">
		</div>

		<div class="register-content">

			<img class="logo-img"
				src="${pageContext.request.contextPath}/assets/images/artistLogo.png" style="height:80px;">
			<h1>Create your Account</h1>
			<h2>계정을 만들어 주세요</h2>
			<div style="width: 290px;">
				<form:form class="register-form" modelAttribute="artistVO"
					 id="member_register">

					<form:input path="email" id="email" class="Authentication"
						type="text" placeholder="email" />
					<div class="inz" onclick="submitEmail()">인증요청</div>
					<div style="display: none;" id="verificationSection">
						<form:input path="code" id="code"
							class="Authentication" type="text" placeholder="인증번호" />
						<div class="inz" onclick="verifyEmail()">인증 하기</div>
					</div>

					<div style="display: none;" id="registerSection">
						<form:input class="Authentication" path="id" placeholder="아이디" />
						<input type="button" id="confirm_id" value="중복체크"
							class="default-btn">
						<form:errors path="id" cssClass="error-color" />
						<span id="message_id"></span>
						<form:input path="passwd_hash" type="text" placeholder="비밀번호" />
						<form:input path="confirmPassword" type="text" placeholder="비밀번호확인" />
						<form:errors path="passwd_hash" cssClass="error-color" />
						<form:input path="name" type="text" placeholder="이름" />
						<form:errors path="name" cssClass="error-color" />
						<form:input id="group_name" path="group_name" type="text" placeholder="그룹이름" />
						<form:input id="intro" path="intro" type="text" placeholder="자기소개 글" />
<form:input id="debut_date" path="debut_date" type="date" placeholder="데뷔날자" />
					
						
					
							<div id="message_nick" style="width:300px;"></div>
	
					</div>
					<div class="line-with-text2">
						<span>or</span>
					</div>
					<table class="register-api">
						<tr>
							<td><img class="inline-img2" alt=""
								src="${pageContext.request.contextPath}/assets/images/naver.png">
								<span class="inline-text2">네이버로그인</span></td>
							<td><img class="inline-img2" alt=""
								src="${pageContext.request.contextPath}/assets/images/kakao.png">
								<span class="inline-text2">카카오로그인</span></td>
						</tr>
						<tr>


						</tr>
					</table>
					<div class="register-submit" id="member_register2" onclick="registerUser()">회원가입</div>
				</form:form>
				<div id="errorMessage" class="error"></div>
			</div>
		</div>
	</div>
</div>






