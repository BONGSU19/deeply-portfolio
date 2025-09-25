package kr.spring.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import kr.spring.chat.vo.Message;


@Controller
public class ChatMessageHandler {

	@MessageMapping("/chat/{room}")
	@SendTo("/topic/chat/{room}")
	public Message sendMessage(@DestinationVariable String room, @RequestBody Message message) {
	    // 메시지를 JSON 형식으로 보내는지 확인
	    System.out.println("Sending message: " + message.toString());
	    return message;
	}
}