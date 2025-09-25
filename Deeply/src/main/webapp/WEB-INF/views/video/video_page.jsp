<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<style>
/* 모든 CSS 스타일을 여기에 포함합니다 */
.youtube-frame {
	border: none; /* 기본 테두리 제거 */
	border-radius: 15px; /* 둥근 모서리 */
	width: 100%;
	height: 600px;
}

.post-container {
	border-radius: 8px;
	background-color: #fff;
	box-shadow: 0 3px 14px rgba(0, 0, 0, 0.05);
	display: flex;
	flex-direction: column; /* 세로 방향으로 정렬 */
	align-items: start;
	width: 80%;
	padding: 20px 30px 149px;
	font-family: 'Noto Sans', sans-serif;
	margin: 0 auto;
}

.title {
	font-size: 24px;
	font-weight: 700;
	color: #1f2937;
	margin-bottom: 20px;
}

.reply-section {
	width: 100%;
	border-radius: 6px;
	border: 1px solid #d1d5db;
}

/* form.reply-input 스타일 */
.reply-input {
	height: auto;
	align-self: stretch;
	display: flex;
	flex-direction: column;
	padding: 11px 0 0;
	transition: border-color 0.3s ease, box-shadow 0.3s ease;
	border-radius: 6px;
	border: 1px solid #d1d5db;
}

.reply-input:focus-within {
	outline: none;
	border-color: #d1d5db;
	box-shadow: 0 0 0 2px #d1d5db;
}

/* textarea 스타일 */
.reply-textarea {
	resize: none;
	border: none;
	width: 100%;
	height: 150px;
	font-size: 14px;
	color: #6b7280;
	padding: 10px;
	font-family: 'Noto Sans', sans-serif;
	outline: none;
	background: transparent;
}

/* textarea 포커스 시 스타일 */
.reply-textarea:focus {
	outline: none;
}

.action-bar {
	border-radius: 0 0 6px 6px;
	background: #f3f4f6;
	display: flex;
	margin-top: 0px;
	width: 100%;
	flex-direction: column;
	font-size: 14px;
	color: #fff;
	font-weight: 700;
	padding: 0 12px 9px 80px;
}

.divider {
	background-color: #d1d5db;
	align-self: center;
	width: 600px;
	max-width: 100%;
	height: 1px;
	border: 1px solid rgba(209, 213, 219, 1);
}

.post-button {
	border-radius: 6px;
	background-color: #000;
	align-self: end;
	display: flex;
	margin-top: 8px;
	gap: 18px;
	padding: 7px 9px 7px 21px;
	cursor: pointer;
	color: #fff; /* 텍스트 흰색 */
}

.post-button:hover {
	background-color: #333;
}

.post-icon {
	width: 16px;
	height: 16px;
	object-fit: contain;
}

/* 구분선 */
.separator {
	align-self: center;
	margin-top: 15px;
	width: 641px;
	max-width: 100%;
	height: 1px;
	border: 1px solid #e3e3e3;
}

.comments-list {
	margin-top: 20px;
	width: 100%;
	padding: 10px;
	border-radius: 6px;

	/* Flexbox 설정 */
	display: flex;
	flex-direction: column; /* 세로로 정렬 */
	align-items: center;    /* 가로 가운데 정렬 */
}

.comment {
	width: 80%; /* 각 댓글의 너비 */
	background-color: #fff;
	border-radius: 4px;
	text-align: left; /* 텍스트는 기본 왼쪽 정렬 */
}

.comment:last-child {
	border-bottom: none;
}

/* 댓글 상단 유저 정보 */
.user-info {
	display: flex;
	width: 272px;
	max-width: 100%;
	gap: 20px;
	justify-content: start;
	margin: 10px 0 10px 18px;
}

.user-meta {
	display: flex;
	align-items: center;
	gap: 8px;
}

.avatar {
	aspect-ratio: 1;
	object-fit: contain;
	width: 32px;
	border-radius: 11px;
}

