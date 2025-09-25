<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<!-- 메인 시작 -->
<!-- <div class="main-page"> -->

	<!-- 부트스트랩 시작 -->
	<div class="key-img">
		<div id="carouselExample" class="carousel slide">
		  <div class="carousel-inner">
		    <div class="carousel-item img1 active a-mainImg">
		      <img  src="${pageContext.request.contextPath}/assets/image_bundle/newjeans.jpg"   class="d-block" alt="...">
		    </div>
		    <div class="carousel-item img1">
		      <img src="${pageContext.request.contextPath}/assets/image_bundle/beno2.png"   class="d-block" alt="...">
		    </div>
		     <div class="carousel-item img1">
		      <img src="${pageContext.request.contextPath}/assets/image_bundle/idol.png"   class="d-block" alt="...">
		    </div>
		      <div class="carousel-item img1">
		      <img src="${pageContext.request.contextPath}/assets/image_bundle/black.jpg"   class="d-block" alt="...">
		    </div>
		
		  </div>
		  <button  class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
		  </button>
		  <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Next</span>
		  </button>
		</div>
	</div>
	<!-- 부트스트랩 끝 -->
	
	<div class="key-box">
		<div class="key-box-1">
			1개월에 6,500원으로<br> 아티스트에게 더 가까워집니다.
		</div>
		<br>
		<div class="key-box-2">
			<i class="bi bi-check-square">   아티스트 미공개 영상 </i>
			<br>
			<i class="bi bi-check-square">   1:1 채팅</i>
			<br>
			<i class="bi bi-check-square">   멤버십 전용 굿즈</i>
			<br>
			<br>
			<a class="check-square-text" href="${pageContext.request.contextPath}/artist/list"> <b>→ 아티스트 구독하기</b></a>
		</div>
	</div>



	<div class="key-box2">
		<div class="key-box2-1">
			<b>아티스트</b>와 <b>나</b> <br>우리가 함께하는 더 가까운 순간
		</div>
		
		<div class="key-box2-1-1">
		특별한 소통, 당신의 마음이 전해지고	<br>
		더 가까이에서 서로의 순간을 함께할 수 있습니다.
		</div>

		
				<!-- <div style="width:71px; text-align:center; float:right; margin-left:50px;">  -->
				<div class="key-box2-2">
				    <div class="key-box2-2-boot">
				        <i class="bi bi-chat-left-text boot2"></i>
				        <div class="key-box2-2-text"> 1:1 채팅</div>
				    </div>
				    <div class="key-box2-2-boot">
				        <i class="bi bi-envelope-paper"></i>
				        <div class="key-box2-2-text"> 편지 쓰기 </div>
				    </div>
				    <div class="key-box2-2-boot">
				        <i class="bi bi-clipboard2-check"></i>
				        <div class="key-box2-2-text"> 커뮤니티 </div>
				    </div>
				    <div class="key-box2-2-boot">
				        <i class="bi bi-film" ></i>
				        <div class="key-box2-2-text">아티스트 영상</div>
				    </div>
				</div>
	</div>
	
		
		<div class="choice">
		<div class="name">
			<div class="c-content1">
			<b style="font-size: clamp(40px, 1.2vw, 60px);">편지 쓰고 답장 받기</b>
			<br>
				<span class="choice-text">
					진심을 담아 아티스트에게 편지를 <br>
					전해보세요. <br>
					마음을 더욱 가까이 전달할 수 있습니다.
				</span>
			</div>
			<div>
			 	<img class="choice4" src="${pageContext.request.contextPath}/assets/image_bundle/main_letter3.png">
			</div>
		</div>
		<div class="name">
			<div class="c-content">
			<b style="font-size: clamp(40px, 1.2vw, 60px);">아티스트의 공연</b>
			<br>
				<span class="choice-text">
					아티스트의 무대를 감상하며<br>
					아주 특별한 순간을 함께하세요.
				</span>
			</div>
			<div>
			 	<img class="choice4" src="${pageContext.request.contextPath}/assets/image_bundle/main_perform.png">
			</div>
		</div>
		<div class="name">	
			<div class="c-content">
			<b style="font-size: clamp(40px, 1.2vw, 60px);">함께하는 커뮤니티</b>
			<br>
				<span class="choice-text">
					팬들과 다양한 이야기를 나누고<br>
					소중한 순간을 공유해 기록해보세요.
				</span>
			</div>
			<div>
			 	<img class="choice4" src="${pageContext.request.contextPath}/assets/image_bundle/main_comm.png">
			</div>
		</div>
		<div class="name">
			<div class="c-content2">
			<b style="font-size: clamp(40px, 1.2vw, 60px);">하이라이트 영상 시청</b>
			<br>
				<span class="choice-text">
					아티스트의 무대, 일상, 비하인드 영상까지! <br>
					다양한 순간을 영상으로 만나보세요.
				</span>
			</div>	
			 	<img class="choice4" src="${pageContext.request.contextPath}/assets/image_bundle/main_movie.png">
			</div>
		</div>
	</div>
<!-- </div> -->

	<div class="shop">
		<div class="shop-1"> SHOP</div>
		<hr class="custom-hr2" noshade="noshade">
		<div class="shop-2">new</div>
    <c:set var="loop_flag" value="true"/>
    <div class="main-items">
        <div class="value-list">
            <c:forEach items="${list}" var="item" varStatus="status">
                <!-- 4개의 아이템만 출력 -->
                <c:if test="${status.index < 4}">
                <div class="item-cards">	
                    <a href="${pageContext.request.contextPath}/item/detail?item_num=${item.item_num}">
                        <img src="${pageContext.request.contextPath}/assets/upload/${item.filename}" class="item-img" >
                    </a>
                    <hr class="custom-hr3" noshade="noshade" width="100%">
                    <span class="item-name list-text">${item.item_name}</span>
                    <span class="item-price list-price">${item.item_price}</span>
                 </div>
                </c:if>
            </c:forEach>
        </div>
     </div>
 	<hr class="custom-hr2" noshade="noshade" >
   <a class="arrow" href="${pageContext.request.contextPath}/item/main">전체보기 →</a>
	<div class="empty-div"></div>
	</div>
	
	<div class="shop-3"> ARTIST </div>
		<div class="artist">
			<c:forEach var="group" items="${groups}" varStatus="status">
			<c:if test="${status.index < 20}">
				<div class="item-cards2">
					<span><a href="${pageContext.request.contextPath}/artist/list">
						<img class="alist-img" src="${pageContext.request.contextPath}/assets/upload/${group.group_photo}"></a></span>
							<span class="artist-text">${group.group_name}</span>
				</div>
			</c:if>
		</c:forEach>
	</div>
<!-- 메인 끝 -->



