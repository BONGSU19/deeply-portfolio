package kr.spring.member.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.spring.member.service.ArtistService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//클래스 내에서 final 또는 @NonNull로 선언된 필드에 대해 생성자를 자동으로 생성
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
	//@RequiredArgsConstructor에 의해 의존성 주입됨
	private final MemberService memberService;
	private final ArtistService artistService;
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
		log.debug("[Spring Security Login Check 1] UserSecurityService 실행");
		log.debug("[Spring Security Login Check 1] 로그인 아이디 :" + id);
		MemberVO member = memberService.selectCheckMember(id);
		ArtistVO artist = artistService.selectCheckMember(id);
		if ((member==null || member.getAuth()==1)&&artist==null) {
			log.debug("[Spring Security Login Check 1] 로그인 아이디가 없거나 탈퇴회원");
			throw new UsernameNotFoundException("UserNotFound");
		}
		if(member!=null&&artist==null) {
			log.debug("<<member 진입>> :" + member);
			return new PrincipalDetails(member);
		}else{
			log.debug("<<artist 진입>> : " + artist);
			return new PrincipalDetails(artist);
		}
	}
}
