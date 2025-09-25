<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 그룹별 영상 페이지 -->
<div class="page-container">

    <!-- 동적으로 그룹명을 표시 -->
    <h1 class="main-heading">${groupName}</h1>

    <!-- 멤버십 섹션 헤더 -->
    <div class="heading-container">
        <h2 class="section-heading">멤버십 전용</h2>
        <div class="right-container">
        	<c:if test="${isArtist}">
            <div class="upload-button" onclick="location.href='${pageContext.request.contextPath}/artist/videos/upload_form'">영상 업로드</div>
            </c:if>
            <p>멤버십 영상 페이지 바로가기 &gt;</p>
        </div>
    </div>

    <!-- [멤버십 전용 영상 섹션] -->
    <div class="video-section">
        <div class="scroll-container" id="scrollContainerMembership">
            <div class="scroll-track">
                <c:forEach var="video" items="${membershipVideos}" varStatus="status">
                    <c:if test="${status.index < 7}">
                        <!-- 유튜브 썸네일 처리 -->
                        <c:choose>
                            <c:when test="${fn:contains(video.mediaUrl, 'youtube.com/watch?v=')}">
                                <c:set var="youtubeId" value="${fn:substringAfter(video.mediaUrl, 'v=')}"/>
                                <c:set var="thumbnailUrl" value="https://img.youtube.com/vi/${youtubeId}/0.jpg"/>
                            </c:when>
                            <c:when test="${fn:contains(video.mediaUrl, 'youtu.be/')}">
                                <c:set var="youtubeId" value="${fn:substringAfter(video.mediaUrl, 'youtu.be/')}" />
                                <c:set var="thumbnailUrl" value="https://img.youtube.com/vi/${youtubeId}/0.jpg" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="thumbnailUrl" value="${video.mediaUrl}" />
                            </c:otherwise>
                        </c:choose>

                        <div class="video-card" onclick="location.href='${pageContext.request.contextPath}/artist/videos/page?videoId=${video.videoId}&group_num=${groupNum}'">
                            <img src="${thumbnailUrl}" alt="썸네일" />
                            <div class="video-card-title">${video.title}</div>
                            <div class="video-card-description">${video.description}</div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="scroll-button scroll-left" id="scrollLeftMembership">&lt;</div>
        <div class="scroll-button scroll-right" id="scrollRightMembership">&gt;</div>

        <!-- 비멤버십 사용자에게 오버레이 표시 -->
        <c:if test="${!isMembership}">
            <div class="membership-overlay">
                <p>멤버십 전용 영상입니다. 멤버십 가입 후 시청하실 수 있습니다.</p>
                <button class="membership-join-btn" onclick="location.href='${pageContext.request.contextPath}/artist/list'">멤버십 가입하기</button>
            </div>
        </c:if>
    </div>

    <!-- [카테고리별 영상 섹션] -->
    <c:forEach var="category" items="${categories}" varStatus="status">
        <h2 class="section-heading">${category.category_name}</h2>
        <div class="video-section">
            <!-- scrollContainer{index} -->
            <div class="scroll-container" id="scrollContainer${status.index}">
                <div class="scroll-track">
                    <c:forEach var="video" items="${categoryVideosMap[category.category_id]}">
                        <c:choose>
                            <c:when test="${fn:contains(video.mediaUrl, 'youtube.com/watch?v=')}">
                                <c:set var="youtubeId" value="${fn:substringAfter(video.mediaUrl, 'v=')}" />
                                <c:set var="thumbnailUrl" value="https://img.youtube.com/vi/${youtubeId}/0.jpg" />
                            </c:when>
                            <c:when test="${fn:contains(video.mediaUrl, 'youtu.be/')}">
                                <c:set var="youtubeId" value="${fn:substringAfter(video.mediaUrl, 'youtu.be/')}" />
                                <c:set var="thumbnailUrl" value="https://img.youtube.com/vi/${youtubeId}/0.jpg" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="thumbnailUrl" value="${video.mediaUrl}" />
                            </c:otherwise>
                        </c:choose>

                        <div class="video-card" onclick="location.href='${pageContext.request.contextPath}/artist/videos/page?videoId=${video.videoId}&group_num=${groupNum}'">
                            <img src="${thumbnailUrl}" alt="썸네일" />
                            <div class="video-card-title">${video.title}</div>
                            <div class="video-card-description">${video.description}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="scroll-button scroll-left" id="scrollLeft${status.index}">&lt;</div>
            <div class="scroll-button scroll-right" id="scrollRight${status.index}">&gt;</div>
        </div>
    </c:forEach>

</div>

<!-- 스크롤 제어 스크립트 -->
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', () => {
    // 옛날 코드와 동일하게, 버튼 클릭 시 scrollBy() 사용
    // 1) 먼저 멤버십 섹션을 배열에 추가
    const sections = [
        {
            container: 'scrollContainerMembership',
            left: 'scrollLeftMembership',
            right: 'scrollRightMembership'
        }
    ];

    // 2) 카테고리별 섹션도 순서대로 추가
    //    JSP에서 categories의 개수만큼 ID가 생성되므로, 동일하게 push
    //    (scrollContainer0, scrollLeft0, scrollRight0), (scrollContainer1, scrollLeft1, scrollRight1), ...
    <c:forEach var="category" items="${categories}" varStatus="st">
        sections.push({
            container: 'scrollContainer${st.index}',
            left: 'scrollLeft${st.index}',
            right: 'scrollRight${st.index}'
        });
    </c:forEach>
    
    // 3) 각각의 섹션에 대해 버튼 클릭 이벤트 + 버튼 표시 업데이트
    sections.forEach((section) => {
        const scrollContainer = document.getElementById(section.container);
        const leftButton = document.getElementById(section.left);
        const rightButton = document.getElementById(section.right);

        // 혹시 해당 ID가 없으면 건너뛰기
        if (!scrollContainer || !leftButton || !rightButton) return;

        // 왼쪽 버튼 클릭
        leftButton.addEventListener('click', () => {
            scrollContainer.scrollBy({ left: -330, behavior: 'smooth' });
        });

        // 오른쪽 버튼 클릭
        rightButton.addEventListener('click', () => {
            scrollContainer.scrollBy({ left: 330, behavior: 'smooth' });
        });

        // 스크롤 위치에 따라 버튼 표시/비표시
        function updateButtons() {
            // 맨 왼쪽으로 스크롤되어 있으면 left 버튼 숨김
            leftButton.style.display = scrollContainer.scrollLeft > 0 ? 'flex' : 'none';
            // 맨 오른쪽이면 right 버튼 숨김
            rightButton.style.display =
                scrollContainer.scrollLeft < (scrollContainer.scrollWidth - scrollContainer.clientWidth)
                    ? 'flex'
                    : 'none';
        }

        // 스크롤 이벤트 감지
        scrollContainer.addEventListener('scroll', updateButtons);
        
        // 페이지 로드 시 초기 버튼 상태 설정
        updateButtons();
    });
});
</script>
