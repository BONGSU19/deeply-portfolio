package kr.spring.member.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.KakaoUserInfo;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.NaverUserInfo;
import kr.spring.member.vo.OAuth2UserInfo;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

/*
 * OAuth 2.0(Open Authorization 2.0, OAuth2) : 인증을 위한 개방형 표준 프로토콜
 * 네이버,카카오 간편 로그인 기능도 OAuth2 프로토콜 기반의 사용자 인증 기능을 제공
 */
@Slf4j
@Service
public class CustomOauth2UserService 
                        extends DefaultOAuth2UserService{
	@Autowired
	private MemberService memberService;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest)
	                    throws OAuth2AuthenticationException{
		//getClientRegistration -> registrationId를 사용해서 어떤
		//Oauth로 로그인 했는지 확인 가능.
		log.debug("getClientRegistration : " 
		      + userRequest.getClientRegistration());//서버의 기본 정보 출력
		log.debug("getAccessToken : " 
		      + userRequest.getAccessToken().getTokenValue());//패스워드 토큰 값
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.debug("getAttributes : " + oAuth2User.getAttributes());//회원정보 출력
		
		String registrationId = userRequest.getClientRegistration()
				                           .getRegistrationId();
		OAuth2UserInfo oAuth2UserInfo = null;
		if(registrationId.equals("naver")) {
			oAuth2UserInfo = new NaverUserInfo(
				(Map)oAuth2User.getAttributes().get("response"));
			log.debug("<<네이버 로그인>>");
		}else if(registrationId.equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(
					               oAuth2User.getAttributes());
			log.debug("<<카카오 로그인>>");
		}else {
			log.debug("<<미지원 로그인>>");
		}
		
		String provider = oAuth2UserInfo.getProvider();
		String username = provider + "_" + oAuth2UserInfo.getProviderId();
		String name = oAuth2UserInfo.getName();
		String nick_name = provider + "_" + oAuth2UserInfo.getNick_name();
		String email = oAuth2UserInfo.getEmail();
		
		log.debug("<<소셜 로그인 아이디>> : " + username);
		MemberVO db_member = 
				memberService.selectCheckMember(username);
		if(db_member==null) {
			//회원가입이 안 됨
			MemberVO member = new MemberVO();
			member.setId(username);
			member.setName(name);
			member.setNick_name(nick_name);
			member.setEmail(email);
			member.setSocial_name(provider);
			
			//null를 인정하지 않는 컬럼은 " " 공백 처리
			if(member.getName()==null || member.getName().equals(""))
				member.setName(" ");
			if(member.getEmail()==null || member.getEmail().equals(""))
				member.setEmail(" ");
			if(member.getPhone()==null || member.getPhone().equals(""))
				member.setPhone(" ");
			
			member.setPasswd_hash(" ");
			member.setZipcode(" ");
			member.setAddress1(" ");
			member.setAddress2(" ");
			
			//회원가입
			memberService.insertMember(member);
			db_member = member;
			db_member.setAuth(2);//일반회원
		}
		
		return new PrincipalDetails(
				db_member,oAuth2User.getAttributes());
	}
}






