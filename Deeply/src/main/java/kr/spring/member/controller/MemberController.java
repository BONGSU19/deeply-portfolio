package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.member.service.ArtistService;
import kr.spring.member.service.EmailService;
import kr.spring.member.service.MemberService;
import kr.spring.member.service.MemberServiceImpl;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
import kr.spring.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

@RequestMapping("/member")
public class MemberController {



	@Value("${dataconfig.naver-client-id}")
	private String naver_client_id;
	@Value("${dataconfig.naver-client-secret}")
	private String naver_client_secret;

	@Autowired
	private MemberService memberService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailService emailService;
	//자바빈(VO) 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	/*===============================
	 * 회원가입
	 *===============================*/
	//회원가입 폼 호출

	@GetMapping("/registerUser")
	public String form() {
		return "memberRegister";
	}

	//인증번호 전송
	@PostMapping("/emailSubmit")
	@ResponseBody
	public Map<String, String> emailSubmit(@RequestBody Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String email= request.get("email");
		Optional<MemberVO> existingMember = memberService.findByEmail(email);
		if (existingMember.isPresent()) {
			response.put("result", "fail");
			return response;
		}
		String verificationCode = emailService.generateVerificationCode();
		emailService.sendVerificationEmail(email, verificationCode);
		MemberServiceImpl.verificationCodes.put(email,verificationCode );

		response.put("email",email); 
		response.put("code",verificationCode);

		return response;
	}

	@PostMapping("/emailSubmit2")
	@ResponseBody
	public Map<String, String> emailSubmit2(@RequestBody Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String email= request.get("email");

		String verificationCode = emailService.generateVerificationCode();
		emailService.sendVerificationEmail(email, verificationCode);
		MemberServiceImpl.verificationCodes.put(email,verificationCode );

		response.put("email",email); 
		response.put("code",verificationCode);

		return response;
	}



