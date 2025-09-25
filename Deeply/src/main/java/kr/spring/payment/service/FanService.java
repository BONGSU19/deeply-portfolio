package kr.spring.payment.service;

import java.util.List;
import java.util.Map;

import kr.spring.member.vo.ArtistVO;
import kr.spring.payment.vo.FanVO;

public interface FanService {
	//아티스트 정보 불러오기
	public ArtistVO artiInfo(Long user_num);
	public FanVO selectArtist(Long artist_num);

	//내 정보 조회
	Long getUser_bal(Long user_num);

	//팬인지 조회
	public FanVO selectFan(Long user_num, Long fan_artist);

	//팬클럽 가입
	public void updateUser_bal(Long user_num);
	public void joinFan(FanVO fan);

	//팬클럽 탈퇴신청
	public void removeFan(FanVO fan);
	//탈퇴 신청하고 만기일 된 팬정보 조회
	public FanVO noMoreFan(Long user_num, Long fan_artist);

	//만기일
	//만기일인데 돈 없는 사람 구하기
	public List<FanVO> selectNoMoney(FanVO fan);
	//만기일인데 돈 있는 사람 구하기
	public List<FanVO> selectKeepFan(FanVO fan);
	//정기결제
	public void keepFan(FanVO fan);
	//돈 없으면
	public void noMoney(FanVO fan);
	//만기일되면 탈퇴완료
	public void deleteFan(FanVO fan);

	//회원이 내가 팬맺은 아티스트 보기
	List<FanVO> getMyArtist(Map<String,Object> map);
	public Integer countMyArtist(Long user_num);
}
