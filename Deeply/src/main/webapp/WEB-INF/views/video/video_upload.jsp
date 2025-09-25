<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 메인 타이틀 -->
<div class="video-upload-content">
    <section class="main-title">
        <h1>영상 업로드</h1>
    </section>

    <!-- 업로드 섹션 -->
    <section class="upload-section">
        <h2>영상 업로드</h2>
        <!-- 파일 업로드는 제거하므로, 일반 POST로 변경 -->
        <form action="upload" method="post" id="video-upload-form" novalidate>
            <!-- CSRF 토큰 -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <!-- 제목 입력 -->
            <div class="title-container">
                <input type="text" name="title" maxlength="100"
                    placeholder="제목을 입력하세요" class="upload-title" data-required="true">
            </div>

            <!-- 카테고리와 멤버십 여부 선택 -->
            <div class="selection-row">
                <div class="category-select-container"
                    style="cursor: pointer; position: relative; width: 400px;">
                    <!-- 선택된 카테고리 -->
                    <div class="selected-category" id="selected-category" onclick="toggleDropdown()">카테고리 선택</div>
                    <input type="hidden" name="categoryId" id="selected-category-input" value="">

                    <div id="dropdown-menu" class="dropdown-menu hidden">
                        <!-- 카테고리 리스트 -->
                        <div class="dropdown-item-container"></div>

                        <!-- 카테고리 추가 -->
                        <div class="add-category-container">
                            <input type="text" id="new-category-name" placeholder="카테고리 명" />
                            <button type="button" onclick="addCategory()">추가</button>
                        </div>
                    </div>
                </div>

                <!-- 멤버십 여부 선택 -->
                <div class="membership-toggle" style="margin-top: 15px;">
                    <label for="isExclusive">
                        <input type="checkbox" id="isExclusive" name="isExclusive" value="1" style="margin-right: 10px;">
                        <span>멤버십 영상</span>
                    </label>
                </div>
            </div>

            <!-- 탭 버튼 (글, 링크, 영상 설명) -->
            <div class="tabs-container">
                <div class="tab-button active" data-tab="text">
                    <span class="tab-icon"></span> 글
                </div>
                <div class="tab-button" data-tab="link">
                    <span class="tab-icon"></span> 링크
                </div>
                <div class="tab-button" data-tab="video-description">
                    <span class="tab-icon"></span> 영상 설명
                </div>
            </div>

            <!-- 탭에 따른 콘텐츠 영역 -->
            <div class="tab-content" id="tab-content">
                <!-- 1) 글 탭 -->
                <div class="tab-panel active" data-tab="text">
                    <!-- Rich Text Editor -->
                    <div class="editor-container">
                        <!-- Editable Div for CKEditor 5 -->
                        <div id="description-editor" class="description-editor" data-required="true"></div>
                        <!-- Hidden textarea to store the editor data -->
                        <textarea name="description" id="description" class="description-input hidden"></textarea>
                    </div>
                </div>

                <!-- 2) 링크 탭 -->
                <div class="tab-panel" data-tab="link">
                    <input type="url" name="mediaUrl" id="mediaUrl" placeholder="유튜브 링크를 입력하세요" data-required="true">
                </div>

                <!-- 3) 영상 설명 탭 -->
                <div class="tab-panel" data-tab="video-description">
                    <textarea name="videoDescription" id="videoDescription" placeholder="영상 설명을 입력하세요" class="upload-video-description" data-required="true"></textarea>
                </div>
            </div>

            <!-- 기능 버튼 -->
            <div class="form-actions">
                <button type="submit" class="btn-post">Post</button>
            </div>
        </form>
    </section>
</div>

<!-- 필요한 메타 태그 (CSRF 관련) -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<!-- 스크립트 로드 (헤더에서 옮김) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>

<!-- CKEditor 5 Classic Editor CDN -->
<script src="https://cdn.ckeditor.com/ckeditor5/29.1.0/classic/ckeditor.js"></script>

<!-- 외부 자바스크립트 파일들 -->
<script src="${pageContext.request.contextPath}/assets/js/category.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/editor.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/formValidation.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/tabs.js" defer></script>
