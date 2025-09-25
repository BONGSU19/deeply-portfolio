package kr.spring.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import kr.spring.member.security.CustomOauth2UserService;
import kr.spring.member.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
//모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션,스프링 시큐리티를 활성화하는 역할
//내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정 가능
@EnableWebSecurity
//Controller 메서드 레벨에서 권한을 체크할 수 있도록 설정. @PreAuthorize 사용시 추가
@EnableGlobalMethodSecurity(prePostEnabled = true) 
//final 필드와 @NonNull 필드에 대해 생성자를 자동 생성
@RequiredArgsConstructor
public class SecurityConfig{
	@Value("${dataconfig.rememberme-key}")
	private String rememberme_key;
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	@Autowired
	private CustomOauth2UserService principalOauth2UserService;
	@Autowired
	private UserSecurityService userSecurityService;
	@Autowired
	private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler customAuthenticationFailureHandler;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	
		return http
		
				.authorizeHttpRequests(authorize-> authorize
						//롤 설정을 먼저 하고 permitAll()를 호출해야 정상적으로 롤이 지정됨
						///main/admin: ROLE_ADMIN 권한 필요 지정
						.antMatchers("/main/admin").hasAuthority("ROLE_ADMIN")
						//기타 경로: 누구나 접근 가능
						.antMatchers("/**").permitAll()
						//인증되지 않은 요청은 로그인 페이지로 리다이렉트됨
						.anyRequest().authenticated() 
						)
				.formLogin(login -> login //로그인 설정
						.loginPage("/member/login")
						.successHandler(customAuthenticationSuccessHandler)
						.failureHandler(customAuthenticationFailureHandler)
						.usernameParameter("id")
						.passwordParameter("passwd_hash"))
				.logout(logout -> logout //로그아웃 설정
						.logoutUrl("/member/logout")
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
						.deleteCookies("remember-me","JSESSIONID"))
				
				.oauth2Login(oauth2 -> oauth2
						.loginPage("/member/login")
						.userInfoEndpoint()
						.userService(principalOauth2UserService))
				.exceptionHandling(error -> error
						.accessDeniedHandler(new AccessDeniedHandler(){
							@Override
							public void handle(HttpServletRequest request, HttpServletResponse response,
									           AccessDeniedException accessDeniedException)
									                        throws IOException, ServletException {
								log.error("<<예외 발생>> : " + accessDeniedException.toString());
								log.debug("<<예외 발생 페이지>> : " + request.getRequestURI());
								log.debug("<<x-csrf-token>> : " + request.getHeader("x-csrf-token"));
								
								if(accessDeniedException instanceof MissingCsrfTokenException | accessDeniedException instanceof InvalidCsrfTokenException) {
									if(request.getRequestURI().equals("/member/logout")) {
										response.sendRedirect("/main/main");
									}else if(request.getHeader("x-csrf-token")!=null) {
										response.sendRedirect("/common/accessLogout");
									}else if(request.getContentType()!= null){
										request.getContentType().startsWith("multipart/form-data");	
										
										try {
											request.getParts();
											response.sendRedirect("/member/login");
										}catch(Exception e) {
											log.error("<<최대 업로드 사이즈 초과>>");
											response.sendRedirect("/common/fileSizeExceeded");
										}
									}else {
										response.sendRedirect("/member/login");
									}
								}else if(accessDeniedException instanceof AccessDeniedException) {
									response.sendRedirect("/common/accessDenied");
								}else {
									//로그아웃 상태에서 로그아웃 주소를 호출하면 오류가 발생하기 때문에 아래와 같이 조건 체크해서 로그인 페이지가 보여지는
									//것이 아니라 메인으로 이동하게 처리
									if(request.getRequestURI().equals("/member/logout")) {
										response.sendRedirect("/main/main");
									}else {
										response.sendRedirect("/member/login");
									}
								}
							}
						}))
				.rememberMe(me -> me
						.key(rememberme_key) //쿠키에 사용되는 값을 암호화하기 위한 키(key)값
						.tokenRepository(persistentTokenRepository()) //토큰은 데이터베이스에 저장
						.tokenValiditySeconds(60*60*24*7)
						.userDetailsService(userSecurityService))
				//.csrf(csrf -> csrf.disable()) //CSRF 보호 기능을 비활성화
				.build();	
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	/*
	 * 자동로그인 사용시 자동으로 생성되는 persistent_logins 테이블 컬럼 설명
	 * series :  사용자의 로그인 세션을 식별하는 고유한 값
	 * username: 로그인한 사용자의 ID
	 * token: 사용자의 브라우저에 저장되는 토큰 값(쿠키에 저장되는 암호화된 토큰 값)
	 *        이 토큰을 통해 시스템은 사용자를 인증
	 *        매번 로그인이 유지될 때마다 갱신
	 *        토큰이 일치하지 않으면 Remember-Me 세션이 무효화
	 * last_used: 토큰이 마지막으로 사용된 시각. 토큰의 유효 기간을 관리하는 데 사용
	 */
	
	
}





