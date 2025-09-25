<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/customjs.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/shop.js"></script>
<script type="text/javascript">
	$(function(){
		$('#item_description').summernote({
	        height: 300
	      });
	});
</script>

<div class="write-main">
	<div class="writecontent-container item-container">
        <form:form modelAttribute="item" method="post" action="update" enctype="multipart/form-data">
	        	<form:hidden path="item_num" value="${item.item_num}"/>
	        	<form:hidden path="user_num" value="${item.user_num}"/>
		            <div class="button-right">
						<form:button  class="box-button2">수정</form:button>
						<input type="button" class="box-button2" value="취소" onclick="location.href='/item/list?user_num=${item.user_num}'" />
					</div>
					
					<!-- 1. 사진 업로드 영역 -->
            		<div class="write-info-section">
            			<div class="write-info1">
            				<c:if test="${item.filename != null}">
                   				<button class="upload-btn" id="upload_btn">파일 업로드</button>
                   				 <input type="file" class="file-submit" name="upload" id="upload" multiple>
    							<img src="${pageContext.request.contextPath}/assets/upload/${item.filename}" class="items-img" alt="상품 이미지" />
							</c:if>
            			</div>
            			
            			<!-- 2. 상품 정보 입력 영역 -->
                		<div class="write-info2">
	                		<form:label path="item_name">상품 등급</form:label>
	                    	<button class="item-premium-btn" data-num="0">일반 상품</button>
	                    	<button class="item-premium-btn" data-num="1">프리미엄 상품</button>
	                   		 <form:hidden path="category" id="category_val"/>
	                   		 <form:errors path="item_name" cssClass="error-color" />
                			<ul>
				                <li>
				                    <form:label path="item_name">상품명</form:label>
				                    <form:input type="text" maxlength="20" path="item_name" />
				                    <form:errors path="item_name" cssClass="error-color" />
				                </li>
				                <li>
				                    <form:label path="item_price">가격</form:label>
				                    <form:input type="number" min="1" max="9999999" id="item_price" path="item_price" placeholder="가격을 입력하세요." />
				                    <form:errors path="item_price" cssClass="error-color" />
				                </li>
				                <li>
				                    <form:label path="item_stock">판매 수량</form:label>
				                    <form:input type="number" min="1" max="9999999" path="item_stock" placeholder="판매 수량을 입력하세요." />
				                    <form:errors path="item_stock" cssClass="error-color" />
				                </li>
                			</ul>
                		</div>
            		</div>
            	<!-- 3. 상품 설명 영역 -->
           		 <div class="write-info3">
                    <form:textarea path="item_description" placeholder="상품 설명을 입력하세요."/>
                    <form:errors path="item_description" cssClass="error-color" />
        		</div>
        </form:form>
    
    

    <!-- 반품/교환 규정 포함 -->
    <div class="item-policy">
   			<div class="policy-title">반품/교환 규정</div>
     		   <%@ include file="returnPolicy.jsp" %> 
  		  </div>
    </div>
</div>