	//회원가입 데이터 전송
	@PostMapping("/registerUser")
	public @ResponseBody Map<String, String> registerUser(@Valid MemberVO memberVO, 
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
			}else if(!memberVO.getPasswd_hash().equals(memberVO.getConfirmPassword())) {
				response.put("error", "비밀번호 2개가 일치 안합니다");


			}else if (result.hasFieldErrors("nick_name")) {
				response.put("error", "닉네임을 제대로 입력해야 합니다.");
			} else if (result.hasFieldErrors("zipcode")) {
				response.put("error", "우편번호는 5자리 숫자로 입력해야 합니다.");
			} else if (result.hasFieldErrors("address1")) {
				response.put("error", "주소1를 올바르게 입력해주세요.");
			} else if (result.hasFieldErrors("address2")) {
				response.put("error", "주소2를 올바르게 입력해주세요.");
			} else if (result.hasFieldErrors("phone")) {
				response.put("error", "전화번호를 올바르게 입력해주세요.");
			}
			else {
				response.put("error", "알 수 없는 오류가 발생했습니다.");
			}
			return response;
		}
		// 비밀번호 암호화 및 회원 저장
		memberVO.setPasswd_hash(passwordEncoder.encode(memberVO.getPasswd_hash()));
		// 기본 프로필 이미지 설정

		String defaultPhotoName = "defaultProfile.svg";


		memberVO.setPhoto_name(defaultPhotoName);
		memberService.insertMember(memberVO);


		// 성공적인 회원가입 후 메시지
		response.put("successMessage", "회원가입이 완료되었습니다. 이메일 인증을 진행해주세요.");

		return response;  // 성공적인 응답 반환
	}


	// 이메일 인증 처리
	@PostMapping("/verifyEmail")
	public @ResponseBody Map<String, String> verifyEmail(@RequestParam String email, 
			@RequestParam String code, 
			Model model,
			MemberVO memberVO) {
		Map<String, String> response = new HashMap<>();
		String isVerified = memberService.verifyEmail(email, code);

		if ("verified".equals(isVerified)) {
			response.put("status", "verified");
		} else {
			response.put("status", "failed");
		}
		return response;
	}

	// 이메일 인증 코드 확인 페이지
	@GetMapping("/verifyEmailPage")
	public String verifyEmailPage(@RequestParam String email, Model model) {
		model.addAttribute("email", email);
		return "/verifyEmail";
	}
	@GetMapping("/login")
	public String formLogin() {
		return "memberLogin";
	}

	private String generateVerificationCode() {
		Random random = new Random();
		return String.format("%06d", random.nextInt(1000000));
	}
	@GetMapping("/findID")
	public String findID() {
		return"findID";
	}

	@PostMapping("/findID")
	public String findUserId(@RequestParam String name, 
			@RequestParam String email, 
			Model model) {
		try {
			String id = memberService.findId(name, email);
			model.addAttribute("message", "찾은 아이디: " + id);
		} catch (IllegalArgumentException e) {
			model.addAttribute("message", "입력한 정보에 해당하는 아이디가 없습니다.");
		}
		return "findID"; // findId.jsp로 이동
	}



	@GetMapping("/findPasswd")
	public String findPasswd() {
		return"findPasswd";
	}


	@PostMapping("/requestReset")
	public String requestPasswordReset(@RequestParam String email, Model model) {
		try {
			String verificationCode = emailService.generateVerificationCode();
			emailService.sendPasswordResetEmail(email);
			model.addAttribute("message", "비밀번호 재설정 이메일이 전송되었습니다.");
		} catch (Exception e) {
			model.addAttribute("message", "이메일 전송에 실패했습니다.");
		}
		return "member/resultView";  // 이메일 발송 후 결과 출력
	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(@RequestParam String email, Model model) {
		model.addAttribute("email", email);  // 이메일 값을 폼에 전달
		return "resetPassword";  // 비밀번호 재설정 폼을 렌더링
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam String email, 
			@RequestParam String newPassword, 
			Model model,
			HttpServletRequest request) {
		newPassword=passwordEncoder.encode(newPassword);

		try {
			memberService.resetPassword(email, newPassword);  // 비밀번호 재설정 서비스 호출
			model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		} catch (Exception e) {
			model.addAttribute("message", "비밀번호 변경에 실패했습니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}
		return "common/resultAlert";  // 변경 결과를 출력할 폼으로 다시 리다이렉트
	}

	/*=================
	 * 마이페이지
    =================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mypage")
	public String myPageMain(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		// 멤버와 아티스트 정보를 각각 조회
		MemberVO member = null;
		ArtistVO artist = null;
		
		// principalDetails가 null이 아니고 memberVO가 null이 아니면 member 정보 조회
		if (principalDetails != null) {
			// memberVO가 존재하면 member 정보 조회
			if (principalDetails.getMemberVO() != null) {
				member = memberService.selectMember(principalDetails.getMemberVO().getUser_num());
				log.debug("<<회원상세 정보>> : " + member);
			}
			// memberVO가 없고 artistVO가 존재하면 artist 정보 조회
			else if (principalDetails.getArtistVO() != null) {
				artist = artistService.selectMember(principalDetails.getArtistVO().getUser_num());
				log.debug("<<아티스트상세 정보>> : " + artist);
			}
		}
		
		// member와 artist 중 하나라도 존재하면 해당 정보를 모델에 담음
		if (member instanceof MemberVO) {
			model.addAttribute("member", member);
		} else {
			model.addAttribute("member", artist);
		}
		if (member instanceof MemberVO) {
			model.addAttribute("isMemberVO", true);
		} else {
			model.addAttribute("isMemberVO", false);
			model.addAttribute("artistVO", artist); // artistVO를 추가
		}
		return "mypage";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myPage1")
	public String myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
	    // 멤버와 아티스트 정보를 각각 조회
	    MemberVO member = null;
	    ArtistVO artist = null;

	    // principalDetails가 null이 아니고 memberVO가 null이 아니면 member 정보 조회
	    if (principalDetails != null) {
	        // memberVO가 존재하면 member 정보 조회
	        if (principalDetails.getMemberVO() != null) {
	            member = memberService.selectMember(principalDetails.getMemberVO().getUser_num());
	            log.debug("<<회원상세 정보>> : " + member);
	        }
	        // memberVO가 없고 artistVO가 존재하면 artist 정보 조회
	        else if (principalDetails.getArtistVO() != null) {
	            artist = artistService.selectMember(principalDetails.getArtistVO().getUser_num());
	            log.debug("<<아티스트상세 정보>> : " + artist);
	        }
	    }
	  
	    // member와 artist 중 하나라도 존재하면 해당 정보를 모델에 담음
	    if (member instanceof MemberVO) {
	        model.addAttribute("member", member);
	    } else {
	        model.addAttribute("member", artist);
	    }
	    if (member instanceof MemberVO) {
	        model.addAttribute("isMemberVO", true);
	    } else {
	        model.addAttribute("isMemberVO", false);
	        model.addAttribute("artistVO", artist); // artistVO를 추가
	    }
	    return "myPage1";
	}

	/*=================
	 * 프로필 사진
    =================*/
	//프로필 사진 보기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/photoView")
	public String getProfile(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request, Model model) {
		MemberVO user = principalDetails.getMemberVO();
		ArtistVO artist=principalDetails.getArtistVO();
		log.debug("<<photoView>> : " + user);
		if(artist!=null&&user==null) {//로그인이 되지 않은 경우
			ArtistVO artistVO = 
					artistService.selectMember(artist.getUser_num());
			viewProfile(user,artistVO,request,model);			
		}else if(user!=null&&artist==null) {//로그인 된 경우
			MemberVO memberVO = 
					memberService.selectMember(user.getUser_num());
			viewProfile(memberVO,artist,request,model);
		}else {
			MemberVO memberVO=null;
			ArtistVO artist2=null;
			viewProfile(memberVO,artist2,request,model);
		}
		return "imageView";
	}


	
	@GetMapping("/photoView2")
	public String getProfile2(HttpServletRequest request, Model model,@RequestParam long user_num ) {

			MemberVO user=memberService.selectMember(user_num);
			ArtistVO artistVO = 
					artistService.selectMember(user_num);
			viewProfile(user,artistVO,request,model);			
			log.debug("1");
		return "imageView";
	}

	//프로필 사진 출력(회원번호 지정)
	@GetMapping("/viewProfile")
	public String getProfileByUser_num(long user_num, HttpServletRequest request, Model model) {
		MemberVO memberVO = 
				memberService.selectMember(user_num);
		ArtistVO aritstVO=artistService.selectMember(user_num);
		
		viewProfile(memberVO,aritstVO, request, model);

		return "imageView";
	}

	//프로필 사진 처리를 위한 공통 코드
	public void viewProfile(MemberVO memberVO,ArtistVO artistVO, HttpServletRequest request, Model model) {
		if((memberVO==null&&artistVO.getPhoto()==null)||(artistVO==null&&memberVO.getPhoto()==null)) {
			
			getBasicProfileImage(request, model);
		}else if(memberVO!=null&&memberVO.getPhoto_name()!=null) {//업로드한 프로필 이미지 읽기
			model.addAttribute("imageFile", memberVO.getPhoto());
			model.addAttribute("filename", memberVO.getPhoto_name());
		}else {
		
			model.addAttribute("imageFile", artistVO.getPhoto());
			model.addAttribute("filename", artistVO.getPhoto_name());
			
		}
	}
	


	//기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request, Model model) {
		byte[] readbyte = 
				FileUtil.getBytes(
						request.getServletContext().getRealPath(
								"/assets/image_bundle/face 1.png"));
		model.addAttribute("imageFile", readbyte);
		model.addAttribute("filename", "face 1.png");
		log.debug("3");
	}
	
	//수정폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String submitUpdate(MemberVO memberVO, 
	                           ArtistVO artistVO, 
	                           @AuthenticationPrincipal PrincipalDetails principal, 
	                           RedirectAttributes redirectAttributes) {

	    // memberVO가 있을 경우 처리
	    if (principal.getMemberVO() != null) {
	        if (!isValidEmail(memberVO.getEmail())) {
	            redirectAttributes.addFlashAttribute("successMessage", "유효한 이메일을 입력해주세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName(memberVO.getNick_name())) {
	            redirectAttributes.addFlashAttribute("successMessage", "닉네임을 올바르게 입력해주세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName2(memberVO.getZipcode())) {
	            redirectAttributes.addFlashAttribute("successMessage", "유효한 우편번호를 입력해주세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName2(memberVO.getAddress1())) {
	            redirectAttributes.addFlashAttribute("successMessage", "주소를 입력하세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName2(memberVO.getAddress2())) {
	            redirectAttributes.addFlashAttribute("successMessage", "주소를 입력하세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName2(memberVO.getPhone())) {
	            redirectAttributes.addFlashAttribute("successMessage", "번호를 입력하세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName2(memberVO.getId())) {
	            redirectAttributes.addFlashAttribute("successMessage", "아이디를 입력하세요.");
	            return "redirect:/member/myPage1";
	        }

	        // 회원 정보 수정
	        
	        long userNum = principal.getMemberVO().getUser_num();
	        memberVO.setUser_num(userNum);
	        memberService.updateMember(memberVO);

	        // PrincipalDetails에 저장된 정보 갱신
	        principal.getMemberVO().setNick_name(memberVO.getNick_name());
	        principal.getMemberVO().setEmail(memberVO.getEmail());
	    }

	    // artistVO가 있을 경우 처리
	    if (principal.getArtistVO() != null) {
	        if (!isValidEmail(artistVO.getEmail())) {
	            redirectAttributes.addFlashAttribute("successMessage", "유효한 이메일을 입력해주세요.");
	            return "redirect:/member/myPage1";
	        }
	        if (!isValidNickName2(artistVO.getId())) {
	            redirectAttributes.addFlashAttribute("successMessage", "아이디를 입력하세요.");
	            return "redirect:/member/myPage1";
	        }

	        // 아티스트 정보 수정
	        
	        long userNum = principal.getArtistVO().getUser_num();
	        artistVO.setUser_num(userNum);
	        memberService.updateMember2(artistVO);

	        // PrincipalDetails에 저장된 아티스트 이메일 갱신
	        principal.getArtistVO().setEmail(artistVO.getEmail());
	    }

	    // 성공 메시지
	    redirectAttributes.addFlashAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
	    return "redirect:/member/myPage1";
	}


		
		private boolean isValidEmail(String email) {
		    // 이메일 유효성 검사 로직
		    return email != null && email.contains("@");
		}

		private boolean isValidNickName(String nickName) {
		    // 닉네임 유효성 검사 로직
		    return nickName != null && nickName.length() > 2;
		}
		
		private boolean isValidNickName2(String nickName) {
		    // 닉네임 유효성 검사 로직
		    return nickName != null;
		}
		
		
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@GetMapping("/admin_update")
		public String form(long user_num, Model model) {
			MemberVO memberVO = 
					memberService.selectMember(user_num);
			model.addAttribute("memberVO", memberVO);
			
			return "admin_memberModify";
		}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PostMapping("/admin_update")
		public String submit(MemberVO memberVO,
				             Model model,
				             HttpServletRequest request) {
			log.debug("<<관리자 회원권한 수정>> : " + memberVO);
			//회원권한 수정
			memberService.updateByAdmin(memberVO);
			
			//View에 표시할 메시지
			model.addAttribute("message", "회원권한 수정 완료!");
			model.addAttribute("url", 
					request.getContextPath()+"/member/admin_update?user_num="+memberVO.getUser_num());
			
			return "common/resultAlert";
		}
		
}







