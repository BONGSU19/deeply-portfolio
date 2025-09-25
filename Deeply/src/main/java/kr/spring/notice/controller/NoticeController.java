package kr.spring.notice.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.commu.vo.CommuVO;
import kr.spring.member.service.ArtistService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.notice.service.NoticeService;
import kr.spring.notice.vo.NoticeVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	@Autowired
	ArtistService artistService;
	@Autowired
	MemberService memberService;
	
	//자바빈 초기화
	@ModelAttribute
	public NoticeVO initCommand() {
		return new NoticeVO();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String getList(@RequestParam(defaultValue="1") int pageNum,
	                      String keyfield, String keyword, Model model,
	                      @AuthenticationPrincipal PrincipalDetails principal) {
		
	    Long user_num = 0L;

	    if (principal.getMemberVO() != null && principal.getMemberVO().getUser_num() != null) {
	        user_num = principal.getMemberVO().getUser_num();
	        MemberVO member = memberService.selectMember(user_num);
	        model.addAttribute("member", member);
	    } else if (principal.getArtistVO() != null && principal.getArtistVO().getUser_num() != null) {
	        user_num = principal.getArtistVO().getUser_num();
		    ArtistVO artist = artistService.selectMember(user_num);
		    model.addAttribute("artist", artist);
	    }

	    Map<String, Object> map = new HashMap<>();
	    map.put("user_num", user_num);
	    map.put("keyfield", keyfield);
	    map.put("keyword", keyword);
	    
	    int count=0;
	    
	    // 전체/검색 레코드 수
	    if (principal.getMemberVO() != null) {
        	count = noticeService.countNotice(map);
	    } else if (principal.getArtistVO() != null) {
	    	count = noticeService.countMyNotice(map);
	    }

	    // 페이지 처리
	    PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 20, 5, "list", null);

	    List<NoticeVO> list = null;
	    
	    if (count > 0) {
	        map.put("start", page.getStartRow());
	        map.put("end", page.getEndRow());
	        if (principal.getMemberVO() != null) {
	        	list = noticeService.selectNotice(map);
	        } else if (principal.getArtistVO() != null) {
	        	list = noticeService.selectMyNotice(map);
	        }
	    }else {
	    	list=null;
	    }
	    
	    model.addAttribute("count", count);
	    model.addAttribute("list", list);
	    model.addAttribute("page", page.getPage());

	    return "/notice/noticeList";
	}
	/*========================
	 * 게시판 글상세
	 *========================*/
	//글상세
	@GetMapping("/detail")
	public String process(long notice_num,Model model) {
		log.debug("<<게시판 글상세 - c_num>> : " + notice_num);
		
		NoticeVO notice = noticeService.getNoticeByNum(notice_num);
		
		model.addAttribute("notice", notice);
		
		return "/notice/noticeView";
	}
	
	/*========================
	 * 게시판 글 등록
	 *========================*/
	//등록 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String form() {
		return "noticeWrite";
	}
	
	//전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String submit(@Valid NoticeVO noticeVO,
			             BindingResult result,
			             HttpServletRequest request,
			             RedirectAttributes redirect,
			             @AuthenticationPrincipal 
			             PrincipalDetails principal) 
			          throws IllegalStateException, IOException {
		log.debug("<<게시판 글 저장>> : " + noticeVO);
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		//회원 번호 읽기
		ArtistVO vo = principal.getArtistVO();
		noticeVO.setUser_num(vo.getUser_num());
		//파일 업로드
		noticeVO.setNotice_file(FileUtil.createFile(request, noticeVO.getUpload()));
		//글쓰기
		noticeService.insertNotice(noticeVO);
		
		//RedirectAttributes 객체는 리다이렉트 시점에 한 번만
		//사용되는 데이터를 전송.
		//브라우저에 데이터를 전송하지만 URI상에는 보이지 않는 숨겨진
		//데이터의 형태로 전달
		redirect.addFlashAttribute("result","success");
		
		//리다이렉트할 URL에 쿼리 스트링으로 추가
		//redirect.addAttribute("board_num", 10);
		
		return "redirect:/notice/list";
	}
	/*========================
	 * 글 수정
	 *========================*/
	//수정 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update")
	public String formUpdate(long notice_num,
			                 Model model,
			                 @AuthenticationPrincipal
			                 PrincipalDetails principal) {
		NoticeVO noticeVO = noticeService.getNoticeByNum(notice_num);
		
		//DB에 저장된 파일 정보 구하기
		if(principal.getMemberVO().getUser_num() != 
				                 noticeVO.getUser_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "redirect:/common/accessDenied";
		}
		
		model.addAttribute("noticeVO", noticeVO);
		
		return "noticeModify";
	}
	//수정 폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String submitUpdate(@Valid NoticeVO noticeVO,
			                  BindingResult result,
			                  HttpServletRequest request,
			                  Model model,
			                  @AuthenticationPrincipal
			                  PrincipalDetails principal) 
			                		  throws IllegalStateException, IOException {
		log.debug("<<글 수정>> : " + noticeVO);
		
		//DB에 저장된 파일 정보 구하기
		NoticeVO db_notice = 
			noticeService.getNoticeByNum(noticeVO.getNotice_num());
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(principal.getMemberVO().getUser_num() != 
				db_notice.getUser_num()) {
			return "redirect:/common/accessDenied";
		}
		//전송된 데이터 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			//title 또는 content가 입력되지 않아 유효성 체크에 걸리면
			//파일 정보를 잃어버리기 때문에 폼을 호출할 때 다시 셋팅해주어야
			//함
			noticeVO.setNotice_file(db_notice.getNotice_file());
			
			return "noticeModify";
		}
		//파이명 셋팅(FileUtil.createFile에서 파일이 없으면 null 처리했음)
		noticeVO.setNotice_file(
				FileUtil.createFile(request, 
						             noticeVO.getUpload()));
		//글 수정
		noticeService.updateNotice(noticeVO);
		
		//파일 교체 여부를 체크해서 교체했을 경우 기존 파일을 삭제
		if(noticeVO.getUpload() != null && 
				!noticeVO.getUpload().isEmpty()) {
			//기존 파일(수정 작업전 파일) 삭제 처리
			FileUtil.removeFile(request, db_notice.getNotice_file());
		}
		
		//View에 표시할 메시지
		model.addAttribute("message", "글 수정 완료!!");
		model.addAttribute("url", 
				request.getContextPath() + 
				         "/notice/detail?notice_num="
						             +noticeVO.getNotice_num());		
		return "common/resultAlert";
	}
	
	/*========================
	 * 게시판 글 삭제
	 *========================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String submitDelete(long notice_num,
			                   HttpServletRequest request,
			                   @AuthenticationPrincipal
			                   PrincipalDetails principal) {
		log.debug("<<게시판 글 삭제>> : " + notice_num);
		
		//DB에 저장된 게시판 정보 구하기
		NoticeVO db_notice = 
				       noticeService.getNoticeByNum(notice_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(principal.getMemberVO().getUser_num() != 
				db_notice.getUser_num()) {
			return "redirect:/common/accessDenied";
		}
		
		//글 삭제
		noticeService.deleteNotice(notice_num);
		
		if(db_notice.getNotice_file() != null) {
			//파일 삭제
			FileUtil.removeFile(request, db_notice.getNotice_file());
		}
		
		return "redirect:/notice/list";
	}
}
