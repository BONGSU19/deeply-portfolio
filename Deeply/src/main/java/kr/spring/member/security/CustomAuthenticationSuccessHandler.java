package kr.spring.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
    		HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.debug("[Spring Security Login Check 2] CustomAuthnticationSuccessHandler 실행");
        MemberVO user = ((PrincipalDetails)authentication.getPrincipal()).getMemberVO();
        ArtistVO artist = ((PrincipalDetails)authentication.getPrincipal()).getArtistVO();
        log.debug("[Spring Security Login Check 2] 로그인 성공 : " + user);
        log.debug("[Spring Security Login Check 2] 로그인 성공 : " + artist);
       
		if(user!=null && user.getAuth() == 9) {//관리자					
			setDefaultTargetUrl("/main/admin");
		}else if(user!=null && user.getAuth() == 2) {//정지회원	
			log.debug("[Spring Security Login Check 2] 정지회원 : " + user.getId());
			//정지회원일 경우 로그아웃 처리
			new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
			
			final FlashMap flashMap = new FlashMap();
	        flashMap.put("error", "error_noAuthority");
	        final FlashMapManager flashMapManager = new SessionFlashMapManager();
	        flashMapManager.saveOutputFlashMap(flashMap, request, response);
	        
			setDefaultTargetUrl("/member/login");
		}else {
			//루트로 이동시 생략 가능
			setDefaultTargetUrl("/");
		}
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
