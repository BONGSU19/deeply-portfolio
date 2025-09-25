<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/uploadAdapter.js"></script>
<<style>
    html, body {
        margin: 0;
        padding: 0;
        height: 100%;
    }

    body {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        min-height: 100vh;
    }

    #content-wrapper {
        flex-grow: 1;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    form#notice_register {
        width: 100%;
        max-width: 1000px;
        padding: 20px;
        border: 1px solid #000000;
        margin: 20px;
    }

    ul.notice-t {
        list-style: none;
        padding: 0;
    }

    li.notice-write {
        margin-bottom: 15px;
    }

    li.notice-write form\:input,
    li.notice-write form\:textarea,
    li.notice-write input[type="file"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    li.notice-write form\:label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    /* notice_title 입력 필드 너비를 더 넓게 설정 */
    input#notice_title {
        width: calc(100% - 20px);
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

<div class="page-main-write">
    <h2>공지사항</h2>
    <hr size="3">
    <div id="content-wrapper">
        <form:form modelAttribute="noticeVO" action="write" id="notice_register" enctype="multipart/form-data">
            <form:errors element="div" cssClass="error-color"/>
            <ul class="notice-t">
                <li class="notice-write">
                    <form:input path="notice_title" id="notice_title" placeholder="제목(30자 이내)"/>
                    <form:errors path="notice_title" cssClass="error-color"/>
                </li>
                <li class="notice-write">
                    <form:textarea path="notice_content"/>
                    <form:errors path="notice_content" cssClass="error-color"/>
                    <%-- CKEditor 셋팅 --%>
                    <script>
                        function MyCustomUploadAdapterPlugin(editor){
                            editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
                                return new UploadAdapter(loader);
                            }
                        }
                        ClassicEditor
                            .create(document.querySelector('#notice_content'),{
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
                <li class="notice-write">
                    <form:label path="upload">파일 첨부</form:label>
                    <input type="file" name="upload" id="upload">
                </li>
            </ul>
            <div style="text-align:center;">
                <form:button class="grey-btn">등록</form:button>
                <input type="button" value="이전으로" class="grey-btn" onclick="location.href='list'">
            </div>
        </form:form>
    </div>
</div>