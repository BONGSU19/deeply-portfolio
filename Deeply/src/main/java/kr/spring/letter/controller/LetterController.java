package kr.spring.letter.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.letter.service.LetterService;
import kr.spring.letter.vo.LetterVO;
import kr.spring.letter.vo.ReplyVO;
import kr.spring.member.service.ArtistService;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.vo.FanVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/letter")
public class LetterController {
	
	@Autowired
	ArtistService artistService;
	
	@Autowired
	LetterService letterService;
	
	//편지 목록
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String getList(long artist_num,@RequestParam(defaultValue="1") int pageNum, Model model, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request) {
		loginCheck(principal, model, request);
		ArtistVO artist = artistService.selectMember(artist_num);
		model.addAttribute("artist", artist);
		
		if(principal.getArtistVO() != null) {
			model.addAttribute("message", "아티스트 계정 전용 페이지로 이동합니다");
		    model.addAttribute("url",request.getContextPath() + "/letter/artist_list?artist_num="+artist_num);
			
			return "common/resultAlert";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_num", principal.getMemberVO().getUser_num());
		map.put("artist_num", artist_num);
		
		int count = letterService.countLetterByUser(map);
		PagingUtil page = new PagingUtil(pageNum,count,11,5,"list");
		
		List<LetterVO> letters = new ArrayList<LetterVO>();
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			letters = letterService.selectLetterByUser(map);
		}
		
		int letter_limit = letterService.getLetterLimit(principal.getMemberVO().getUser_num());
		
		model.addAttribute("letter_limit", letter_limit);
		model.addAttribute("count", count);
		model.addAttribute("letters", letters);
		model.addAttribute("page", page.getPage());
		
		return "letterList";
	}
	
	//편지 작성
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String form(long artist_num, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request, Model model) {
		loginCheck(principal, model, request);
		if(principal.getArtistVO() != null) {
			model.addAttribute("message", "아티스트 계정으로 편지를 작성할 수 없습니다");
		    model.addAttribute("url",request.getContextPath() + "/letter/list?artist_num=" + artist_num);
			
			return "common/resultAlert";
		}
		
		ArtistVO artist = artistService.selectMember(artist_num);
		
		int letter_limit = letterService.getLetterLimit(principal.getMemberVO().getUser_num());
		
		model.addAttribute("letter_limit", letter_limit);
		model.addAttribute("letterVO", new LetterVO());
		model.addAttribute("artist", artist);
		return "letterWrite";
	}
	
