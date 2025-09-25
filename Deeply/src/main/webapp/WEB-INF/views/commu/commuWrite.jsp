<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/uploadAdapter.js"></script>
<style>
    html, body {
        margin: 0;
        padding: 0;
        height: 100%; /* 뷰포트 높이를 기준으로 설정 */
    }

    body {
        display: flex;
        flex-direction: column;
        justify-content: space-between; /* 헤더와 푸터를 페이지 상하단에 고정 */
        min-height: 100vh; /* 뷰포트 전체 높이에 맞추기 */
    }

    #content-wrapper {
        flex-grow: 1; /* 헤더와 푸터 사이의 공간을 차지 */
        display: flex;
        justify-content: center; /* 콘텐츠를 가로로 중앙 정렬 */
        align-items: center; /* 콘텐츠를 세로로 중앙 정렬 */
    }

    form#commu_register {
        width: 100%;
        max-width: 1000px; /* 최대 너비 설정 */
        padding: 20px;
        border: 1px solid #000000;
        margin: 20px; /* 상하좌우 여백 */
    }

    ul.commu-t {
        list-style: none;
        padding: 0;
    }

    li.commu-write {
        margin-bottom: 15px;
    }

    li.commu-write form\\:input,
    li.commu-write form\\:textarea,
    li.commu-write input[type="file"] {
        width: 100%; /* 입력 필드 너비를 전체 너비로 설정 */
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    li.commu-write form\\:label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }
	
	/* c_title 입력 필드 너비를 더 넓게 설정 */
    input#c_title {
        width: calc(100% - 20px); /* 양쪽 패딩을 고려한 너비 조정 */
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
	
    .grey-btn {
        background-color: #666;
        color: white;
        border: none;
        padding: 10px 20px;
        margin: 5px;
        border-radius: 5px;
        cursor: pointer;
    }

    .grey-btn:hover {
        background-color: #555;
    }

    div[style="text-align:center;"] {
        text-align: center;
    }
</style>
<body>
<div class="page-main-write">
	<c:if test="${c_category==1}">
		<h2>전체게시판</h2>
	</c:if>
	<c:if test="${c_category==2}">
		<h2>팬덤게시판></h2>
	</c:if>
	<div id="content-wrapper">
    <form:form modelAttribute="commuVO" action="write"
        id="commu_register" enctype="multipart/form-data">
		<form:errors element="div" cssClass="error-color"/>
		<input type="hidden" value="${param.c_category}">
		<ul class="commu-t">
			<li class="commu-write">
				<form:input path="c_title" placeholder="제목(30자 이내)"/>
				<form:errors path="c_title" cssClass="error-color"/>
			</li>
			<li class="commu-write">
				<form:textarea path="c_content"/>
				<form:errors path="c_content" cssClass="error-color"/>
				<%-- CKEditor 셋팅 --%>
				<script>
					function MyCustomUploadAdapterPlugin(editor){
						editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
							return new UploadAdapter(loader);
						}
					}
					ClassicEditor
						.create(document.querySelector('#c_content'),{
							extraPlugins:[MyCustomUploadAdapterPlugin]
						})
						.then(editor => {
							window.editor = editor;
						})
						.catch(error => {
							console.error(error);
						});
				</script>
			</li>
			<li class="commu-write">
				<form:label path="upload">파일 첨부</form:label>
				<input type="file" name="upload" id="upload">
			</li>
		</ul> 
		<div style="text-align:center;">
			<form:button class="grey-btn">등록</form:button>
			<input type="button" value="이전으로" class="grey-btn"
			             onclick="location.href='list'">
		</div> 
	</form:form>
	</div>
</div>
</body>