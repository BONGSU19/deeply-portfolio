<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- MyPage 메뉴 시작 -->
<div class="side-bar">
	<ul>
		<li>
			<img src="${pageContext.request.contextPath}/member/photoView"
			       width="200" height="200" class="my-photo">
			<div class="camera" id="photo_btn">
				<img src="${pageContext.request.contextPath}/assets/images/camera.png" width="35">
			</div>       
		</li>
		<li>
			<div id="photo_choice" style="display:none;">
				<input type="hidden" id="csrfHeaderName"
				       value="${_csrf.headerName}">
				<input type="hidden" id="csrfTokenValue"
				       value="${_csrf.token}">
				<input type="file" id="upload"
				    accept="image/gif,image/png,image/jpeg">
				<br>
				<input type="button" value="전송" id="photo_submit">
				<input type="button" value="취소" id="photo_reset">           
			</div>
		</li>
	</ul>
	<ul>
		<li>
			<input type="button" class="menu-btn" value="비밀번호변경" 
			  onclick="location.href='changePassword'">
		</li>
		<li>
			<input type="button" class="menu-btn" value="회원탈퇴" 
			  onclick="location.href='delete'">
		</li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/customjs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/member.profile.js"></script>
<!-- MyPage 메뉴 끝 -->