	//편지 폼 전송
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String writeLetter(@ModelAttribute("letterVO") @Valid LetterVO letterVO,BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
							  @AuthenticationPrincipal PrincipalDetails principal) throws IllegalStateException, IOException {
		
		loginCheck(principal, model, request);
		log.debug("<<편지 전송>> : " + letterVO);
		letterVO.setLetter_num(0); // 클라이언트 입력 무시		
		
		if(letterVO.getUpload() != null) {
			String filename = FileUtil.createFile(request, letterVO.getUpload());
			letterVO.setLetter_photo(filename);
		}
		
		if(result.hasErrors()) {
		    model.addAttribute("letterVO", letterVO);
		    model.addAttribute("errors", result.getAllErrors());
		    return "letterWrite"; // 유효성 검증 실패 시 다시 작성 화면으로 이동
		}
		
		letterService.postLetter(letterVO);
		
		model.addAttribute("message", "편지를 전송하였습니다");
	    model.addAttribute("url",request.getContextPath() + "/letter/list?artist_num=" + letterVO.getArtist_num());
		letterService.minusLetterLimit(principal.getMemberVO().getUser_num());
	    
		return "common/resultAlert";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/detail")
	public String showDetail(long letter_num, Model model, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request) {
		loginCheck(principal, model, request);
		LetterVO letter = letterService.showLetterDetail(letter_num);
		ArtistVO artist = artistService.selectMember(letter.getArtist_num());
		
		
		if(principal.getMemberVO() == null || principal.getMemberVO().getUser_num() != letter.getUser_num()) {
			if(principal.getMemberVO() == null) {
				model.addAttribute("message", "로그인이 필요합니다");
			}else {
				model.addAttribute("message", "로그인 계정이 작성자 계정과 일치하지 않습니다");
			}
			model.addAttribute("url",request.getContextPath() + "/artist/detail?artist_num="+letter.getArtist_num());
		}
		
		if(letter.getLetter_photo() == null) {
			String default_img = "default_img.png";
			model.addAttribute("default_img", default_img);
		}
		
		int letter_limit = letterService.getLetterLimit(principal.getMemberVO().getUser_num());
		
		model.addAttribute("letter_limit", letter_limit);
		model.addAttribute("letter", letter);
		model.addAttribute("artist", artist);
		return "letterDetail";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reply")
	public String showReplyList(long artist_num,@RequestParam(defaultValue="1") int pageNum, Model model, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request) {
		
		loginCheck(principal, model, request);
		ArtistVO artist = artistService.selectMember(artist_num);
		
		if(principal.getArtistVO() != null) {
			model.addAttribute("message", "아티스트 계정 전용 페이지로 이동합니다");
		    model.addAttribute("url",request.getContextPath() + "/artist/list");
			
			return "common/resultAlert";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_num", principal.getMemberVO().getUser_num());
		map.put("artist_num", artist_num);
		
		int count = letterService.countReply(map);
		PagingUtil page = new PagingUtil(pageNum,count,11,5,"reply");
		
		List<ReplyVO> replies = new ArrayList<ReplyVO>();
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			replies = letterService.showReplyForUser(map);
		}
		
		int letter_limit = letterService.getLetterLimit(principal.getMemberVO().getUser_num());
		
		model.addAttribute("letter_limit", letter_limit);
		model.addAttribute("count", count);
		model.addAttribute("artist", artist);
		model.addAttribute("replies", replies);
		model.addAttribute("page", page.getPage());
		
		return "letterReply";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/artist_list")
	public String showArtistLetterList(long artist_num,@RequestParam(defaultValue="1") int pageNum, Model model, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principal) {
		loginCheck(principal, model, request);
		ArtistVO artist = artistService.selectMember(artist_num);
		
		if(principal.getMemberVO() != null) {
			model.addAttribute("message", "유저 계정 전용 페이지로 이동합니다");
		    model.addAttribute("url",request.getContextPath() + "/letter/list?artist_num="+artist_num);
			
			return "common/resultAlert";
		}
		else if(principal.getArtistVO() != null && principal.getArtistVO().getUser_num() != artist_num) {
			model.addAttribute("message", "접근 권한이 없습니다");
		    model.addAttribute("url",request.getContextPath() + "/artist/list");
			
			return "common/resultAlert";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("artist_num", artist_num);
		int count = letterService.countLetterForArtist(map);
		PagingUtil page = new PagingUtil(pageNum,count,11,5,"artist_list");
		
		List<LetterVO> letters = new ArrayList<LetterVO>();
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			letters = letterService.selectLetterForArtist(map);
		}
		
		List<FanVO> fanList = letterService.getFanByArtistNum(artist_num);
		
		for(FanVO f : fanList) {
			for(LetterVO l : letters) {
				if(f.getUser_num() == l.getUser_num()) {
					l.setIsFan(1);
				}
			}
		}
		
		model.addAttribute("count", count);
		model.addAttribute("letters", letters);
		model.addAttribute("page", page.getPage());
		model.addAttribute("artist", artist);
		
		return "artist_letterList";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/artist_reply")
	public String showArtistReplyList(long artist_num,@RequestParam(defaultValue="1") int pageNum, Model model, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request) {
		loginCheck(principal, model, request);
		ArtistVO artist = artistService.selectMember(artist_num);
		
		if(principal.getMemberVO() != null) {
			model.addAttribute("message", "유저 계정 전용 페이지로 이동합니다");
		    model.addAttribute("url",request.getContextPath() + "/artist/list");
			
			return "common/resultAlert";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("artist_num", artist_num);
		
		int count = letterService.countReplyForArtist(artist_num);
		PagingUtil page = new PagingUtil(pageNum,count,11,5,"reply");
		
		List<ReplyVO> replies = new ArrayList<ReplyVO>();
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			replies = letterService.showReplyForArtist(map);
		}
		
		log.debug("<<replies : >>" + replies);
		
		model.addAttribute("count", count);
		model.addAttribute("artist", artist);
		model.addAttribute("replies", replies);
		model.addAttribute("page", page.getPage());
		
		return "artist_replyList";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/artist_write")
	public String writeReply(long artist_num, long letter_num, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request, Model model) {
		loginCheck(principal, model, request);
		ArtistVO artist = artistService.selectMember(artist_num);
		LetterVO letter = letterService.showLetterDetail(letter_num);
		ReplyVO replyVO = new ReplyVO();
		model.addAttribute("replyVO", replyVO);
		model.addAttribute("artist",artist);
		model.addAttribute("letter", letter);
		log.debug("<<letterVO: >> :" + letter);                                
		return "artist_writeReply";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/artist_write")
	public String postReply(@ModelAttribute("replyVO") @Valid ReplyVO replyVO, BindingResult result, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request, Model model) throws IllegalStateException, IOException {
		loginCheck(principal, model, request);
		model.addAttribute("replyVO", replyVO);
		log.debug("<<replyVO>> : " + replyVO);
		
		if(result.hasErrors()) {
		    model.addAttribute("replyVO", replyVO);
		    model.addAttribute("errors", result.getAllErrors());
		    return "artist_writeReply"; // 유효성 검증 실패 시 다시 작성 화면으로 이동
		}
		
		if(replyVO.getUpload() != null) {
			replyVO.setImg(FileUtil.createFile(request, replyVO.getUpload()));
		}
		
		String fileNames = "";
		
		if(replyVO.getFile_upload() != null) {
			
			if(replyVO.getFile_upload().size() > 3) {
				model.addAttribute("message", "이미지 파일은 최대 3개까지 등록이 가능합니다");
			    model.addAttribute("url",request.getContextPath() + "/letter/artist_list?artist_num="+replyVO.getArtist_num());
				
				return "common/resultAlert";
			}
			
			for(MultipartFile file : replyVO.getFile_upload()) {
				if(!file.isEmpty()) {
					String file_name = FileUtil.createFile(request, file);
					if(fileNames.equals("")) {
						fileNames += file_name;
					}else {
						fileNames = fileNames + "," + file_name;  
					}
				}
			}
		}
		
		replyVO.setFile_name(fileNames);
		
		letterService.postReply(replyVO);
		
		
		model.addAttribute("message", "답장이 전송되었습니다");
	    model.addAttribute("url",request.getContextPath() + "/letter/artist_list?artist_num="+replyVO.getArtist_num());
		
		return "common/resultAlert";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reply_detail")
	public String showReplyDetail(long reply_num, Model model, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request) {
		loginCheck(principal, model, request);
		ReplyVO reply = letterService.showReplyDetail(reply_num);
		ArtistVO artist = artistService.selectMember(reply.getArtist_num());
		
		if(reply.getFile_name() != null) {
			
			List<String> file_name = new ArrayList<String>();
			
			String[] f_names = reply.getFile_name().split(",");
			int file_count = f_names.length;
			for(String s : f_names) {
				file_name.add(s);
			}
			
			model.addAttribute("file_name", file_name);
			model.addAttribute("file_count",file_count);
		}
		
		if(principal.getMemberVO() == null || principal.getMemberVO().getUser_num() != reply.getUser_num()) {
			if(principal.getMemberVO() == null) {
				model.addAttribute("message", "로그인이 필요합니다");
			}else {
				model.addAttribute("message", "접근이 불가합니다");
			}
			model.addAttribute("url",request.getContextPath() + "/artist/detail?artist_num="+reply.getArtist_num());
		}
		
		model.addAttribute("reply", reply);
		model.addAttribute("artist", artist);
		return "replyDetail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String deleteLetter(long letter_num, @AuthenticationPrincipal PrincipalDetails principal, Model model, HttpServletRequest request) {
		loginCheck(principal, model, request);
		LetterVO letterVO = letterService.showLetterDetail(letter_num);
		boolean isWriter = false;
		
		if(principal.getMemberVO() != null) {
			if(letterVO.getUser_num() == principal.getMemberVO().getUser_num()) {
				isWriter = true;
			}
		}else if(principal.getArtistVO() != null) {
			if(letterVO.getArtist_num() == principal.getArtistVO().getUser_num()) {
				isWriter = true;
			}
		}
		
		if(isWriter) {
			letterService.deleteLetter(letter_num);
		}else {
			model.addAttribute("message","편지를 삭제할 권한이 없습니다");
			model.addAttribute("url",request.getContextPath() + "/artist/list");
			return "common/resultAlert";
		}
		
		model.addAttribute("message","편지를 삭제하였습니다");
		model.addAttribute("url",request.getContextPath() + "/artist/list");
		return "common/resultAlert";
		
	}
	
	
	public String loginCheck(@AuthenticationPrincipal PrincipalDetails principal, Model model, HttpServletRequest request) {
		if(principal.getArtistVO() == null && principal.getMemberVO() == null) {
			model.addAttribute("message","로그인이 필요합니다. 로그인 화면으로 이동합니다.");
			model.addAttribute("url",request.getContextPath() + "/member/login");
			return "common/resultAlert";
		}
		
		return null;
	}
}
