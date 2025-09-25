package kr.spring.chat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.chat.dao.ChatMapper;

import kr.spring.chat.vo.ChatVO;
import kr.spring.member.vo.ArtistVO;


@Service
@Transactional
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatMapper chatMapper;
	
	@Override
	public void insertChatroom(ChatVO chvo) {
		chatMapper.insertChatroom(chvo);
		
	}

	@Override
	public void insertAuserInfo(ChatVO chvo) {
		chatMapper.insertAuserInfo(chvo);
		
	}

	@Override
	public void insertDuserInfo(ChatVO chvo) {
		chatMapper.insertDuserInfo(chvo);
		
	}
	//중간테이블에 2가지 유저정보를 정보를 업데이트 시킨다.	
	@Override
	public void insertAuserChat(ChatVO chvo) {
		chatMapper.insertAuserChat(chvo);
		
	}
	@Override
	public void insertDuserChat(ChatVO chvo) {
		chatMapper.insertDuserChat(chvo);
		
	}
	//중간테이블에 한 번만 들어갈 수 있도록!
	@Override
	public int checkAuserCondition(Long chat_num) {
		return chatMapper.checkAuserCondition(chat_num);	
	}
	
	@Override
	public int checkDuserCondition(Long chat_num) {
	
		return chatMapper.checkDuserCondition(chat_num);
	}
	
	@Override
	public Long selectChatnum(Long auser_num) {
		
		return chatMapper.selectChatnum(auser_num);
	}

	@Override
	public void updateAuserKind(Long chat_num) {
		 chatMapper.updateAuserKind(chat_num);
		
	}

	
	/*
	 * 메세지 관련 부분
	 * */
	
	
	

	@Override
	public Long selectChatroomNum(Long chat_user_num) {
		
		return chatMapper.selectChatroomNum(chat_user_num);
	}

	@Override
	public String selectId(Long chat_user_num) {
		// TODO Auto-generated method stub
		return chatMapper.selectId(chat_user_num);
	}

	
	
	

	
}


	
	



	
	

	

	
	
	
	


	

