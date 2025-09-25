<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 게시판 글 수정 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/uploadAdapter.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hr2.css">
<div class="page-main-write">
	<c:if test="${c_category==1}">
		<h2>전체게시판</h2>
	</c:if>
	<c:if test="${c_category==2}">
		<h2>팬덤게시판></h2>
	</c:if>
	<form:form modelAttribute="commuVO" action="write"
	  id="commu_register" enctype="multipart/form-data">
	  	<form:hidden path="c_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<form:input path="c_title" placeholder="제목(30자 이내)"/>
				<form:errors path="c_title" cssClass="error-color"/>
			</li>
			<li>
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
			<li>
				<form:label path="upload">파일업로드</form:label>
				<input type="c_file" name="upload" id="upload">
				<c:if test="${!empty commuVO.c_file}">
				<div id="file_detail">
					(${commuVO.c_file})파일이 등록되어 있습니다.
					<input type="button" value="파일 삭제" id="file_del">
				</div>
				<script type="text/javascript">
				$(function(){
					$('#file_del').click(function(){
						const choice = confirm('삭제하시겠습니까?');
						if(choice){
							//서버와 통신
							$.ajax({
								url:'deleteFile',
								data:{c_num:${commuVO.c_num}},
								type:'get',
								dataType:'json',
								success:function(param){
									if(param.result == 'logout'){
										alert('로그인 후 사용하세요');
									}else if(param.result == 'wrongAccess'){
										alert('잘못된 접속입니다.');
									}else if(param.result == 'success'){
										$('#file_detail').hide();
									}else{
										alert('파일 삭제 오류 발생');
									}
								},
								error:function(){
									alert('네트워크 오류 발생');
								}
							});
						}
					});
				});
				</script>
				</c:if>
			</li>
		</ul> 
		<div class="align-center">
			<form:button>수정</form:button>
			<input type="button" value="이전으로"
			   onclick="location.href='list'">
		</div> 
	</form:form>
</div>
<!-- 게시판 글쓰기 끝 -->




