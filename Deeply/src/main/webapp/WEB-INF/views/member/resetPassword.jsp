<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정</title>
    <style>
        /* 전체 페이지 스타일 */
    
        /* 컨테이너 스타일 */
        .container {
            background-color: rgba(255, 255, 255, 0.15);
            padding: 30px 50px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
            width: 100%;
            max-width: 400px;
            margin:0 auto;
        }

        /* 제목 스타일 */
        h1 {
            margin-bottom: 20px;
            font-size: 1.8em;
            color: black;
            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
        }

        /* 입력 필드 스타일 */
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
            background-color: rgba(255, 255, 255, 0.9);
            font-size: 1em;
            box-shadow: inset 2px 2px 5px rgba(0, 0, 0, 0.2);
        }

        /* 버튼 스타일 */
        button {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 1em;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        /* 메시지 스타일 */
        p {
            margin-top: 15px;
            font-size: 1em;
            color: #ffdd57;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
        }

        /* 반응형 디자인 */
        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }

            h1 {
                font-size: 1.5em;
            }

            button {
                font-size: 0.9em;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>비밀번호 재설정</h1>
        <form action="/member/resetPassword" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="email" value="${email}">
            <label for="newPassword">새 비밀번호:</label>
            <input type="password" id="newPassword" name="newPassword" required>
            <br><br>
            <button type="submit">비밀번호 변경</button>
        </form>

        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>
    </div>
</body>
</html>
