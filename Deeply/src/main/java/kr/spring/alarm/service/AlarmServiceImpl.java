package kr.spring.alarm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.alarm.dao.AlarmMapper;
import kr.spring.alarm.vo.AlarmVO;
import kr.spring.payment.vo.FanVO;

@Service
@Transactional
public class AlarmServiceImpl implements AlarmService{

	@Autowired
	AlarmMapper alarmMapper;
	//답장 관련
	
	@Override
	public List<AlarmVO> selectInfo(Long user_num) {
		// TODO Auto-generated method stub
		return alarmMapper.selectInfo(user_num);
	}

	

	
	@Override
	public void deleteReplyAlarm() {
		alarmMapper.deleteReplyAlarm();
		
	}
	

	@Override
	public void insertReplyAlarm(AlarmVO alvo) {
		
		alarmMapper.insertReplyAlarm(alvo);
		
	}
	//공연 관련
	@Override
	public int selectRowCount(Long user_num) {
		
		 return alarmMapper.selectRowCount(user_num);
	}
	@Override
	public List<Long> serchArtisList(Long user_num) {
		 return alarmMapper.serchArtisList(user_num);
	}
	@Override
	public Long serchGroupNum(Long artist_num) {
		
		return alarmMapper.serchGroupNum(artist_num);
	}
	@Override
	public void insertPerformAlarm(AlarmVO alvo) {
		alarmMapper.insertPerformAlarm(alvo);
		
	}





	
}
