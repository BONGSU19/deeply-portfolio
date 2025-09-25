<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>


<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<input type="hidden" id="chat_num" value="${chat_num}">
<!-- socket 및 stomp cdn -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr1.css">
<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs"></script>

<!--  
<div class="page-main">

  <h2>형성된 채팅방</h2>
  
    <div class="well">
       <input type="text" id="msg" value="1212" class="form-control" />
       <button id="btnSend" class="btn btn-primary">Send Message</button>
    </div>
  
	<c:if test="${chat_kind==1}">
	<div>
		아티스트의 채팅방
		${chat_condition}
	 	<button type="submit" onclick="location.href='chWrite'">채팅방 닫기</button>
	 </div>
	</c:if> 
	
-->

  <!-- 유저 이름 설정 -->
<!-- Login Section -->
      <!-- 로그인 컨테이너 -->
   
 
 <div class="chatroom-detail">
        <h2>채팅방 상세</h2>
        
        <p><strong>채팅방 이름:</strong> ${chatName}</p>
        <p><strong>말풍선 내용:</strong> ${ballonName}</p>

        <!-- 아티스트 사진 미리보기 -->
        <c:if test="${not empty artistPhoto}">
            <p><strong>아티스트 사진:</strong></p>
        </c:if>

    </div>
 
 
         <div class="container">
        <!-- 로그인 화면 -->
        <div class="login-container">
            <h2>로그인</h2>
            <p>사용자 이름을 자동으로 가져옵니다...</p>
        </div>

        <!-- 방 선택 화면 -->
        <div class="room-container">
            <h2>채팅방 선택</h2>
            <div id="roomList"></div>
            <button id="btnJoinRoom">Join Room</button>
        </div>

        <!-- 채팅 화면 -->
        <div id="chatContainer">
            <div id="message-list"></div>
            <div class="input-container">
                <input type="text" id="msg" placeholder="메시지를 입력하세요..." />
                <button id="btnSend">전송</button>
            </div>
        </div>
    </div>

    <!-- JS 라이브러리 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockJS-client@1.5.1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
   



<script src="${pageContext.request.contextPath}/assets/js/chat.js"></script>

            <!-- Messages will appear here
        </div>
    </div>
    <div class="input-container">
        <input type="text" id="msg" placeholder="Type a message...">
        <button id="btnSend">Send</button>
    </div>
 
<div id="chat-container"></div>
    <h2>코코넛</h2>
    <div id="message-list"></div>
    <div id="input-container">
        <input type="text" id="msg" placeholder="Enter your message..." />
        <button id="btnSend">보내기</button>
    </div>

	<c:if test="${chat_kind==0}">
	<div>
		유저의 채팅방
		${chat_condition}

	 	<button type="submit" onclick="${pageContext.request.contextPath}/main/main">채팅방 나가기</button>
	 </div>
	</c:if>-->