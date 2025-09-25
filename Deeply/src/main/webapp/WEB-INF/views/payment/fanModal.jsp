<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<style>

.modal {
	display: none;
	position: fixed;
	z-index: 1000;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
	background-color: white;
	margin: 10% auto;
	padding: 10px;
	border: 4px solid #00A2CF;
	border-radius: 30px;
	width: 900px;
	text-align: center;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	position: relative;
}

.modal-content h2 {
	color: #333;
}

.modal-content button {
	margin: 3px;
	padding: 8px 20px;
	border: none;
	border-radius: 5px;
	background-color: #00A2CF;
	color: white;
	cursor: pointer;
	font-size: 14px;
	font-weight: bold;
}

.modal-content button.blue-close-btn{
	font-size: 20px;
	color: #00A2CF;
	margin: 0;
	padding: 0;
	border: none;
	border-radius: 0;
	background-color: #FFFFFF;
	cursor: pointer;
	font-weight: bold;
	position: absolute; /* 절대 위치 */
    top: 20px;   /* 상단으로부터의 거리 */
    right: 25px;
}

.modal-content button.cancel {
	background-color: #6c757d;
}
</style>
</head>
	<div id="token_info" data-header="${_csrf.headerName}" data-token="${_csrf.token}"></div>
	<div id="joinFan" class="modal">
		<div class="modal-content">
			<h2 style="color:#00A2CF; margin:20px 0 30px 0;">멤버십을 가입하시겠습니까?</h2>
			<button onclick="closeModal('joinFan')" class="blue-close-btn">X</button>
			<div style="text-align:left; padding: 0 0 0 80px; font-size:16px;">
				<p><b>다음 결제일</b> ${fan_end}</p>
				<p><b>주의사항</b> 다음 결제일까지 해지하지 않을 시 매달 자동 갱신되며, 결제 후 환불은 불가합니다.</p>
			</div>
			<button id="joinForm" data-num="${userNum}" data-arti="${artist.user_num}" data-bal="${user_bal}">가입</button>
			<button class="cancel" onclick="closeModal('joinFan')">취소</button>
		</div>
	</div>
	
	<div id="successJoin" class="modal">
		<div class="modal-content">
			<button onclick="closeModal('successJoin')" class="blue-close-btn">X</button>
			<c:if test="${artist.group_name == artist.name}">
				<h2 class="fan-title">
					아티스트 <span class="blue-artiname">${artist.name}</span>님의 멤버십
				</h2>
				<h2>멤버십 가입이 완료되었습니다.</h2>
			</c:if>
			<c:if test="${artist.group_name != artist.name}">
				<h2 class="fan-title">
					아티스트 <span class="blue-artiname">${artist.group_name} ${artist.name}</span>님의 멤버십
				</h2>
				<h2>멤버십 가입이 완료되었습니다.</h2>
			</c:if>
		</div>
	</div>

	<div id="removeFan" class="modal">
		<div class="modal-content">
			<h2 style="color:#00A2CF; margin:20px 0 30px 0;">멤버십을 해지하시겠습니까?</h2>
			<button onclick="closeModal('removeFan')" class="blue-close-btn">X</button>
			<div style="text-align:left; padding: 0 0 0 80px; font-size:16px;">
				<p><b>다음 결제일</b> ${fan_end}</p>
				<p><b>주의사항</b> 해지시에도 다음 결제일 이전까지 멤버십 사용이 가능하며 남은 기간에 비례해 환불하는 것은 불가능합니다.</p>
			</div>
			<button id="removeForm" data-num="${userNum}" data-arti="${artist.user_num}" data-status="${db_fan.fan_status}" data-end="${fan_end}">해지</button>
			<button id="deleteForm" data-num="${userNum}" data-arti="${artist.user_num}" data-status="${db_fan.fan_status}">즉시해지</button>
			<button class="cancel" onclick="closeModal('removeFan')">취소</button>
		</div>
	</div>

	<div id="successRemove" class="modal">
		<div class="modal-content">
			<button onclick="closeModal('successRemove')" class="blue-close-btn">X</button>
			<c:if test="${artist.group_name == artist.name}">
				<h2 class="fan-title">
					아티스트 <span class="blue-artiname">${artist.name}</span>님의 멤버십
				</h2>
				<h2>멤버십 해지가 완료되었습니다.</h2>
			</c:if>
			<c:if test="${artist.group_name != artist.name}">
				<h2 class="fan-title">
					아티스트 <span class="blue-artiname">${artist.group_name} ${artist.name}</span>님의 멤버십
				</h2>
				<h2>멤버십 해지가 완료되었습니다.</h2>
			</c:if>
		</div>
	</div>