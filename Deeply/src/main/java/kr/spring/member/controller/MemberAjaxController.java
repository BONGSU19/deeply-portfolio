package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.spring.member.service.MemberService;
import kr.spring.member.vo.ArtistVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberAjaxController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private kr.spring.member.service.ArtistService artistService;
	
	/*================================
	 * 아이디, 별명 중복 체크
	 *================================*/
	@GetMapping("confirmIdnNickName")
	@ResponseBody
	public Map<String, String> process(
	        String id, String nick_name, String group_name) {
	    log.debug("<<아이디 중복 체크>> : " + id);
	    log.debug("<<별명 중복 체크>> : " + nick_name);
	    log.debug("<<그룹 이름 중복 체크>> : " + group_name);

	    Map<String, String> mapAjax = new HashMap<String, String>();
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("id", id);
	    map.put("group_name", group_name);

	    // 아이디 중복 체크
	    if (id != null && !id.trim().isEmpty() && nick_name == null && group_name == null) {
	        map.put("nick_name", null);  // nick_name은 null로 설정
	        map.put("group_name", null);  // group_name은 null로 설정
	        MemberVO member = memberService.selectIdAndNickName(map);
	        ArtistVO artist = artistService.selectIdAndNickName(map);

	        if (member != null || artist != null) {
	            mapAjax.put("result", "idDuplicated");  // 아이디 중복
	        } else {
	            if (!Pattern.matches("^[A-Za-z0-9]{4,14}$", id)) {
	                mapAjax.put("result", "notMatchPattern");  // 패턴 불일치
	            } else {
	                mapAjax.put("result", "idNotFound");  // 아이디 미중복
	            }
	        }
	    }
	    // 닉네임 중복 체크
	    else if (nick_name != null && !nick_name.trim().isEmpty() && id == null && group_name == null) {
	        map.put("id", null);  // id는 null로 설정
	        map.put("group_name", null);  // group_name은 null로 설정
	        map.put("nick_name", nick_name);

	        MemberVO member = memberService.selectIdAndNickName(map);


	        if (member != null) {
	            mapAjax.put("result", "nickDuplicated");  // 별명 중복
	        } else {
	            if (!Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$", nick_name)) {
	                mapAjax.put("result", "notMatchPattern");  // 패턴 불일치
	            } else {
	                mapAjax.put("result", "nickNotFound");  // 별명 미중복
	            }
	        }
	    }
	    // 그룹 이름 중복 체크 (아티스트)
	    else if (group_name != null && !group_name.trim().isEmpty() && id == null && nick_name == null) {
	        map.put("id", null);  // id는 null로 설정
	        map.put("nick_name", null);  // nick_name은 null로 설정
	        map.put("group_name", group_name);

	        // 아티스트에서 그룹 이름 중복 체크
	        ArtistVO artist = artistService.selectIdAndNickName(map);
	        if (artist != null) {
	            mapAjax.put("result", "groupNameDuplicated");  // 그룹 이름 중복
	        } else {
	            mapAjax.put("result", "groupNameNotFound");  // 그룹 이름 미중복
	        }
	    }
	    // id, nick_name, group_name이 모두 비어있는 경우 처리
	    else if ((id == null || id.trim().isEmpty()) && (nick_name == null || nick_name.trim().isEmpty()) && (group_name == null || group_name.trim().isEmpty())) {
	        mapAjax.put("result", "allFieldsEmpty");  // 모든 값이 비어있을 때 처리
	    } else {
	        mapAjax.put("result", "error");  // 예상치 못한 경우 처리
	    }

	    return mapAjax;
	}

	
	@PostMapping("/updateMyPhoto")
	@ResponseBody
	public Map<String, String> processProfile(
	        @AuthenticationPrincipal PrincipalDetails principalDetails,
	        MemberVO memberVO,
	        ArtistVO artistVO) {
	    log.debug("<<PrincipalDetails>> : " + principalDetails);

	    Map<String, String> mapAjax = new HashMap<>();

	    // Check if the member is logged in
	    if (principalDetails.getMemberVO() != null) {
	        MemberVO user = principalDetails.getMemberVO();
	        memberVO.setUser_num(user.getUser_num());
	        memberService.updateProfile(memberVO);
	        mapAjax.put("userType", "member");
	    } 
	    // Check if the artist is logged in
	    else if (principalDetails.getArtistVO() != null) {
	        ArtistVO artist = principalDetails.getArtistVO();
	        artistVO.setUser_num(artist.getUser_num());
	        memberService.updateProfile2(artistVO);
	        mapAjax.put("userType", "artist");
	    } 
	    // Handle the case where neither MemberVO nor ArtistVO is available
	    else {
	        mapAjax.put("result", "error");
	        mapAjax.put("message", "Invalid user type or user not authenticated.");
	        return mapAjax;
	    }

	    // Add a success message if no errors occur
	    mapAjax.put("result", "success");
	    return mapAjax;
	}

	}

