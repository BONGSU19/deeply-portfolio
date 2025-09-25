<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>이메일 인증</title>
</head>
<body>
    <h2>이메일 인증</h2>
    <p>${email}으로 인증 메일을 보내드렸습니다. 이메일을 확인 후 인증 코드를 입력하세요.</p>

    <form action="verifyEmail" method="post">
        <input type="hidden" name="email" value="${email}" />
          <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <!-- 이메일과 인증 코드를 입력받는 필드 -->
        <label for="code">인증 코드:</label>
        <input type="text" id="code" name="code" required /><br>

        <button type="submit">인증</button>
    </form>
</body>
</html>