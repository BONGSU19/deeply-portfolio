<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/artistPage.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/follow.js"></script>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>


<div class="artist-main background-black">
	<div class="member-selection-container">
		<div class="member-selection-header align-center">
			<div class="font-white bold-title top-5 font-1_5">편지로 소통하고싶은 ${vo.group_name} 멤버를 선택해 주세요</div>
			<div class="member-selection-close font-white font-1">닫기</div>
		</div>
		<div class="member-selection-div">
			<c:forEach var="member" items="${members}">
				<a href="${pageContext.request.contextPath}/letter/list?artist_num=${member.user_num}">
				<div class="member-selection">
					<img class="member-profile-img" src="/member/photoView2?user_num=${member.user_num}">
					<div class="align-center font-white bold-title top-5">${member.name}</div>
				</div>
				</a>
			</c:forEach>
		</div>
	</div>
	<div class="member-selection-container2">
		<div class="member-selection-header align-center">
			<div class="font-white bold-title top-5 font-1_5">채팅하고싶은 ${vo.group_name} 멤버를 선택해 주세요</div>
			<div class="member-selection-close font-white font-1">닫기</div>
		</div>
		<div class="member-selection-div">
			<c:forEach var="member" items="${members}">
				<a href="${pageContext.request.contextPath}/chat/chatting?artist_num=${member.user_num}">
				<div class="member-selection">
					<img class="member-profile-img" src="/member/photoView2?user_num=${member.user_num}">
					<div class="align-center font-white bold-title top-5">${member.name}</div>
				</div>
				</a>
			</c:forEach>
		</div>
	</div>
	<c:if test="${!empty vo.group_photo && vo.group_photo != ''}">
	<div class="a-mainpic align-center">
		<img class="a-mainImg" src="${pageContext.request.contextPath}/assets/upload/${vo.group_photo}">
	</div>
	</c:if>
	<div class="artist-detail">
		<div class="vertical-center">
			<div class="width-80">
				<div class="a-fandom font-white bold-title vertical-center">${vo.fandom_name}</div>
				<div class="a-title font-white bold-title vertical-center">${vo.group_name}</div>
			</div>
			<div class="width-20">
			<c:if test="${empty principal.artistVO || principal.artistVO.group_name != vo.group_name}">
				<hr class="premium-hr">
				<div class="premium-btn font-white bold-title align-center">
					팔로우 부분
				</div>
				<hr class="premium-hr">
			</c:if>
			<c:if test="${!empty principal.artistVO && principal.artistVO.group_name == vo.group_name}">
				<button class="artist-modify-btn font-white bold-title align-center" onclick="location.href='${pageContext.request.contextPath}/artist/modify?group_num=${vo.group_num}'">
					정보 수정    
				</button>
			</c:if>	
			</div>
			
		</div>
		<div class="a-desc font-white vertical-center">${vo.intro_desc}</div>
		<div class="detail-premium-container">
			
			<c:if test="${!empty principal.memberVO}">
			<button class="premium-item ad-chat">채팅</button>
			<button class="premium-item ad-letter">편지</button>
			<button class="premium-item ad-book" onclick="location.href='${pageContext.request.contextPath}/booking/list?group_num=${vo.group_num}'">예매</button>
			</c:if>
			<c:if test="${!empty principal.artistVO && principal.artistVO.group_name == vo.group_name}">
			<button class="premium-item ad-chat">채팅</button>
			<button class="premium-item" onclick="location.href='${pageContext.request.contextPath}/letter/artist_list?artist_num=${principal.artistVO.user_num}'">편지</button>
			<button class="premium-item ad-book" onclick="location.href='${pageContext.request.contextPath}/booking/list?group_num=${vo.group_num}'">이벤트</button>
			</c:if>
			<c:if test="${!empty principal.artistVO && principal.artistVO.group_name != vo.group_name}">
			<div class="wrong-artist-div align-center font-white font-0_8">접근 권한이 없습니다</div>
			</c:if>
			<c:if test="${empty principal.artistVO && empty principal.memberVO}">
				<div class="wrong-artist-div align-center font-white font-0_8">로그인이 필요합니다</div>
			</c:if>
		</div>
		
		<div class="a-profile font-white bold-title vertical-center">PROFILE</div>
		<div class="member-container">
			<div class="member-list">
				<c:forEach var="member" items="${members}">
					<div class="member-item" data-value="${member.user_num}">
						<img class="member-profile-img"
							src="/member/photoView2?user_num=${member.user_num}">
						<div class="align-center font-white bold-title top-5">${member.name}</div>
						<c:if test="${!empty principal}">
							<button class="membership-btn top-10"
								onclick="location.href='${pageContext.request.contextPath}/fan/selectArtist?user_num=${member.user_num}'">
								멤버십 구독</button>
							<c:if
								test="${empty member.follower_num or member.follower_num!=principal.memberVO.user_num}">
								<span style="color: white;"><b>팔로워</b> <span
									class="vertical-center follow-cnt">${member.follow_cnt}</span></span>
								<img class="output_follow" data-num="${member.user_num}"
									data-header="${_csrf.headerName}" data-token="${_csrf.token}"
									src="${pageContext.request.contextPath}/assets/images/hr2/unfollow.svg"
									style="width: 20px; height: 20px;]">
							</c:if>
							<c:if
								test="${!empty member.follower_num and member.follower_num==principal.memberVO.user_num}">
								<span style="color: white;"><b>팔로워</b> <span class="vertical-center follow-cnt">${member.follow_cnt}</span></span>
								<img class="output_follow" data-num="${member.follow_num}"
									data-unum="${member.follower_num}"
									data-header="${_csrf.headerName}" data-token="${_csrf.token}"
									src="${pageContext.request.contextPath}/assets/images/hr2/follow.svg"
									style="width: 20px; height: 20px;]">
							</c:if>
						</c:if>
					</div>
				</c:forEach>
			</div>
			<div class="member-detail font-white">
				<hr>
					<c:forEach var="member" items="${members}">
					<ul class="member-info" data-member="${member.user_num}">
						<li>
							<label>아티스트</label><span> ${member.name}</span>
						</li>
						<li>
							<label>데뷔일</label>
							<span>
								<c:if test="${!empty member.debut_date}">${member.debut_date}</c:if>
								<c:if test="${empty member.debut_date}">등록 정보 없음</c:if>
							</span>
						</li>
						
						<li>
							<hr>
							<div class="member-desc font-white vertical-center left-3">
								${member.intro}
							</div>
						</li>
					</ul>
					</c:forEach>
				
				
			</div>
		</div>
	</div>
	<div class="space-10vw"></div>
