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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/videoAdapter.js"></script>
<div class="page-main">
    <!-- 게시판 헤더 -->
    <div class="notice-header">
            <h2>공지사항</h2>
    </div>
    <!-- 게시글 정보 -->
    <div class="post-title">
        <h3 class="commu-category">${notice.notice_title}</h3>
        <hr>
        <ul class="post-meta">
            <li class="post-date">
                <c:if test="${empty notice.notice_update}">
                    ${notice.notice_date}
                </c:if>
                <c:if test="${!empty notice.notice_update}">
                    ${notice.notice_update}
                </c:if>
            </li>
            <hr size="1px">
        </ul>
    </div>
		    <!-- 작성자 정보 -->
		    <div class="author-info">
		        <img src="${pageContext.request.contextPath}/member/viewProfile?user_num=${notice.user_num}" width="40" height="40" class="my-photo">
		        <span class="author-name">${notice.group_name}</span>
		    </div>


    <!-- 이미지 출력 -->
    <c:if test="${!empty notice.notice_file}">
        <div class="post-image">
            <c:if test="${fn:endsWith(notice.notice_file, '.jpg') || 
                        fn:endsWith(notice.notice_file, '.JPG') || 
                        fn:endsWith(notice.notice_file, '.jpeg') || 
                        fn:endsWith(notice.notice_file, '.JPEG') || 
                        fn:endsWith(notice.notice_file, '.gif') || 
                        fn:endsWith(notice.notice_file, '.GIF') || 
                        fn:endsWith(notice.notice_file, '.png') || 
                        fn:endsWith(notice.notice_file, '.PNG')}">
                <div class="align-center">
                    <img src="${pageContext.request.contextPath}/assets/upload/${notice.notice_file}" class="detail-img">
                </div>
            </c:if>
        </div>
    </c:if>

    <!-- 게시글 내용 -->
    <div class="post-content">
        ${notice.notice_content}
    </div>

    <!-- 버튼 -->
    <div class="post-buttons">
        <input type="button" class="grey-btn" value="이전으로" onclick="location.href='list'">
        <c:if test="${!empty principal && principal.artistVO.user_num == notice.user_num}">
            <input type="button" value="글 수정" class="grey-btn" style="font-align:right;" onclick="location.href='update?notice_num=${notice.notice_num}'">
            <input type="button" value="글 삭제" id="delete_btn">
            <script type="text/javascript">
                const delete_btn = document.getElementById('delete_btn');
                delete_btn.onclick = function () {
                    const choice = confirm('삭제하시겠습니까?');
                    if (choice) {
                        location.replace('delete?notice_num=${notice.notice_num}');
                    }
                };
            </script>
        </c:if>
    </div>
</div>