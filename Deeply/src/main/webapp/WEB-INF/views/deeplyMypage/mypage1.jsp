<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- 회원정보 시작 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- jQuery CDN을 추가 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/member.profile.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/customjs.js"></script>
	<c:if test="${not empty successMessage}">
    <script type="text/javascript">
        alert('${successMessage}');
    </script>
</c:if>
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
			url:'confirmIdnNickName',
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
		if($('#nick_name').val().trim()==''){
			$('#message_nick').css('color','red')
			                  .text('별명을 입력하세요');
			$('#nick_name').val('').focus();
			return;				  
		}
		
		$('#message_nick').text(''); //메시지 초기화
		
		//서버와 통신
		$.ajax({
			url:'confirmIdnNickName',
			type:'get',
			data:{nick_name:$('#nick_name').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'nickNotFound'){
					checkNick = 1;
					$('#message_nick').css('color','#000')
					                  .text('등록 가능 별명');									 
				}else if(param.result == 'nickDuplicated'){
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
		//별명 중복 체크 선택
		if(checkNick == 0){
			$('#message_nick').css('color','red').html('<div style="width:200px;">별명 중복체크를 해주세용</div>');
			if($('#nick_name').val().trim()==''){
				$('#nick_name').val('').focus();
			}
			return false;
			
			
		}
		$.ajax({
		    url: '/member/registerUser',
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
		        $('#errorMessage').text("중복체크를 완료해주세요.");
		    }
		});
	});	
});











</script>
<c:if test="${isMemberVO}">
<div class="main-div" style="text-align: center">
	<h2>
		유저 정보 수정 
	</h2>
	<div class="profile-image">
		<img src="${pageContext.request.contextPath}/member/photoView"
			width="200" height="200" class="my-photo2" id="photo_btn">
	</div>
	<div class="mypage-1">
		<div id="photo_choice" style="display: none;">
			<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
			<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
			<input type="file" id="upload"
				accept="image/gif,image/png,image/jpeg"> <br> <input
				type="button" value="전송" id="photo_submit"> <input
				type="button" value="취소" id="photo_reset">
		</div>
		<div
			style="margin-left: 50px; padding-top: 22px; width: 350px; display: inline-block; text-align: left;">
			<b>이름</b> ${member.name}
			<div style="width: 150px; display: inline-block; width: 10px;"></div>
			<b style="margin-right: 30px;">연락처</b> ${member.phone}
		</div>
		<div
			style="margin-left: 50px; padding-top: 22px; width: 350px; display: inline-block; text-align: left;">
			<b>닉네임</b> ${member.nick_name}
			<div style="width: 150px; display: inline-block; width: 10px;"></div>
			<b style="margin-right: 30px;">예치금</b>0
		</div>
		<div
			style="margin-left: 50px; padding-top: 22px; width: 350px; display: inline-block; text-align: left;">
			<b>이메일</b> ${member.email}
			<div style="width: 150px; display: inline-block; width: 10px;"></div>
			<b style="margin-right: 30px;">소셜연결</b>
		</div>
		<div
			style="margin-left: 50px; padding-top: 22px; width: 350px; display: inline-block; text-align: left;">
			<b>주소</b> ${member.zipcode}${member.address1}${member.address2}
		</div>

	</div>
	<div class="register-wrap">
		<div class="register-content"
			style="margin: 0 auto; margin-top: 50px;">
			<form:form class="register-form" method="post" action="update"
				modelAttribute="memberVO" id="member_register">

				<form:input path="email" id="email" type="text" placeholder="email" />

				<form:input class="Authentication" path="id" placeholder="아이디" />
				<input type="button" id="confirm_id" value="중복체크"
					class="default-btn">
			
				<div id="message_id" style="width: 300px;"></div>

				
				<form:input path="nick_name" class="Authentication" type="text"
					placeholder="닉네임" />
				<form:errors path="nick_name" cssClass="error-color" />
				<input type="button" id="confirm_nick" value="중복체크"
					class="default-btn">

				<div id="message_nick" style="width: 300px;"></div>
				<form:input path="zipcode" id="zipcode" class="Authentication2"
					type="text" placeholder="우편번호" />
				
			
				<input type="button" onclick="execDaumPostcode()" value="우편번호찾기"
					class="default-btn2">
				<form:input path="address1" id="address1" type="text"
					placeholder="주소" />
			
		

				<form:input path="address2" type="text" placeholder="상세주소" />
			
			
				<form:input path="phone" id="phone" type="text" placeholder="번호" />
		
				
				<form:button style="width:300px;">저장</form:button>

			</form:form>
		</div>
	</div>
</div>
</c:if>


<c:if test="${!isMemberVO}">
<div class="main-div" style="text-align: center">
	<h2>
		아티스트 정보 수정
	</h2>
	<div class="profile-image">
		<img src="${pageContext.request.contextPath}/member/photoView"
			width="200" height="200" class="my-photo2" id="photo_btn">
	</div>
	<div class="mypage-1">
		<div id="photo_choice" style="display: none;">
			<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
			<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
			<input type="file" id="upload"
				accept="image/gif,image/png,image/jpeg"> <br> <input
				type="button" value="전송" id="photo_submit"> <input
				type="button" value="취소" id="photo_reset">
		</div>
		<div
			style="margin-left: 50px; padding-top: 22px; width: 350px; display: inline-block; text-align: left;">
			<b>아이디</b> ${member.id}
		
		</div>
		
	
		<div
			style="margin-left: 50px; padding-top: 22px; width: 350px; display: inline-block; text-align: left;">
			<b>이메일</b> ${member.email}
		

		</div>
	
	</div>
	<div class="register-wrap">
		<div class="register-content"
			style="margin: 0 auto; margin-top: 50px;">
			<form:form class="register-form" method="post" action="update"
				modelAttribute="artistVO" id="member_register">

				<form:input path="email" id="email" type="text" placeholder="email" />

				<form:input class="Authentication" path="id" placeholder="아이디" />
				<input type="button" id="confirm_id" value="중복체크"
					class="default-btn">
			
				<div id="message_id" style="width: 300px;"></div>



		
		
			
		


				<form:button style="width:300px;">저장</form:button>

			</form:form>
		</div>
	</div>
</div>

</c:if>
<!-- 회원정보 끝 -->

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


