<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr1.css">
<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs"></script>

<!-- 채팅방 형성 시작 -->
<div class="page-main">
    <h2>채팅방</h2>
    <p>${auser_num}</p> <!-- auser_num을 보여주는 부분 -->

    <!-- 채팅방 생성 -->
    <c:if test="${ch_kind == 1}">
        <div>
            <form:form modelAttribute="chatVO" action="chWrite" id="makeChatroom" enctype="multipart/form-data">
                <div id="chatroom_name">
                    <label for="chat_name">채팅방 이름 만들기</label>
                    <form:input path="chat_name" id="chat_name" />
                </div>
                <div id="make_ballon">
                     말풍선 내용
                   <textarea path="ballon_name"></textarea>  <!-- textarea 수정 -->
                </div>
                <div>
                    <label for="artist_photo">아티스트 사진</label>
                    <input type="file" id="imageInput" />
                    <div id="image-container"></div> <!-- 이미지가 표시될 영역 -->
                </div>

                <div>
                    <button type="submit">채팅방 형성</button>
                </div>
            </form:form>
        </div>
    </c:if>

    <!-- 채팅방 입장 -->
    <c:if test="${ch_kind == 0}">
        <div>
            <form:form modelAttribute="chatVO" action="${pageContext.request.contextPath}/chat/chatting" id="enterChatroom" method="post" enctype="multipart/form-data">
                <form:hidden path="auser_num" value="${param.artist_num}" />
                <button type="submit">채팅방 들어가기</button>
            </form:form>
        </div>
    </c:if>
</div>

<script>
    // 파일 선택 시 이미지 미리보기
    const imageInput = document.getElementById('imageInput');
    const imageContainer = document.getElementById('image-container');

    // 파일이 선택되었을 때 실행
    imageInput.addEventListener('change', function(event) {
        const file = event.target.files[0];  // 선택된 파일
        if (file) {
            const reader = new FileReader();  // 파일을 읽을 FileReader 객체

            reader.onload = function(e) {
                // 읽은 파일의 데이터를 이용하여 이미지 표시
                const img = document.createElement('img');
                img.src = e.target.result;  // 파일 내용(이미지 데이터 URL) 설정
                img.alt = 'Uploaded Image';
                img.style.maxWidth = '100%';  // 이미지 크기 조정

                // 기존 이미지를 지우고 새 이미지 삽입
                imageContainer.innerHTML = '';  
                imageContainer.appendChild(img);
            };

            reader.readAsDataURL(file);  // 파일을 데이터 URL 형식으로 읽기
        }
    });
</script>
