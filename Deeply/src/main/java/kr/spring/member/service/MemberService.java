package kr.spring.member.service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.admin.vo.UserStatisticVO;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
public interface MemberService {

	public void insertMember(MemberVO member);

	public MemberVO selectIdAndNickName(Map<String,String> map);
	public MemberVO selectCheckMember(String id);
	public void registerMember(MemberVO memberVO);
	public String verifyEmail(String email, String code);
	public void storeVerificationCode(String email, String code);
	public Optional<MemberVO> findByEmail(String email);

	public String findId(String name,String email);

	public int selectRowCount(Map<String,Object> map);
	public List<MemberVO> selectList(Map<String,Object> map);
	
	public int selectRowCount2(Map<String,Object> map);
	public List<MemberVO> selectList2(Map<String,Object> map);
	
	
	public void resetPassword(String email,String newPassword);

	public void recordUserCountForDate();
	public List<UserStatisticVO> getUserStatisticsByDate();
	public void updateByAdmin(MemberVO memberVO);
	
	
	
	//마이페이지
	public MemberVO selectMember(Long user_num);
	public void updateProfile(MemberVO member);
	public void updateProfile2(ArtistVO artist);
	public void updateMember(MemberVO member);
	public void updateMember2(ArtistVO artist);
}
