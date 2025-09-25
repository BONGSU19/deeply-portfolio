package kr.spring.payment.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.ArtistVO;
import kr.spring.payment.vo.FanVO;

@Mapper
public interface FanMapper {
	//아티스트 정보 불러오기
	@Select("SELECT * FROM auser_detail WHERE user_num=${user_num}")
	public ArtistVO artiInfo(Long user_num);
	FanVO selectArtist(Long artist_num);
	
	//결제일 구하기
	LocalDate fan_start = LocalDate.now();
	LocalDate fan_end = LocalDate.now().plusMonths(1);
	
	//내 정보 조회
	//예치금 조회
	@Select("SELECT user_bal FROM duser_detail WHERE user_num = #{user_num}")
	public Long getUser_bal(Long user_num);
	
	//팬인지 조회
	@Select("SELECT * FROM fan WHERE user_num=${user_num} and fan_artist=${fan_artist}")
	public FanVO selectFan(Long user_num, Long fan_artist);
	
	//팬클럽 가입
	public void joinFan(FanVO fan);
	@Update("UPDATE duser_detail SET user_bal = user_bal - 6500 WHERE user_num = #{user_num} AND user_bal >= 6500")
	public void updateUser_bal(Long user_num);
	
	//팬클럽 탈퇴신청
	@Update("UPDATE fan SET fan_status = 2 WHERE user_num=${user_num} and fan_artist=${fan_artist}")
	public void removeFan(FanVO fan);
	//탈퇴 신청하고 만기일 된 팬정보 조회
	@Select("SELECT * FROM fan WHERE user_num=${user_num} and fan_artist=${fan_artist} and fan_status=2")
	public FanVO noMoreFan(Long user_num, Long fan_artist);
	
	//만기일
	//만기일인데 돈 없는 사람 구하기
	@Select("SELECT * FROM fan JOIN duser_detail USING (user_num) WHERE user_bal<6500 and TO_CHAR(fan_end,'YYYY/MM/DD') = TO_CHAR(SYSDATE,'YYYY/MM/DD')")
	public List<FanVO> selectNoMoney(FanVO fan);
	//만기일인데 돈 있는 사람 구하기
	@Select("SELECT * FROM fan JOIN duser_detail USING (user_num) WHERE user_bal>=6500 and TO_CHAR(fan_end,'YYYY/MM/DD') = TO_CHAR(SYSDATE,'YYYY/MM/DD')")
	public List<FanVO> selectKeepFan(FanVO fan);
	//정기결제
	@Update("UPDATE duser_detail SET user_bal = user_bal - 6500 WHERE user_num=${user_num}")
	public void keepFan(FanVO fan);
	//돈 없으면
	@Delete("DELETE FROM fan WHERE user_num=${user_num}")
	public void noMoney(FanVO fan);
	//만기일되면 탈퇴완료
	@Delete("DELETE FROM fan WHERE TO_CHAR(fan_end,'YYYY/MM/DD') = TO_CHAR(SYSDATE,'YYYY/MM/DD') and fan_status=2")
	public void deleteFan(FanVO fan);
	
	
	//회원이 내가 팬맺은 아티스트 보기
	List<FanVO> getMyArtist(Map<String,Object> map);
	@Select("SELECT COUNT(*) FROM fan WHERE user_num=#{user_num}")
	public Integer countMyArtist(Long user_num);
}
