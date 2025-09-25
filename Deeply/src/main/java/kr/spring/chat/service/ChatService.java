package kr.spring.chat.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.annotations.Select;


import kr.spring.chat.vo.ChatVO;
import kr.spring.member.vo.ArtistVO;

public interface ChatService {

	/*
	 * 채팅방 관련
	 * */
	//채팅방 형성하기
	public void insertChatroom(ChatVO chvo);
	
	//로그인된 아이디 정보 채팅 유저 테이블에 넣기
	public void insertDuserInfo(ChatVO chvo);
	public void insertAuserInfo(ChatVO chvo);
	//들어가는 방 번호 select(아티스트 입장)
	public Long selectChatnum(Long auser_num);
	//들어가는 방 번호 select(유저 입장)
	//public Long selectChatnumUser(Long duser_num);
	//중간테이블에 넣기
	public void insertAuserChat(ChatVO chvo);
	public void insertDuserChat(ChatVO chvo);
	//중간테이블에 한 번만 들어갈 수 있도록 관리
	public int checkAuserCondition(Long chat_num);
	public int checkDuserCondition(Long chat_num);
	//아티스트면 chat_kind = 1
	public void updateAuserKind(Long chat_num);


	
	
	/*
	 * 메세지 관련
	 * */
	
	//메세지 DB에 메시지 등록하기
	
	//void insertMsg(ChatMsgVO chmVO);
	public Long selectChatroomNum(Long chat_user_num);
	public String selectId(Long chat_user_num);
    
	
}
