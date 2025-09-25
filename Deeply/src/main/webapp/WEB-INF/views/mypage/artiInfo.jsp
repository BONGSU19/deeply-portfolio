<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- 마이페이지 시작 -->
<div class="page-main">
	<div class="artiInfo">
		<img src="${pageContext.request.contextPath}/" width="100"
			height="100" class="my-photo" style="item-align: center;">
		<table class="myTable">
			<tr>
				<td class="align-center"><b>아이디 </b>${artiInfo.id}</td>
				<td class="align-center"><b>닉네임 </b>${artiInfo.group_name} ${artiInfo.name}</td>
			</tr>
			<tr>
				<td class="align-center"><b>이메일 </b>${artiInfo.email}</td>
			</tr>
			<tr>
				<td class="align-center"><b>소개글 </b>${artiInfo.intro}</td>
			</tr>
		</table>
	</div>
</div>
