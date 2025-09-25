package kr.spring.commu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.commu.service.CommuService;
import kr.spring.commu.vo.CommuVO;
import kr.spring.follow.service.FollowService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/commu")
public class CommuController {
	@Autowired
	CommuService commuService;
	@Autowired
	MemberService memberService;
	@Autowired
	FollowService followService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public CommuVO initCommand() {
		return new CommuVO();
	}
	
	/*========================
	 * 게시판 글 등록
	 *========================*/
		//등록 폼
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/write")
		public String form() {
			return "commuWrite";
		}
		
		//전송된 데이터 처리
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/write")
		public String submit(@Valid CommuVO commuVO,
				             BindingResult result,
				             HttpServletRequest request,
				             RedirectAttributes redirect,
				             @AuthenticationPrincipal 
				             PrincipalDetails principal) 
				          throws IllegalStateException, IOException {
			log.debug("<<게시판 글 저장>> : " + commuVO);
			//유효성 체크 결과 오류가 있으면 폼 호출
			if(result.hasErrors()) {
				return form();
			}
			//회원 번호 읽기
			MemberVO vo = principal.getMemberVO();
			commuVO.setUser_num(vo.getUser_num());
			//파일 업로드
			commuVO.setC_file(FileUtil.createFile(request, commuVO.getUpload()));
			//글쓰기
			commuService.insertCommu(commuVO);
			
			//RedirectAttributes 객체는 리다이렉트 시점에 한 번만
			//사용되는 데이터를 전송.
			//브라우저에 데이터를 전송하지만 URI상에는 보이지 않는 숨겨진
			//데이터의 형태로 전달
			redirect.addFlashAttribute("result","success");
			
			//리다이렉트할 URL에 쿼리 스트링으로 추가
			//redirect.addAttribute("board_num", 10);
			
			return "redirect:/commu/list";
		}
		//등록 폼
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/Fanwrite")
		public String Fanform() {
			return "commuWrite";
		}
		
