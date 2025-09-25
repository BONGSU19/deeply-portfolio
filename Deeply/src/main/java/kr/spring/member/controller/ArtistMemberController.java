package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.ArtistService;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/artistMember")
public class ArtistMemberController {
@Autowired
private ArtistService artistService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@ModelAttribute
	public ArtistVO initCommand() {
		return new ArtistVO();
	}


	@GetMapping("/artistRegister")
	public String formLogin() {
		return "artistRegister" ;
	}
	
	@PostMapping("/registerUser")
	public @ResponseBody Map<String, String> registerUser(@Valid ArtistVO artistVO, 
			BindingResult result) {
		Map<String, String> response = new HashMap<>();

		// 유효성 검사 결과 오류가 있으면, 오류 메시지를 response에 추가
		if (result.hasErrors()) {
			if(result.hasFieldErrors("email")) {
				response.put("error", "이메일을 제대로 입력해주새요.");}
			else if(result.hasFieldErrors("code")) {
				response.put("error", "코드를 입력해주세요");
			}
			else if(result.hasFieldErrors("id")) {
				response.put("error", "아이디는 4~12자의 영문자와 숫자로 구성되어야 합니다.");
			} else if (result.hasFieldErrors("passwd_hash")) {
				response.put("error", "비밀번호는 8~20자의 영문자, 숫자, 특수문자로 구성되어야 합니다.");
			}else if(!artistVO.getPasswd_hash().equals(artistVO.getConfirmPassword())) {
				response.put("error", "비밀번호 2개가 일치 안합니다");
			}else if(result.hasFieldErrors("name")) {
				response.put("error", "이름을 정확히 입력바람");
			}else if(result.hasFieldErrors("group_name")) {
				response.put("error", "그룹이름을 제대로 입력해주세요");
			}
			else {

				response.put("error", "알 수 없는 오류가 발생했습니다.");
			}
			return response;
		}
		// 비밀번호 암호화 및 회원 저장
		artistVO.setPasswd_hash(passwordEncoder.encode(artistVO.getPasswd_hash()));
		artistService.insertMember(artistVO);

		// 성공적인 회원가입 후 메시지
		response.put("successMessage", "회원가입이 완료되었습니다. 이메일 인증을 진행해주세요.");

		return response;  // 성공적인 응답 반환
	}

	@PostMapping("/artistRegister")
	public String register() {
		return null;
		
	}

	
}
