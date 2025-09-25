package kr.spring.main.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.admin.vo.UserStatisticVO;
import kr.spring.item.service.ItemService;
import kr.spring.item.vo.ItemVO;
import kr.spring.member.service.ArtistService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.AgroupVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;

@Controller
public class MainController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ArtistService artistService;
	
	@GetMapping("/")
	public String init(@AuthenticationPrincipal PrincipalDetails principal,Model model) {
		if(principal!=null && principal.getMemberVO()!=null && principal.getMemberVO().getAuth()==9) {
			return "redirect:/main/admin";
		}
		List<ItemVO> list = itemService.showListByGroup();
		List<ItemVO> group = itemService.showListGroup();
List<AgroupVO> groups = artistService.selectArtistByGroup();
		
		model.addAttribute("groups", groups);
		model.addAttribute("list", list);
		model.addAttribute("group", group);
		return "redirect:/main/main";
		
		
		
	}
	@GetMapping("/main/main")
	public String main(Model model) {
	    // 오늘 날짜를 가져옵니다.
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	List<ItemVO> list = itemService.showListByGroup();
		List<ItemVO> group = itemService.showListGroup();
		model.addAttribute("list", list);
		model.addAttribute("group", group);
List<AgroupVO> groups = artistService.selectArtistByGroup();
		
		model.addAttribute("groups", groups);
        // 날짜별 접속자 수 기록
        memberService.recordUserCountForDate();
		return "main";//타일스 설정명
	}
	
	 @GetMapping("/user-statistics")
	    @ResponseBody
	    public List<UserStatisticVO> getUserStatistics() {
	        return memberService.getUserStatisticsByDate();  // 날짜별 접속자 수를 반환
	    }
	//관리자 페이지
	@GetMapping("/main/admin")
	public String adminMain(Model model) {
		
		return "admin";
		
	}
	
	
	
	
}




