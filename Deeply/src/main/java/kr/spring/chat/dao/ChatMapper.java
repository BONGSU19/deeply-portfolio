package kr.spring.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import kr.spring.chat.vo.ChatVO;
import kr.spring.member.vo.ArtistVO;

@Mapper
public interface ChatMapper {
	/*****************
	채팅방 형성
	*******************/
	//아티스트가 (채팅방 들어가기)를 누르면 채팅방 정보가 새롭게 insert된다.
	public void insertChatroom(ChatVO chvo);
	
	//유저정보(아티스트, 유료유저) insert 
	public void insertAuserInfo(ChatVO chvo);
	public void insertDuserInfo(ChatVO chvo);
	
	
	//이미 형성되어서 들어갈 방 SELECT 하기(먼저 채팅방 번호를 구하는 method를 구현한다.)
	@Select("SELECT chat_num FROM (SELECT chat_num FROM chatroom WHERE auser_num=#{auser_num} ORDER BY chat_time DESC) WHERE ROWNUM=1")
	public Long selectChatnum(Long auser_num);
	
	//@Select("SELECT chat_num FROM (SELECT chat_num FROM chatroom WHERE chat=#{auser_num} ORDER BY chat_time DESC) WHERE ROWNUM=1")
	//ipublic Long selectChatnum(Long auser_num);
	
	
	//@Update("UPDATE chat_room SET chat_status=0 WHERE chat_num=#{chat_num}")
	//@Select("SELECT fan_artist FROM FAN WHERE user_num=#{duser_num}")
	//public Long selectAusernum(Long duser_num);
	
	//중간테이블에 2가지 유저정보를 정보를 업데이트 시킨다.	
	public void insertAuserChat(ChatVO chvo);
	public void insertDuserChat(ChatVO chvo);
	//중간테이블에 한 번만 들어갈 수 있도록!
	@Select("SELECT COUNT(chat_user_num) FROM chat WHERE chat_num = #{chat_num} AND chat_kind=1")
	public int checkAuserCondition(Long chat_num);
	@Select("SELECT COUNT(chat_user_num) FROM chat WHERE chat_num = #{chat_num} AND chat_kind=0")
	public int checkDuserCondition(Long chat_num);
	
	
	//아티스트면 유저 종류를 1로 업데이트 시킨다.
	@Update("UPDATE chat SET chat_kind=1 WHERE chat_num=#{chat_num}")
	public int updateAuserKind(Long chat_num);
	
	


	/*****************
	메세지 관련
	*******************/
	
	//메세지 주고 받기
	//public void insertMsg(ChatMsgVO chmVO);
	@Select("SELECT chat_num FROM(SELECT chat_num FROM chat WHERE chat_user_num = #{chat_user_num} ORDER BY chat_num DESC) WHERE ROWNUM=1")
	public Long selectChatroomNum(Long chat_user_num);
	@Select("SELECT chat_id FROM(SELECT chat_id FROM chat WHERE chat_user_num = #{chat_user_num} ORDER BY chat_num DESC) WHERE ROWNUM=1")
	public String selectId(Long chat_user_num);
	 // 특정 채팅방의 메시지를 가져오는 메서드
    

}
