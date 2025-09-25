<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!-- 글상세 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/commu.reply.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/videoAdapter.js"></script>
<div class="page-main">
    <!-- 게시판 헤더 -->
    <div class="board-header">
        <c:if test="${commu.c_category == 1}">
            <h2 class="commu-category">전체 게시판</h2>
        </c:if>
        <c:if test="${commu.c_category == 2}">
            <h2 class="commu-category">팬덤 게시판 > ${commu.arti_name}</h2>
        </c:if>
    </div>

    <!-- 게시글 정보 -->
    <div class="post-title">
        <h3 class="commu-category">${commu.c_title}</h3>
        <hr>
        <ul class="post-meta">
            <li class="post-date">
                <c:if test="${empty commu.c_update}">
                    ${commu.c_date}
                </c:if>
                <c:if test="${!empty commu.c_update}">
                    ${commu.c_update}
                </c:if>
            </li>
            <hr size="1px">
            <li class="post-views">조회수 ${commu.c_hit}</li>
        </ul>
    </div>
		    <!-- 작성자 정보 -->
		    <div class="author-info">
		        <img src="${pageContext.request.contextPath}/member/viewProfile?user_num=${commu.user_num}" width="40" height="40" class="my-photo">
		        <span class="author-name">${commu.nick_name}</span>
		    </div>


    <!-- 이미지 출력 -->
    <c:if test="${!empty commu.c_file}">
        <div class="post-image">
            <c:if test="${fn:endsWith(commu.c_file, '.jpg') || 
                        fn:endsWith(commu.c_file, '.JPG') || 
                        fn:endsWith(commu.c_file, '.jpeg') || 
                        fn:endsWith(commu.c_file, '.JPEG') || 
                        fn:endsWith(commu.c_file, '.gif') || 
                        fn:endsWith(commu.c_file, '.GIF') || 
                        fn:endsWith(commu.c_file, '.png') || 
                        fn:endsWith(commu.c_file, '.PNG')}">
                <div class="align-center">
                    <img src="${pageContext.request.contextPath}/assets/upload/${commu.c_file}" class="detail-img">
                </div>
            </c:if>
        </div>
    </c:if>

    <!-- 게시글 내용 -->
    <div class="post-content">
        ${commu.c_content}
    </div>

    <!-- 버튼 -->
    <div class="post-buttons">
        <input type="button" class="grey-btn" value="이전으로" onclick="location.href='list'">
        <c:if test="${!empty principal && principal.memberVO.user_num == commu.user_num}">
            <input type="button" value="글 수정" class="grey-btn" style="font-align:right;" onclick="location.href='update?c_num=${commu.c_num}'">
            <input type="button" value="글 삭제" id="delete_btn">
            <script type="text/javascript">
                const delete_btn = document.getElementById('delete_btn');
                delete_btn.onclick = function () {
                    const choice = confirm('삭제하시겠습니까?');
                    if (choice) {
                        location.replace('delete?c_num=${commu.c_num}');
                    }
                };
            </script>
        </c:if>
    </div>

    <hr size="1" width="100%">
    
    <!-- 댓글 목록 출력 -->
    <div id="output"></div>
    <div id="loading" style="display:none;">
        <img src="${pageContext.request.contextPath}/assets/images/loading.gif" width="30" height="30">
    </div>
    <div class="paging-button" style="display:none;">
        <input type="button" value="더보기">
    </div>

    <!-- 댓글 UI -->
    <div class="reply-section">
        <span class="re-c_title">댓글 달기</span>
        <form id="cre_form">
            <input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
            <input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
            <input type="hidden" name="c_num" value="${commu.c_num}" id="c_num">
            <textarea rows="3" cols="50" name="cre_content" id="cre_content" class="crep-content" <c:if test="${empty principal}">disabled="disabled"</c:if>><c:if test="${empty principal}">로그인해야 작성할 수 있습니다.</c:if></textarea>
            <c:if test="${!empty principal}">
                <div id="cre_first">
                    <span class="letter-count">300/300</span>
                </div>
                <div id="cre_second" class="align-right">
                    <input type="submit" value="등록">
                </div>
            </c:if>
        </form>
    </div>

</div>