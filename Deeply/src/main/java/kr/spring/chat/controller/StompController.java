package kr.spring.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import kr.spring.chat.vo.Message;

import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class StompController {

    private final SimpMessagingTemplate messagingTemplate;

    public StompController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 클라이언트에서 /app/chat 요청이 들어오면 처리
   


        @MessageMapping("/chat/{room}/sendMessageFromStomp")
        @SendTo("/topic/chat/{room}")
        public Message sendMessage(Message message) throws Exception {
            return message;
        }
    

    // 클라이언트에서 메시지를 받을 때, 서버에서 이를 처리한 후 다른 클라이언트들에게 메시지를 전달
    public void sendToRoom(String room,Message message) {
        messagingTemplate.convertAndSend("/topic/chat/" + room, message);
    }
}