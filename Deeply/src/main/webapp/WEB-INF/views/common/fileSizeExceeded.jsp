<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>최대 업로드 사이즈 초과</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/common.css">
</head>
<body>
<div class="page-one">
	<h2>최대 업로드 사이즈 초과</h2>
	<div class="result-display">
		<div class="align-center">
			파일의 크기가 최대 업로드 크기 제한을 초과했습니다.
			<p>
			<input type="button" value="이동" onclick="history.go(-1)">
		</div>
	</div>
</div>
</body>
</html>