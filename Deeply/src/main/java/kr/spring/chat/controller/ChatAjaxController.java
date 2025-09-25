package kr.spring.chat.controller;


import kr.spring.chat.service.ChatService;

import kr.spring.chat.vo.ChatVO;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatAjaxController {

    @Autowired
    private ChatService chatService;
    
    
    @GetMapping("/room")
    @ResponseBody
    public Map<String,Object> searchRoom(
	        ChatVO chatvo,
	        @AuthenticationPrincipal
	        PrincipalDetails principal){

    	
    	Map<String,Object> mapJson = 
				new HashMap<String,Object>();
    	
    	try {
    	    // MemberVO가 null인지 먼저 체크
    	    if (principal.getMemberVO() != null && principal.getMemberVO().getUser_num() != null) {
    	        Long chat_user_num = principal.getMemberVO().getUser_num();
    	        Long room = chatService.selectChatroomNum(chat_user_num);
    	        log.debug("<<채팅방 자동 형성>> : " + chat_user_num);
    	        log.debug("<<채팅방 자동 형성>> : " + room);
    	        mapJson.put("room", room);
    	        mapJson.put("result", "success");
    	    } else if (principal.getArtistVO() != null && principal.getArtistVO().getUser_num() != null) {
    	        // ArtistVO가 null이 아니고, 그 안의 User_num이 null이 아닌지 체크
    	        ArtistVO artistVO = principal.getArtistVO();
    	        Long chat_user_num = artistVO.getUser_num();
    	        log.debug("<<채팅방 자동 형성>> : " + chat_user_num);
    	        Long room = chatService.selectChatroomNum(chat_user_num);
    	        mapJson.put("room", room);
    	        mapJson.put("result", "success");
    	    } else {
    	        // 두 객체가 모두 null일 경우
    	        mapJson.put("result", "logout");
    	    }
    	} catch (Exception e) {
    	    // 예외 처리
    	    mapJson.put("result", "error");
    	    log.error("Error occurred: ", e);
    	}
    	return mapJson;

    	
    }
    
    
    @GetMapping("/getUsername")
    @ResponseBody
    public Map<String,Object> searcId(
            ChatVO chatvo,
            @AuthenticationPrincipal
            PrincipalDetails principal){
        
        log.debug("<<게시판 좋아요 - 등록/삭제>> : " + (principal.getMemberVO() != null ? principal.getMemberVO().getUser_num() : "null"));

        Map<String,Object> mapJson = new HashMap<String,Object>();

        try {
            // MemberVO가 null인지 체크
            if (principal.getMemberVO() != null && principal.getMemberVO().getUser_num() != null) {
                Long chat_user_num = principal.getMemberVO().getUser_num();
                String id = chatService.selectId(chat_user_num);
                mapJson.put("id", id);
                log.debug("<<채팅방 아이디 자동 부여>> : " + id);
                mapJson.put("result", "success");
            } 
            // MemberVO가 null인 경우, ArtistVO를 확인
            else if (principal.getArtistVO() != null && principal.getArtistVO().getUser_num() != null) {
                ArtistVO artistVO = principal.getArtistVO();
                Long chat_user_num = artistVO.getUser_num();
                String id = chatService.selectId(chat_user_num);
                mapJson.put("id", id);
                log.debug("<<채팅방 자동 형성>> : " + chat_user_num);
                mapJson.put("result", "success");
            } 
            // 두 객체가 모두 null인 경우
            else {
                mapJson.put("result", "logout");
            }
        } catch (Exception e) {
            // 예외 발생 시 처리
            mapJson.put("result", "error");
            log.error("Error occurred while retrieving user ID: ", e);
        }

    	
		return mapJson;
    	
    }
    
    
}