		//전송된 데이터 처리
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/Fanwrite")
		public String fanSubmit(@Valid CommuVO commuVO,
				BindingResult result,
				HttpServletRequest request,
				RedirectAttributes redirect,
				@AuthenticationPrincipal 
				PrincipalDetails principal) 
						throws IllegalStateException, IOException {
			log.debug("<<게시판 글 저장>> : " + commuVO);
			//유효성 체크 결과 오류가 있으면 폼 호출
			if(result.hasErrors()) {
				return form();
			}
			//회원 번호 읽기
			MemberVO vo = principal.getMemberVO();
			commuVO.setUser_num(vo.getUser_num());
			//파일 업로드
			commuVO.setC_file(FileUtil.createFile(request, commuVO.getUpload()));
			//글쓰기
			commuService.insertCommu(commuVO);
			
			//RedirectAttributes 객체는 리다이렉트 시점에 한 번만
			//사용되는 데이터를 전송.
			//브라우저에 데이터를 전송하지만 URI상에는 보이지 않는 숨겨진
			//데이터의 형태로 전달
			redirect.addFlashAttribute("result","success");
			
			//리다이렉트할 URL에 쿼리 스트링으로 추가
			//redirect.addAttribute("board_num", 10);
			
			return "redirect:/commu/list";
		}
	/*========================
	 * 게시판 목록
	 *========================*/
	//전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String getList(@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="") String c_category,
			String keyfield,String keyword,Model model,@AuthenticationPrincipal 
			PrincipalDetails principal) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = commuService.selectRowCount(map);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,20,5,"list",null);
		
		List<CommuVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = commuService.selectList(map);
		}
		
		//작성자 정보
		MemberVO member=memberService.selectMember(principal.getMemberVO().getUser_num());
		
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		model.addAttribute("member", member);
		
		return "commuList";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/commuFreeList")
	public String getFreeList(@RequestParam(defaultValue="1") int pageNum,
	                          @RequestParam(defaultValue="1") String c_category,
	                          String keyfield, String keyword, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    
	    // c_category가 1일 경우만 필터링
	    if ("1".equals(c_category)) {
	        map.put("c_category", c_category);  // c_category가 1인 경우만 조건에 넣음
	    }
	    
	    map.put("user_num", principal.getMemberVO().getUser_num());
	    map.put("keyfield", keyfield);
	    map.put("keyword", keyword);
	    
	    // 전체/검색 레코드수
	    int count = commuService.selectFreeRowCount(map);
	    // 페이지 처리
	    PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 20, 5, "list", null);
	    
	    List<CommuVO> list = null;
	    if (count > 0) {
	        map.put("start", page.getStartRow());
	        map.put("end", page.getEndRow());
	        
	        list = commuService.selectFreeList(map);  // 필터링된 리스트 반환
	    }
	    
	    // 작성자 정보
	    MemberVO member = memberService.selectMember(principal.getMemberVO().getUser_num());
	    
	    model.addAttribute("count", count);
	    model.addAttribute("list", list);
	    model.addAttribute("page", page.getPage());
	    model.addAttribute("member", member);
	    
	    return "commuFreeList";
	}
	//팬덤게시판 보기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/commuFandomList")
	public String getFandomList(@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="2") String c_category,
			String keyfield,String keyword,Model model,@AuthenticationPrincipal 
			PrincipalDetails principal) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("c_category", c_category);
		map.put("user_num", principal.getMemberVO().getUser_num());
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = commuService.selectFandomRowCount(map);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,20,5,"list",null);
		
		List<CommuVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = commuService.selectFandomList(map);
		}
		
		//작성자 정보
		MemberVO member=memberService.selectMember(principal.getMemberVO().getUser_num());
		
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		model.addAttribute("member", member);
		
		return "commuFandomList";
	}
	//내 글만 보기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myList")
	public String getMyList(@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="") String c_category,
			String keyfield,String keyword,Model model,@AuthenticationPrincipal 
			PrincipalDetails principal) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_num", principal.getMemberVO().getUser_num());
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = commuService.selectMyRowCount(map);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,20,5,"list",null);
		
		List<CommuVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = commuService.selectMyList(map);
		}
		
		//작성자 정보
		MemberVO member=memberService.selectMember(principal.getMemberVO().getUser_num());
		
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		model.addAttribute("member", member);
		
		return "commuMyList";
	}
	/*========================
	 * 게시판 글상세
	 *========================*/
	//글상세
	@GetMapping("/detail")
	public String process(long c_num,Model model) {
		log.debug("<<게시판 글상세 - c_num>> : " + c_num);
		//해당 글의 조회수 증가
		commuService.updateHit(c_num);
		
		CommuVO commu = commuService.selectCommu(c_num);
		
		model.addAttribute("commu", commu);
		
		return "commuView";
	}
	
	//파일 다운로드
	@GetMapping("/file")
	public String download(long c_num,
			    HttpServletRequest request,
			     Model model) {
		CommuVO commu = commuService.selectCommu(c_num);
		byte[] downloadFile = 
				FileUtil.getBytes(
						request.getServletContext()
						       .getRealPath("/assets/upload")
						            +"/"+commu.getC_file());
		
		model.addAttribute("downloadFile", downloadFile);
		model.addAttribute("c_file", commu.getC_file());
		
		return "downloadView";
	}
	
	/*========================
	 * 글 수정
	 *========================*/
	//수정 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update")
	public String formUpdate(long c_num,
			                 Model model,
			                 @AuthenticationPrincipal
			                 PrincipalDetails principal) {
		CommuVO CommuVO = commuService.selectCommu(c_num);
		
		//DB에 저장된 파일 정보 구하기
		if(principal.getMemberVO().getUser_num() != 
				                 CommuVO.getUser_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "redirect:/common/accessDenied";
		}
		
		model.addAttribute("CommuVO", CommuVO);
		
		return "commuModify";
	}
	//수정 폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String submitUpdate(@Valid CommuVO CommuVO,
			                  BindingResult result,
			                  HttpServletRequest request,
			                  Model model,
			                  @AuthenticationPrincipal
			                  PrincipalDetails principal) 
			                		  throws IllegalStateException, IOException {
		log.debug("<<글 수정>> : " + CommuVO);
		
		//DB에 저장된 파일 정보 구하기
		CommuVO db_commu = 
			commuService.selectCommu(CommuVO.getC_num());
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(principal.getMemberVO().getUser_num() != 
				db_commu.getUser_num()) {
			return "redirect:/common/accessDenied";
		}
		//전송된 데이터 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			//title 또는 content가 입력되지 않아 유효성 체크에 걸리면
			//파일 정보를 잃어버리기 때문에 폼을 호출할 때 다시 셋팅해주어야
			//함
			CommuVO.setC_file(db_commu.getC_file());
			
			return "commuModify";
		}
		//파이명 셋팅(FileUtil.createFile에서 파일이 없으면 null 처리했음)
		CommuVO.setC_file(
				FileUtil.createFile(request, 
						             CommuVO.getUpload()));
		//글 수정
		commuService.updateCommu(CommuVO);
		
		//파일 교체 여부를 체크해서 교체했을 경우 기존 파일을 삭제
		if(CommuVO.getUpload() != null && 
				!CommuVO.getUpload().isEmpty()) {
			//기존 파일(수정 작업전 파일) 삭제 처리
			FileUtil.removeFile(request, db_commu.getC_file());
		}
		
		//View에 표시할 메시지
		model.addAttribute("message", "글 수정 완료!!");
		model.addAttribute("url", 
				request.getContextPath() + 
				         "/commu/detail?c_num="
						             +CommuVO.getC_num());		
		return "common/resultAlert";
	}
	/*========================
	 * 게시판 글 삭제
	 *========================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String submitDelete(long c_num,
			                   HttpServletRequest request,
			                   @AuthenticationPrincipal
			                   PrincipalDetails principal) {
		log.debug("<<게시판 글 삭제>> : " + c_num);
		
		//DB에 저장된 게시판 정보 구하기
		CommuVO db_commu = 
				       commuService.selectCommu(c_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(principal.getMemberVO().getUser_num() != 
				db_commu.getUser_num()) {
			return "redirect:/common/accessDenied";
		}
		
		//글 삭제
		commuService.deleteCommu(c_num);
		
		if(db_commu.getC_file() != null) {
			//파일 삭제
			FileUtil.removeFile(request, db_commu.getC_file());
		}
		
		return "redirect:/commu/list";
	}
	
	
	
}
