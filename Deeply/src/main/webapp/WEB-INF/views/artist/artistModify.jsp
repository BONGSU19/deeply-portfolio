<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/artistPage.js"></script>
<sec:authorize access="isAuthenticated()"><sec:authentication property="principal" var="principal" /></sec:authorize>


<div class="artist-main background-black">
	<form:form modelAttribute="agroupVO" action="modify?origin_name=${agroupVO.group_name}" id="modify_artist" enctype="multipart/form-data">
	<form:hidden path="group_num" value="${agroupVO.group_num}"/>
	
	<div class="a-mainpic align-center">
		<c:if test="${!empty agroupVO.group_photo && agroupVO.group_photo != ''}">
			<img class="img-previewer" id="previewImage" src="${pageContext.request.contextPath}/assets/upload/${agroupVO.group_photo}">
		</c:if>
		<c:if test="${empty agroupVO.group_photo || agroupVO.group_photo == ''}">
			<img class="img-previewer" id="previewImage">
		</c:if>
		<button class="amainpic-select bold-title">이미지 수정</button>
		<input type="file" name="upload" id="group_photo">
		<input type="hidden" name="group_photo" value="${agroupVO.group_photo}">
	</div>
	
	<div class="artist-detail modify-details">
		<div class="vertical-center">
			<div class="width-80">
				<div class="font-white bold-title vertical-center font-1_5">
					<form:label path="fandom_name">팬덤명</form:label>
					<form:input path="fandom_name"/>
					<form:errors path="fandom_name" cssClass="error-color"/>	
				</div>
				<div class="font-white bold-title vertical-center font-1_5">
					<form:label path="group_name">아티스트(그룹)명</form:label>
					<form:input path="group_name"/>
					<form:errors path="group_name" cssClass="error-color"/>
				</div>
			</div>
		</div>
		<div class="a-desc font-white">
			<div><form:label class="bold-title font-1_5" path="intro_desc">아티스트 상세 정보</form:label></div>
			<div><form:textarea path="intro_desc"/></div>
			<div><form:errors path="intro_desc" cssClass="error-color"/></div>
		</div>
	</div>
	<div class="modify-submit align-center">
	<form:button class="modify-submit-btn left-10">수정</form:button>
	</div>
	</form:form>
	<div class="modify-bottom-div">
	<button class="modify-cancel-btn" onclick="location.href='${pageContext.request.contextPath}/artist/detail?group_num=${agroupVO.group_num}'">목록</button>
	</div>
</div>
