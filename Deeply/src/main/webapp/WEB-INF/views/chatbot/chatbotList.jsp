<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 챗봇서비스 시작 -->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>챗봇 서비스</title>
<style type="text/css">
body {
	font-family: Arial, sans-serif;
	background-color: #f4f6f9;
	margin: 0;
	padding: 0;
}

.page-main {
	max-width: 800px;
	margin: 50px auto;
	padding: 20px;
	background-color: #ffffff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
}

h2 {
	text-align: center;
	color: #333333;
}

#chatting_message {
	border: 1px solid #dddddd;
	width: 95%;
	height: 400px;
	margin: 20px 0;
	padding: 15px;
	overflow-y: scroll;
	background-color: #f9f9f9;
	border-radius: 10px;
}

.from-position, .to-position {
	display: flex;
	margin-bottom: 10px;
}

.from-position {
	justify-content: flex-end;
}

.from-position .item {
	background-color: #dcf8c6;
	padding: 10px 15px;
	border-radius: 20px;
	max-width: 70%;
	position: relative;
}

.to-position .item {
	background-color: #ffffff;
	padding: 10px 15px;
	border-radius: 20px;
	max-width: 70%;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
	position: relative;
}

.item::after {
	content: '';
	position: absolute;
	bottom: 0;
	width: 0;
	height: 0;
	border: 10px solid transparent;
}

.from-position .item::after {
	right: -10px;
	border-left-color: #dcf8c6;
}

.to-position .item::after {
	left: -10px;
	border-right-color: #ffffff;
}

#chatting_form {
	display: flex;
	flex-direction: column;
}

#chatting_form textarea {
	width: 100%;
	padding: 10px;
	border: 1px solid #cccccc;
	border-radius: 5px;
	resize: none;
	font-size: 16px;
}

#chatting_form input[type="submit"] {
	margin-top: 10px;
	padding: 10px;
	background-color: #000000;
	color: #ffffff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s ease;
}

#chatting_form input[type="submit"]:hover {
	background-color: #000000;
}

.predefined-questions {
	margin-top: 20px;
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	justify-content: center;
}

.predefined-questions button {
	padding: 10px 15px;
	background-color: #000000;
	color: #ffffff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease;
	font-size: 14px;
}

.predefined-questions button:hover {
	background-color: #1976d2;
}
/* 로딩 스피너 스타일 */
.spinner {
	display: none;
	margin: 20px auto;
	width: 40px;
	height: 40px;
	border: 4px solid #f3f3f3;
	border-top: 4px solid #3498db;
	border-radius: 50%;
	animation: spin 1s linear infinite;
}

@
keyframes spin { 0% {
	transform: rotate(0deg);
}

100
%
{
transform
:
rotate(
360deg
);
}
}
#chatting_form ul {
	list-style-type: none;
	padding: 0;
	margin: 0;
	width: 90%; /* 너비 조정 */
	margin: 0 auto; /* 중앙 정렬 */
}

#chatting_form li {
	padding: 0;
	margin: 0;
	display: block; /* 블록 요소로 변경 */
}

#chatting_form textarea {
	width: 100% !important; /* 부모 너비에 맞춤 */
	/* 기존 textarea 스타일 유지 */
}
</style>
<script
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
        $(function(){
            // Enter 키로 메시지 전송
            $('#message').keydown(function(event){
                if(event.keyCode == 13 && !event.shiftKey) {
                    $('#chatting_form').trigger('submit');
                    $('#message').val('').focus();
                }
            });

            // 사전 정의된 질문 버튼 클릭 시
            $('.predefined-questions button').click(function(){
                var question = $(this).text();
                $('#message').val(question);
                $('#chatting_form').trigger('submit');
            });

            // 채팅 메시지 처리
            $('#chatting_form').submit(function(event){	
                var message = $('#message').val().trim();
                if(message == ''){
                    alert('내용을 입력하세요!');
                    $('#message').val('').focus();
                    return false;
                }

                // 사용자 메시지 추가
                $('#chatting_message').append('<div class="from-position"><div class="item">'+message.replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>')+'</div></div>');
                $('#chatting_message').scrollTop($("#chatting_message")[0].scrollHeight);

                // 로딩 스피너 표시
                $('.spinner').show();

                // 폼 데이터 읽기
                const form_data = $(this).serialize();

                // 서버와 통신
                $.ajax({
                    url:'chatbot',
                    type:'post',
                    data:form_data,
                    dataType:'json',
                    beforeSend:function(xhr){
                        xhr.setRequestHeader($('#csrfHeaderName').val(),$('#csrfTokenValue').val());
                    },
                    success:function(param){
                        // 로딩 스피너 숨기기
                        $('.spinner').hide();

                        // 챗봇 응답 메시지 추가
                        $('#chatting_message').append('<div class="to-position"><div class="item">'+param.response.replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>')+'</div></div>');
                        $('#chatting_message').scrollTop($("#chatting_message")[0].scrollHeight);
                    },
                    error:function(){
                        // 로딩 스피너 숨기기
                        $('.spinner').hide();
                        alert('네트워크 오류!');
                    }
                });
                event.preventDefault();
            });		
        });
    </script>
</head>
<body>
	<div class="page-main">
		<h2>챗봇 서비스</h2>
		<div id="chatting_message"></div>
		<div class="spinner"></div>
		<!-- 로딩 스피너 -->
		<form method="post" id="chatting_form">
			<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
			<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
			<ul>
				<li><label for="message">내용</label> <textarea rows="5"
						cols="40" name="message" id="message" placeholder="메시지를 입력하세요..."></textarea>
					<input type="submit" value="전송"></li>
			</ul>
		</form>
		<div class="predefined-questions">
			<button>굿즈 구매 방법 알려줘</button>
			<button>멤버십 영상은 어떻게 시청해?</button>
			<button>콘서트 티켓 선예약은 어떻게 해?</button>
			<button>아티스트에게 편지 보내는 방법</button>
			<button>커뮤니티에서 소통하는 법</button>
		</div>
	</div>
</body>
</html>
<!-- 챗봇서비스 끝 -->
