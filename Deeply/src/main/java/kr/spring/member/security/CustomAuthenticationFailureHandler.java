package kr.spring.member.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.debug("[Spring Security Login Check 2] CustomAuthnticationFailureHandler 실행");
		log.error("[Spring Security Login Check 2] 로그인 실패 : " + exception.toString());
		
		String error = "";
		if(exception instanceof UsernameNotFoundException | exception instanceof BadCredentialsException){
			error = "error";
		}
		
		//페이지가 리다이렉트되어서 아래와 같이 셋팅했을 경우 request에서 데이터를 읽을 수 있게 처리할 수 있음.
		//따라서 파라미터로 데이터를 넘길 필요가 없음
		final FlashMap flashMap = new FlashMap();
        flashMap.put("error", error);
        final FlashMapManager flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		setDefaultFailureUrl("/member/login");
		
		super.onAuthenticationFailure(request, response, exception);
	}

}
