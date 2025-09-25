package kr.spring.follow.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.follow.service.FollowService;
import kr.spring.follow.vo.FollowVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/follow")
public class FollowAjaxController {
	@Autowired
	private FollowService followService;
	
	//팔로우 리스트에서의 팔로우/언팔로우
	@PostMapping("/unfollow")
	@ResponseBody
	public Map<String, Object> following(FollowVO follow,@AuthenticationPrincipal PrincipalDetails principal){
		log.debug("<<팔로우하기 >> : " + follow);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		Long userNum = principal.getMemberVO().getUser_num();
		
		try {
			//로그인된 회원번호 셋팅
			follow.setFollower_num(principal.getMemberVO().getUser_num());
			log.debug("<<팔로우하기>> : "+follow);
			
			FollowVO followVO = followService.selectFollow(follow);
			
			if(followVO!=null) {
				followService.unfollow(follow);
				mapJson.put("status", "unfollow");
			}else {
				followService.following(follow);
				mapJson.put("status", "following");
			}
			mapJson.put("result", "success");
			mapJson.put("count", followService.countMyFollow(userNum));
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}
		return mapJson;
	};
	
	
	//아티스트 페이지에서의 팔로우/언팔로우
	@PostMapping("/follow")
	@ResponseBody
	public Map<String, Object> artiFollow(FollowVO follow,@AuthenticationPrincipal PrincipalDetails principal){
		log.debug("<<팔로우하기 >> : " + follow);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		try {
			//로그인된 회원번호 셋팅
			follow.setFollower_num(principal.getMemberVO().getUser_num());
			log.debug("<<팔로우하기>> : "+follow);
			
			FollowVO followVO = followService.selectFollow(follow);
			
			if(followVO!=null) {
				followService.unfollow(follow);
				int count = followService.countMyFollower(follow.getFollow_num());
				mapJson.put("status", "unfollow");
				mapJson.put("follow_cnt", count);
			}else {
				followService.following(follow);
				int count = followService.countMyFollower(follow.getFollow_num());
				mapJson.put("status", "following");
				mapJson.put("follow_cnt", count);
			}
			mapJson.put("result", "success");
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}
		return mapJson;
	};
}
