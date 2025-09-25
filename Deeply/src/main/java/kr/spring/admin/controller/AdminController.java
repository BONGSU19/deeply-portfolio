package kr.spring.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
@Slf4j
@RequestMapping("/admin")
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
	@GetMapping("/admin_main")
	public String admin_main() {
		
		return "admin_main";
	}
	@Autowired
	private MemberService memberService;
	
	/*=======================
	 * 회원목록 - 관리자
	 *=======================*/
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin_list")
	public String getList(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage, String keyfield, String keyword,
			Model model) {
		Map<String,Object> map = 
				new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = memberService.selectRowCount(map);
		
		log.debug("<<count>> : " + count);
		
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,currentPage,
						        count,20,10,"admin_list");
		List<MemberVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = memberService.selectList(map);
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		
		return "admin_list";
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin_artist")
	public String getList2(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage, String keyfield, String keyword,
			Model model) {
		Map<String,Object> map = 
				new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = memberService.selectRowCount2(map);
		
		log.debug("<<count>> : " + count);
		
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,currentPage,
						        count,20,10,"admin_artist");
		List<MemberVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = memberService.selectList2(map);
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		
		return "admin_artist";
		
	}
}