.username {
	color: #1f2937;
	font-size: 12px;
	font-weight: 600;
	margin: auto 0;
}

.timestamp {
	color: #6b7280;
	font-size: 10px;
	font-weight: 400;
	margin: auto 0;
}

/* 스레드 라인 + 컨테이너 */
.thread-container {
	display: flex;
	margin-left: 33px;
	flex-wrap: wrap;
	border-left: 4px solid #ccc; /* 두께: 4px, 색상: 연한 회색 */
	padding-left: 15px; /* 필요에 따라 값을 조정하세요 */
}

.thread-line {
	background-color: #e5e7eb;
	width: 2px;
	height: 174px;
	border: 2px solid rgba(229, 231, 235, 1);
}

.post-content {
	align-self: start;
	display: flex;
	flex-direction: column;
	flex-grow: 1;
	flex-basis: 0;
}

.post-text {
	color: #1f2937;
	font-size: 13px;
	font-weight: 400;
	margin: 0 18px 10px 0;
}

.interaction-bar {
	display: flex;
	gap: 20px;
	font-size: 12px;
	color: #4b5563;
	font-weight: 600;
	margin: 16px 0 10px 0px;
}

.action-button {
	display: flex;
	gap: 6px;
	align-items: center;
	cursor: pointer;
}

.action-button:hover {
	text-decoration: underline;
}

/* 대댓글(답글) */
.sub-comment {
	margin-top: 15px;
	margin-left: 30px; /* 부모 댓글과 구분을 위해 들여쓰기 추가 */
	border-left: 2px dotted #ccc;
	padding-left: 15px;
}

/* 대댓글 입력 폼 (기본 숨김) */
.replyForm {
	display: none;
	margin: 10px 0 0 18px;
	width: 80%;
}
</style>

<div class="post-container">
	<!-- 제목 섹션 -->
	<div class="title">${title}</div>

	<!-- 유튜브 프레임 -->
	<iframe src="${youtubeLink}?autoplay=1&mute=1" class="youtube-frame"
		allow="autoplay; fullscreen"></iframe>

	<!-- 최상위 댓글 입력 섹션 -->
	<div class="reply-section">
		<form id="re_form">
			<input type="hidden" name="videoId" value="${video.videoId}">
			<textarea id="re_content" name="commentContent"
				placeholder="댓글을 입력하세요..." class="reply-textarea"></textarea>

			<!-- CSRF 토큰 -->
			<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
			<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">

			<div class="action-bar">
				<div class="divider"></div>
				<button type="submit" class="post-button">
					<div>POST</div>
					<img
						src="https://cdn.builder.io/api/v1/image/assets/TEMP/34589a6e18af93eeda25abd08e3bdb591479aeeac632ec925a6b974cb8722066?placeholderIfAbsent=true&apiKey=ce4da87792964033bdb1e6f244668450"
						class="post-icon" alt="Post Icon" />
				</button>
			</div>
		</form>
	</div>

	<!-- 구분선 -->
	<div class="separator"></div>

	<!-- (1) 댓글 리스트 컨테이너 -->
	<div id="commentListContainer" class="comments-list">
		<!-- Ajax로 불러온 댓글이 여기 append 됩니다 -->
	</div>
</div>

<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
var allComments = []; // 모든 댓글을 저장할 전역 변수

$(document).ready(function(){
    loadCommentList();
});

