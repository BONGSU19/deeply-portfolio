<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<div class="page-main">
	<h2>공지사항</h2>
	<hr size="3px">
	<form action="list" id="search_form" method="get">
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
			<c:if test="${principal!=null && principal.artistVO!=null}">
				<input type="button" class="grey-btn" value="글 작성" onclick="location.href='write'">
			</c:if>
		</div>   
	<c:if test="${count == 0}">
		<div class="align-right">
		</div>   
		<div class="result-display">해당 아티스트의 공지사항이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
		<table class="notice-list">
    <tbody>
        <c:forEach var="notice" items="${list}">
        <tr>
            <td class="notice-titles" id="notice-title">
                <a href="detail?notice_num=${notice.notice_num}">${notice.notice_title}</a>
            </td>
            <td class="notice-date">${notice.notice_date}</td>
            <td class="notice-name">${notice.group_name}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>
	<div class="align-center">${page}</div>
	</c:if>
</div>