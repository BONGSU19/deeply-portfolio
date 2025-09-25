package kr.spring.payment.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.payment.dao.FanMapper;
import kr.spring.payment.vo.FanVO;

@Service
@Transactional
public class FanServiceImpl implements FanService{
	@Autowired
	private FanMapper fanMapper;
	
	@Override
	public Long getUser_bal(Long user_num) {
		return fanMapper.getUser_bal(user_num);
	}

	@Override
	public FanVO selectArtist(Long user_num) {
		return fanMapper.selectArtist(user_num);
	}

	@Override
	public ArtistVO artiInfo(Long user_num) {
		return fanMapper.artiInfo(user_num);
	}

	@Override
	public void joinFan(FanVO fan) {
		fanMapper.joinFan(fan);
	}

	@Override
	public void updateUser_bal(Long user_num) {
		fanMapper.updateUser_bal(user_num);
	}

	@Override
	public void removeFan(FanVO fan) {
		fanMapper.removeFan(fan);
	}
	
	@Override
	public void deleteFan(FanVO fan) {
		fanMapper.deleteFan(fan);
	}

	@Override
	public FanVO selectFan(Long user_num, Long fan_artist) {
		return fanMapper.selectFan(user_num, fan_artist);
	}

	@Override
	public FanVO noMoreFan(Long user_num, Long fan_artist) {
		return fanMapper.noMoreFan(user_num, fan_artist);
	}

	@Override
	public List<FanVO> selectNoMoney(FanVO fan) {
		return fanMapper.selectNoMoney(fan);
	}

	@Override
	public List<FanVO> selectKeepFan(FanVO fan) {
		return fanMapper.selectKeepFan(fan);
	}

	@Override
	public void keepFan(FanVO fan) {
		fanMapper.keepFan(fan);
	}

	@Override
	public void noMoney(FanVO fan) {
		fanMapper.noMoney(fan);
	}

	@Override
	public List<FanVO> getMyArtist(Map<String, Object> map) {
		return fanMapper.getMyArtist(map);
	}

	@Override
	public Integer countMyArtist(Long user_num) {
		return fanMapper.countMyArtist(user_num);
	}





}