/** (1) 댓글 목록 로드 */
function loadCommentList(){
    let currentVideoId = '${video.videoId}';
    $.ajax({
        url : '${pageContext.request.contextPath}/videos/commentList',
        type : 'GET',
        data : { videoId : currentVideoId },
        dataType : 'json',
        success : function(response){
            if(response.result === 'success'){
                $('#commentListContainer').empty();
                allComments = response.comments; // 모든 댓글을 전역 변수에 저장

                // 최상위 댓글(부모가 없는 댓글)만 필터링
                let topComments = allComments.filter(function(c){
                    return !c.parentCommentId || c.parentCommentId === 0;
                });

                if(topComments.length > 0){
                    $.each(topComments, function(index, comment){
                        // 레벨 1 (부모)
                        let html = buildCommentHtml(comment, 1);
                        $('#commentListContainer').append(html);
                    });
                } else {
                    $('#commentListContainer').append('<div>등록된 댓글이 없습니다.</div>');
                }
            } else {
                alert('댓글 목록을 불러오는 중 오류가 발생했습니다!');
            }
        },
        error : function(){
            alert('네트워크 또는 서버 오류');
        }
    });
}

/** (2) 최상위 댓글 작성 */
$('#re_form').submit(function(event) {
    event.preventDefault();

    if ($('#re_content').val().trim() === '') {
        alert('내용을 입력하세요');
        $('#re_content').focus();
        return;
    }
    
    let formData = $(this).serialize(); // videoId, commentContent 등
    $.ajax({
        url: '${pageContext.request.contextPath}/videos/writeReply',
        type: 'POST',
        data: formData,
        dataType: 'json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader($('#csrfHeaderName').val(), $('#csrfTokenValue').val());
        },
        success: function(param) {
            if (param.result === 'logout') {
                alert('로그인해야 작성할 수 있습니다.');
            } else if (param.result === 'success') {
                // 댓글 등록 후 목록 다시 조회
                $('#re_content').val('');
                loadCommentList();
            } else {
                alert('댓글 등록 오류 발생');
            }
        },
        error: function() {
            alert('네트워크 오류 발생');
        }
    });
});

/**
 * (3) 댓글 HTML 생성 함수 (대댓글 포함, 3단계까지만 허용)
 *  - comment: 현재 댓글 객체
 *  - level: 댓글 깊이 (1=부모, 2=자식, 3=자식의 자식)
 */