</div>
<div class="artist-main">
	<div class="artist-contents-container">
		<div class="artist-contents-title bold-title">HIGHLIGHTS</div>
			<div class="scroll-track">
				<div class="artist-contents-list vertical-center">
                <c:forEach var="video" items="${video}" varStatus="status">
                    <c:if test="${status.index < 4}">
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
						<div class="artist-contents-item shadow-effect">
                        	<div class="video-card" onclick="location.href='${pageContext.request.contextPath}videos/page?videoId=${video.videoId}&group_num=${groupNum}'">
                            	<img src="${thumbnailUrl}" alt="썸네일" />
                        	</div>
                        </div>
                    </c:if>
                </c:forEach>
                </div>
            </div>
		<div class="artist-contents-more right-align vertical-center"><a href="${pageContext.request.contextPath}videos/group?group_num=${vo.group_num}">->전체 영상</a></div>
	</div>
	<div class="artist-contents-container">
		<div class="artist-contents-title bold-title">SHOP</div>
		<div class="artist-contents-list vertical-center">
			<c:forEach items="${shops}" var="item" begin="0" end="3">
			<div class="artist-contents-item shadow-effect">
				<img src="${pageContext.request.contextPath}/assets/upload/${item.filename}">
			</div>
			</c:forEach>
		</div>
		<div class="artist-contents-more right-align vertical-center"><a href="${pageContext.request.contextPath}/item/list?user_num=${vo.group_num}">->전체 상품</a></div>
	</div>
</div>

