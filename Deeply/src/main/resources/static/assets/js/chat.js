let username = null;
let currentRoom = null;

$(document).ready(function () {
    // 자동으로 유저 이름을 가져오기
    fetchUsername();

    // 방 목록 자동 가져오기
    fetchRooms();

    // 메시지 전송
    $('#btnSend').on('click', function (evt) {
        evt.preventDefault();
        let msg = $('input#msg').val();

        if (!msg.trim()) {
            alert("Please enter a message.");
            return;
        }

        // 보낸 메시지 화면에 표시
        $('#message-list').append(`<div class="chat-bubble user"><b>${username}:</b> ${msg}</div>`);
        $('input#msg').val(''); // 입력창 초기화

        // STOMP 서버에 메시지 전송
        if (socket && isStomp) {
            socket.send(`/chat/${currentRoom}`, {}, JSON.stringify({ user: username, msg: msg, status: 'sent' }));
        }
    });

    // 채팅방 접속
    $('#btnJoinRoom').on('click', function (evt) {
        evt.preventDefault();

        if (!currentRoom) {
            alert("Please select a room.");
            return;
        }

        console.log("current-room: " + currentRoom);

        // 방 선택 UI 숨기고 채팅 UI 보여주기
        $('.room-container').hide();
        $('#chatContainer').show();

        connectStomp(currentRoom); // STOMP 연결
    });
});

var socket = null;
var isStomp = false;

// 자동으로 유저 이름을 가져오는 AJAX 함수
function fetchUsername() {
    $.ajax({
        url: '/chat/getUsername', // 서버에서 유저 이름을 가져오는 엔드포인트
        method: 'GET',
        success: function (data) {
            console.log("Fetched username response:", data); // 서버 응답 확인
            if (data.result === 'success') {
                username = data.id;

                if (!username) {
                    alert("Username is empty. Please try again.");
                    return;
                }

                $('.login-container').hide();
                $('.room-container').show(); // 방 선택 UI 보이기
                console.log("Username successfully set to:", username);
            } else {
                alert("Failed to fetch username. Please try again.");
            }
        },
        error: function (error) {
            console.error("Error fetching username:", error);
            alert("Error fetching username. Please contact support.");
        }
    });
}

// 방 목록을 자동으로 가져오는 AJAX 함수
function fetchRooms() {
    $.ajax({
        url: '/chat/room', // 서버의 GET 요청 URL
        method: 'GET', // HTTP 메소드
        dataType: 'json', // 서버로부터 응답 받을 데이터 형식
        success: function (data) {
            // 성공적으로 데이터를 받았을 때
            if (data.result === 'success') {
                console.log("Chat room data fetched successfully.");
                
                // 서버에서 방이 1개일 때
                if (data.room) {
                    // 하나의 방 정보가 있는 경우
                    currentRoom = data.room; // 방 번호 설정
                    $('#roomList').html(''); // 이전 내용 삭제
                    $('#roomList').append(`<div>${data.room}</div>`); // 방 이름 출력

                    // 방 선택 UI 자동으로 보여주기
                    $('#btnJoinRoom').show(); // 'Join Room' 버튼 표시

                    // 방에 자동 입장
                    $('.room-container').hide();
                    $('#chatContainer').show();
                    connectStomp(currentRoom); // STOMP 연결
                }
            } else if (data.result === 'logout') {
                alert("You are logged out. Please log in again.");
                // 로그인 페이지로 리디렉션 (필요시)
                window.location.href = '/login';
            }
        },
        error: function (error) {
            // 요청 실패 시 에러 처리
            console.error("Error fetching rooms:", error);
            alert("Error fetching rooms. Please try again.");
        }
    });
}

function connectStomp(room) {
    var sock = new SockJS("/stompChat"); // STOMP 엔드포인트
    var client = Stomp.over(sock);
    isStomp = true;
    socket = client;

    client.connect({}, function () {
        console.log("Connected to /stompChat!");

        // 방에 따라 동적으로 토픽 구독
        const topic = `/topic/chat/${room}`;
        console.log("Subscribing to:", topic);

        client.subscribe(topic, function (event) {
            try {
                let message = JSON.parse(event.body); // 메시지 파싱
                console.log("Received message:", message);

                if (message && message.user && message.msg) {  // 메시지가 예상대로 오는지 확인
                    if (message.user !== username) {
                        // 받은 메시지를 화면에 표시 (received 상태)
                        $('#message-list').append(`<div class="chat-bubble received"><b>${message.user}:</b> ${message.msg}</div>`);

                        // 자동 스크롤
                        var chatContainer = document.getElementById('chatContainer');
                        chatContainer.scrollTop = chatContainer.scrollHeight;
                    } else {
                        console.log("Own message ignored:", message);
                    }
                } else {
                    console.error("Invalid message format:", message);
                }
            } catch (error) {
                console.error("Error parsing message:", error, event.body);
            }
        });
    }, function (error) {
        console.error("STOMP connection failed:", error);  // 연결 실패 시 오류 출력
    });
}

// 메시지 전송 코드 수정 (보내는 메시지에 status 'sent' 추가)
$('#btnSend').on('click', function (evt) {
    evt.preventDefault();
    let msg = $('input#msg').val();

    if (!msg.trim()) {
        alert("Please enter a message.");
        return;
    }

    // 보낸 메시지 화면에 표시
    $('#message-list').append(`<div class="chat-bubble user"><b>${username}:</b> ${msg}</div>`);
    $('input#msg').val(''); // 입력창 초기화

    // STOMP 서버에 메시지 전송
    if (socket && isStomp) {
        socket.send(`/chat/${currentRoom}`, {}, JSON.stringify({ user: username, msg: msg, status: 'sent' }));
    }
});