function buildCommentHtml(comment, level) {
    // level이 3이면 더 이상 대댓글 작성 불가 → interaction-bar 제거
    let canReply = (level < 3);

    // (A) 부모(또는 현재) 댓글 영역
    let html =
        '<div class="comment">' + // Open .comment
            '<div class="user-info">' + // Open .user-info
                '<div class="user-meta">' + // Open .user-meta
                    // 아바타 + 사용자 정보
                    '<img src="/member/photoView2?user_num=' + comment.userNum + '" class="avatar" alt="User Avatar" />' +
                    '<div class="username">' + comment.userName + '</div>' +
                    '<div class="timestamp">' + formatTimestamp(comment.createdAt) + '</div>' +
                '</div>' + // Close .user-meta
            '</div>' + // Close .user-info

            '<div class="thread-container">' + // Open .thread-container
                '<div class="post-content">' + // Open .post-content
                    '<p class="post-text">' + escapeHtml(comment.commentContent) + '</p>';

    // (B) interaction-bar (level<3 일 때만)
    if(canReply) {
        html +=
            '<div class="interaction-bar">' +
                '<div class="action-button" onclick="toggleReplyForm(' + comment.commentId + ')">' +
                    '<img src="' + '${pageContext.request.contextPath}' + '/assets/images/comment.svg" ' +
                        'class="post-icon" alt="Comment Icon">' +
                    '<div>Reply</div>' +
                '</div>' +

                '<div class="action-button" onclick="boostPost()">' +
                    '<img src="https://cdn.builder.io/api/v1/image/assets/TEMP/a2792805d54f608f2ffd8a4e021264f6b7d2f11455a059cfab9a9e0183cf3782?placeholderIfAbsent=true&apiKey=..." ' +
                        'class="post-icon" alt="Boost Icon">' +
                    '<div>Boost</div>' +
                '</div>' +

                '<div class="action-button" onclick="likePost()">' +
                    '<img src="' + '${pageContext.request.contextPath}' + '/assets/images/star.svg" ' +
                        'class="post-icon" alt="Like Icon">' +
                    '<div>Like</div>' +
                '</div>' +

                '<div class="action-button" onclick="moreOptions()">' +
                    '<img src="https://cdn.builder.io/api/v1/image/assets/TEMP/969b6a12fe21c1d1769e8ed4b2ff5b28f481984b7f0b46d426588e1a95cdf523?placeholderIfAbsent=true&apiKey=..." ' +
                        'class="post-icon" alt="More Options Icon">' +
                    '<div>More</div>' +
                '</div>' +
            '</div>' + // Close .interaction-bar

            // (C) 대댓글 입력 폼 - 기본 숨김
            '<div class="replyForm" id="replyForm_' + comment.commentId + '" style="margin-bottom:40px; display:none;">' +
                '<textarea id="subReplyContent_' + comment.commentId + '" ' +
                    'class="reply-textarea" style="height:80px; border: 1px solid #d1d5db;" ' +
                    'placeholder="대댓글을 입력하세요..." aria-label="대댓글 입력"></textarea>' +
                '<button type="button" style="margin:5px 0; padding:5px 10px; background:#000; color:#fff; border-radius:4px;" ' +
                        'onclick="submitSubReply(' + comment.commentId + ')" aria-label="대댓글 등록">등록</button>' +
            '</div>';
    } 
    else {
        // level이 3 => 더 이상 대댓글 불가, interaction-bar 미표시
    }

    // post-content 닫기
    html += '</div>'; // Close .post-content

    // (D) 자식 댓글(대댓글) 필터링 후 재귀 (단, level>=3이면 더이상 Reply 안 달리게)
    let childComments = allComments.filter(function(c){
        return c.parentCommentId === comment.commentId;
    });
    if(childComments.length > 0){
        // 자식 댓글 수직 구조로 추가
        $.each(childComments, function(idx, child){
            // 자식 댓글 → buildCommentHtml(child, level+1)
            html += buildCommentHtml(child, level + 1);
        });
    }

    // thread-container & comment div 닫기
    html +=
            '</div>' + // Close .thread-container
        '</div>'; // Close .comment

    return html;
}

/** (4) 대댓글 폼 토글 */
function toggleReplyForm(commentId){
    $('#replyForm_' + commentId).slideToggle();
}

/** (5) 대댓글 등록 */
function submitSubReply(parentCommentId){
    let content = $('#subReplyContent_' + parentCommentId).val().trim();
    if(content === ''){
        alert('대댓글 내용을 입력하세요.');
        return;
    }

    // CSRF 값 가져오기
    let csrfHeader = $('#csrfHeaderName').val();
    let csrfToken = $('#csrfTokenValue').val();

    $.ajax({
        url: '${pageContext.request.contextPath}/videos/writeReply',
        type: 'POST',
        data: {
            videoId: '${video.videoId}',
            commentContent: content,
            parentCommentId: parentCommentId
        },
        dataType: 'json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(param){
            if(param.result === 'logout'){
                alert('로그인해야 작성할 수 있습니다.');
            } else if(param.result === 'success'){
                // 대댓글 등록 성공 시
                $('#subReplyContent_' + parentCommentId).val('');
                toggleReplyForm(parentCommentId);
                loadCommentList(); 
            } else {
                alert('대댓글 등록 오류 발생');
            }
        },
        error: function(){
            alert('네트워크 오류 발생');
        }
    });
}

/* 임시 함수들 */
function boostPost() {
    alert('Boost 기능은 아직 구현되지 않았습니다.');
}
function likePost() {
    alert('Like 기능은 아직 구현되지 않았습니다.');
}
function moreOptions() {
    alert('More 기능은 아직 구현되지 않았습니다.');
}

// (7) XSS 방지
function escapeHtml(text) {
    return $('<div>').text(text).html();
}

// (8) 타임스탬프 포맷
function formatTimestamp(timestamp) {
    let date = new Date(timestamp);
    return date.toLocaleString();
}
</script>
