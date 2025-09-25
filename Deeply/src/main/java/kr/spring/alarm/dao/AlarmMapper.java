package kr.spring.alarm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.alarm.vo.AlarmVO;
import kr.spring.payment.vo.FanVO;

@Mapper
public interface AlarmMapper {
	
	/***************
	 * 아이템,채팅, 공연, 답장 알람 테이블에 insert하기
	 * *************/
	
	//읽어들일 알람정보 SELECT 하기
	@Select("SELECT * FROM alarm WHERE user_num=#{user_num}")
	public List<AlarmVO> selectInfo(Long user_num);
	
	//@Select("SELECT name FROM auser_detail WHERE user_num=#{user_num}")
	
	//우선 DELETE를 시키고 다시 insert하는 식으로!!
	
	//답장 알람 테이블에 insert
	@Delete("DELETE FROM alarm WHERE al_kind=3")
	public void deleteReplyAlarm();
	public void insertReplyAlarm(AlarmVO alvo);
	
	//공연정보(개인 구독-> 매핑되는 그룹 찾기)
	public int selectRowCount(Long user_num);
	public List<Long> serchArtisList(Long user_num);
	public Long serchGroupNum(Long artist_num);
	public void insertPerformAlarm(AlarmVO alvo);
	
	
	
	
	

}