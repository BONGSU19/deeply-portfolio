<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>결과</title>
    <style>
        /* 전체 페이지 스타일 */
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: #ffffff;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            overflow: hidden;
        }

        /* 콘텐츠 박스 스타일 */
        .container {
            background-color: rgba(255, 255, 255, 0.1);
            padding: 30px 50px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
            max-width: 600px;
        }

        /* 제목 스타일 */
        h2 {
            font-size: 2em;
            margin-bottom: 20px;
            color: #ffdd57;
            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
        }

        /* 메시지 텍스트 스타일 */
        p {
            font-size: 1.2em;
            line-height: 1.6;
            color: #e3f2fd;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
        }

        /* 반응형 조정 */
        @media (max-width: 768px) {
            .container {
                padding: 20px;
                width: 90%;
            }

            h2 {
                font-size: 1.8em;
            }

            p {
                font-size: 1em;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>${message}</h2>
        <p>${message}</p>
    </div>
</body>
</html>
