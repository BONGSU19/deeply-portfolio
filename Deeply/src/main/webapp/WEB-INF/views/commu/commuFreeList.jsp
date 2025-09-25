<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<div class="page-main">
	<table>
		<tr>
		    <td>
		        <a class="commu-title" href="list">전체 글  </a>
		    </td>
		    <td>
		        <a class="commu-title-active" href="commuFreeList">전체게시판</a>
		    </td>
		    <td>
		        <a class="commu-title" href="commuFandomList">팬덤게시판</a>
		    </td>
		</tr>
	</table>
	<hr size="3px">
	<form action="list" id="search_form" method="get">
		<input type="hidden" name="category" value="1">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>작성자</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
					<option value="4" <c:if test="${param.keyfield == 4}">selected</c:if>>제목+내용</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword"
				                           value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
	</form>
		<div class="align-right">
				<input type="button" class="grey-btn" value="글 작성" onclick="location.href='write'" data-category="${param.c_category}">
				<input type="button" class="brt-grey-btn" value="내 글 보기" onclick="location.href='myList'">
		</div>                        
	<c:if test="${count == 0}">
		<div class="result-display">작성된 게시물이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
		<table class="commu-list">
			<c:forEach var="commu" items="${list}">
			<tr>
				<td class="commu-titles"><a href="detail?c_num=${commu.c_num}">${commu.c_title} [${commu.cre_cnt}]</a></td>
				<td class="commu-date">${commu.c_date}</td>
				<td class="commu-name">${member.nick_name}</td>
				<td class="commu-hit">${commu.c_hit}</td>
			</tr>
			</c:forEach>
		</table>
	<div class="align-center">${page}</div>
	</c:if>
</div>