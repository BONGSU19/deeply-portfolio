package kr.spring.follow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.follow.service.FollowService;
import kr.spring.follow.vo.FollowVO;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/follow")
public class FollowController {
	@Autowired
	private FollowService followService;
	@Autowired
	private MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/followList")
	public String getMyFollow(
			@RequestParam(defaultValue="1")int pageNum,
			@AuthenticationPrincipal 
			PrincipalDetails principalDetails,
			Model model, FollowVO follow) {

		//로그인한 회원정보 저장
		Long follower_num = principalDetails.getMemberVO().getUser_num();

		//전체 팔로우 수
		int count = followService.countMyFollow(follower_num);
		//페이지 처리
		PagingUtil page = new PagingUtil(null,null,pageNum, count, 20, 10, "followList");
		
		List<FollowVO> followList = null;
		
		if(count > 0) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("follower_num", follower_num);
			map.put("start",page.getStartRow());
			map.put("end",page.getEndRow());
			
			// Follow 리스트 가져오기
			followList = followService.getMyFollow(map);
		}
		
		MemberVO member = memberService.selectMember(follower_num);

		model.addAttribute("count", count);
		model.addAttribute("followList", followList);
		model.addAttribute("member", member);
		
		return "follow";
	}
}
