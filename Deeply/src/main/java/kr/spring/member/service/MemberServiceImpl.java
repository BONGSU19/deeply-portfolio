package kr.spring.member.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.admin.dao.UserStatisticMapper;
import kr.spring.admin.vo.UserStatisticVO;
import kr.spring.member.dao.ArtistMapper;
import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	
	@Override
	public MemberVO selectCheckMember(String id) {
		return memberMapper.selectCheckMember(id);
	}

   @Override
    public void insertMember(MemberVO member) {
        // 시퀀스 값을 가져와서 user_num에 설정
    	long user_num = memberMapper.selectUser_num(); 
        member.setUser_num(user_num);

        // 회원 정보를 삽입
        memberMapper.insertMember(member);
        memberMapper.insertMember_detail(member);
       
    }

	@Override
	public MemberVO selectIdAndNickName(Map<String, String> map) {
		return memberMapper.selectIdAndNickName(map);

	}
	
	@Override
	public void updateMember(MemberVO member) {
		memberMapper.updateMember2(member);
		memberMapper.updateMember_detail(member);
	}
	

	public static Map<String, String> verificationCodes = new HashMap<>();
	    

	    public void registerMember(MemberVO memberVO) {
	        // 이메일 중복 검사
	        Optional<MemberVO> existingMember = memberMapper.findByEmail(memberVO.getEmail());
	        if (existingMember.isPresent()) {
	            throw new RuntimeException("이미 가입된 이메일입니다.");
	        }
	      
	        // 인증 상태 초기화
	        memberMapper.insertMember(memberVO);  // 회원 정보 저장
	    }

	    public String verifyEmail(String email, String code) {
	        String storedCode = verificationCodes.get(email);
	        if (storedCode == null || !storedCode.equals(code)) {
	            return "fail";
	        }
	        return "verified";
	    }

	    public void storeVerificationCode(String email, String code) {
	        verificationCodes.put(email, code);
	    }

		@Override
		public Optional<MemberVO> findByEmail(String email) {
		    // memberMapper.findByEmail(email)이 null일 수 있으므로 Optional로 감싼다
		   
		    return memberMapper.findByEmail(email);
		}

		@Override
		public MemberVO selectMember(Long mem_num) {
			return memberMapper.selectMember(mem_num);
		}

		@Override
		public String findId(String name, String email) {
			if(memberMapper.findIdByNameAndEmail(name, email)!=null) {
				return memberMapper.findIdByNameAndEmail(name, email);
			}else {
				return memberMapper.findIdByNameAndEmail2(name, email);
			}
			
		}

		@Override
		public void resetPassword(String email,String newPassword) {
			if(memberMapper.findPasswdByNameAndEmail(email)!=null) {
				 memberMapper.resetPassword(email, newPassword);
			}else {
			 memberMapper.resetPassword2(email, newPassword);
			}


			

		}
		@Autowired
		private UserStatisticMapper userStatisticMapper;
   
	
		@Override
		   public void recordUserCountForDate() {
			 String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        // 날짜별로 접속자 수를 확인하고, 없으면 새로 추가, 있으면 업데이트
		        if (userStatisticMapper.existsByDate(date)) {
		            userStatisticMapper.updateUserCount(date);  // 접속자 수 업데이트
		        } else {
		            userStatisticMapper.insertUserCount(date);  // 새로운 날짜에 접속자 수 추가
		        }
		    }
		@Override
		public List<UserStatisticVO> getUserStatisticsByDate() {
	        return userStatisticMapper.selectAllStatistics();  // 날짜별 접속자 수를 반환
	    }

		@Override
		public int selectRowCount(Map<String, Object> map) {
			return memberMapper.selectRowCount(map);
		}

		@Override
		public List<MemberVO> selectList(Map<String, Object> map) {
			return memberMapper.selectList(map);
		}
		
		@Override
		public int selectRowCount2(Map<String, Object> map) {
			return memberMapper.selectRowCount2(map);
		}

		@Override
		public List<MemberVO> selectList2(Map<String, Object> map) {
			return memberMapper.selectList2(map);
		}
		@Override
		public void updateProfile(MemberVO member) {
			memberMapper.updateProfile(member);
		}

		@Override
		public void updateProfile2(ArtistVO artist) {
	
			memberMapper.updateProfile2(artist);
		}

		@Override
		public void updateMember2(ArtistVO artist) {
		
			memberMapper.updateMember_detail2(artist);
			memberMapper.updateMember3(artist);
			
		}

		@Override
		public void updateByAdmin(MemberVO memberVO) {
			memberMapper.updateByAdmin(memberVO);
			
		}}
