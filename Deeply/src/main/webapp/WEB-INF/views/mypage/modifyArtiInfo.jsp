<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="page-main">
	<hr2>아티스트 정보 수정</hr2>
	<hr>
<tiles:insertAttribute name="artiInfo"/>

<!-- 회원정보 수정 시작 -->
	<form:form modelAttribute="memberVO" action="update" id="member_modify">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<form:label path="id">아이디</form:label>
				<form:input path="id" readonly="true"/>
				<form:errors path="id" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="nick_name">닉네임</form:label>
				<form:input path="nick_name" placeholder="필수입력"/>
				<form:errors path="nick_name" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="email">이메일</form:label>
				<form:input path="email" placeholder="필수입력"/>
				<form:errors path="email" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="intro">소개글</form:label>
				<form:input path="intro"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="blue-btn">저장</form:button>
			<input type="button" value="취소" class="grey-btn" onclick="location.href='myPage'">
		</div>
	</form:form>
</div>