package kr.spring.alarm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.alarm.vo.AlarmVO;
import kr.spring.payment.vo.FanVO;


@Service
@Transactional
public interface AlarmService {
	
	public List<AlarmVO> selectInfo(Long user_num);
	
	public void deleteReplyAlarm();
	public void insertReplyAlarm(AlarmVO alvo);
	

	public int selectRowCount(Long user_num);
	public List<Long> serchArtisList(Long user_num);
	public Long serchGroupNum(Long artist_num);
	public void insertPerformAlarm(AlarmVO alvo);

}
