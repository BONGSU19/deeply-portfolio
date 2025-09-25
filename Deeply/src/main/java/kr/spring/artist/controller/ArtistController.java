package kr.spring.artist.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.item.service.ItemService;
import kr.spring.item.vo.ItemVO;
import kr.spring.member.service.ArtistService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
import kr.spring.video.service.VideoService;
import kr.spring.video.vo.VideoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/artist")
public class ArtistController {
	
	@Autowired
	ArtistService artistService;
	@Autowired
	ItemService itemService;
	@Autowired
	MemberService memberService;
	@Autowired
	VideoService videoService;
	
	//아티스트 목록
	@GetMapping("/list")
	public String getList(Model model) {
		
		List<AgroupVO> groups = artistService.selectArtistByGroup();
		
		model.addAttribute("groups", groups);
		
		return "artistList";
	}
	//아티스트 상세
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/detail")
	public String getDetail(long group_num, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		AgroupVO vo = artistService.selectArtistDetail(group_num);
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("group_num", group_num);
		
		List<ArtistVO> members = new ArrayList<ArtistVO>();
		
		if(principal.getMemberVO() != null) {
			Long user = principal.getMemberVO().getUser_num();
			log.debug("<<로그인한 회원>> : " + user);
			map.put("user_num",user);
			MemberVO users = memberService.selectMember(user);
			model.addAttribute("me",users);
			model.addAttribute("login_num", user);
			members = artistService.selectGroupMembersForFollower(map);
		}
		
		if(principal.getArtistVO() != null) {
			members = artistService.selectGroupMembers(group_num);
		}
		log.debug("<<아티스트 상세>> : " + members); 
		
		Map<String,Object> videoMap = new HashMap<String,Object>();
		
		videoMap.put("groupNum", group_num);
		
		List<VideoVO> video = videoService.selectListByGroup(videoMap);
		
		List<ItemVO> shops = itemService.selectListByUserNum(group_num);
		
		model.addAttribute("groupNum", group_num);
		model.addAttribute("video", video);
		model.addAttribute("vo", vo);
		model.addAttribute("members", members);
		model.addAttribute("shops",shops);

		
		return "artistDetail";
	}
	
	//아티스트 상세 정보 수정
	@GetMapping("/modify")
	public String form(@RequestParam long group_num, Model model, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request) {
		
		AgroupVO vo = artistService.selectArtistDetail(group_num);
		List<ArtistVO> members = artistService.selectGroupMembers(group_num);
		
		if(principal.getArtistVO() == null || !principal.getArtistVO().getGroup_name().equals(vo.getGroup_name())) {
			model.addAttribute("message", "잘못된 접근입니다");
		    model.addAttribute("url",request.getContextPath() + "/artist/list");
		    
		    return "common/resultAlert";
		}
		model.addAttribute("agroupVO", vo);
		model.addAttribute("members", members);
		
		return "artistModify";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify")
	public String modifyForm(String origin_name, @ModelAttribute @Valid AgroupVO agroupVO, BindingResult result,Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		
		if (result.hasErrors()) {
	        model.addAttribute("errors", result.getAllErrors());
	        log.debug("<<유효성 에러: >>" + result.getAllErrors());
	        return "artistModify"; // 유효성 검증 실패 시 JSP로 이동
	    }
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("agroupVO", agroupVO);
		map.put("origin_name", origin_name);
		
		String uploadedPhoto = FileUtil.createFile(request, agroupVO.getUpload());
		
		if(uploadedPhoto == null || uploadedPhoto.isEmpty()) {
			agroupVO.setGroup_photo(agroupVO.getGroup_photo());
		}else {
			agroupVO.setGroup_photo(uploadedPhoto);
		}
		
		
		artistService.updateArtistGroup(agroupVO);
		artistService.updateArtistMemberGroupName(map);
		
		if(!origin_name.equals(agroupVO.getGroup_name())) {
			model.addAttribute("message", "아티스트 정보를 수정하였습니다. 아티스트 이름이 변경되었으니 정상적인 이용을 위해 로그아웃 후 재 로그인 해주세요");
			model.addAttribute("url",request.getContextPath() + "/artist/detail?group_num="+agroupVO.getGroup_num());
			    
			return "common/resultAlert"; 
		}
		
		
		model.addAttribute("message", "아티스트 정보를 수정하였습니다");
		model.addAttribute("url",request.getContextPath() + "/artist/detail?group_num="+agroupVO.getGroup_num());
		    
		return "common/resultAlert"; // 성공 시 리다이렉트
	}
	
}
