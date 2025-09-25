<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/navigation.css">
 		
<style>
        /* 네비게이션 메뉴를 오른쪽 하단에 고정 */
        .nav-menu {
            position: fixed;
            bottom: 20px; /* 화면 하단에서 20px만큼 위로 위치 */
            right: 20px;  /* 화면 오른쪽에서 20px만큼 떨어짐 */
            list-style: none;
            margin: 0;
            padding: 0;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3); /* 테두리 그림자 추가 */
            border-radius: 5px; /* 버튼에 둥근 모서리 추가 */
            background-color: #fff; /* 흰색 배경 */
        }

        .nav-menu li {
            margin: 0;
        }
        
        .nav-menu a img {
            width: 2vw;
            height: 2vx;
        }

        .nav-menu a {
            display: block;
            background-color: #fff; /* 흰색 배경 */
            padding: 10px;
            border-radius: 5px; /* 링크에 둥근 모서리 추가 */
            color: #333; /* 링크 텍스트 색상 */
            text-decoration: none; /* 링크의 밑줄 제거 */
        }

        .nav-menu a:hover {
            background-color: #f0f0f0; /* hover 시 배경색 */
        }

        .alarm-button {
            cursor: pointer;
        }
    </style>

<nav>
    <ul class="nav-menu">
        <li>
            <a href="${pageContext.request.contextPath}/chatbot/list">
                <img src="${pageContext.request.contextPath}/assets/image_bundle/alarm.png" alt="알람 버튼" class="alarm-button">
            </a>
        </li>
    </ul>
</nav>