package kr.spring.alarm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.alarm.service.AlarmService;
import kr.spring.alarm.vo.AlarmVO;

import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.payment.vo.FanVO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/alarm")
public class AlarmController {
	
	@Autowired
	private AlarmService alarmService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public AlarmVO initCommand() {
		return new AlarmVO();
	}
		/*
		 * 알람테이블에 동{적으로 넣기
		 * */
		
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/userReplyAlarm")
	public String returnFormAtrist(Model model) {
		// 파라미터 값을 모델에 추가
		
		return "alarm/nav_alarm";  // JSP 경로
	}
	
		
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/userReplyAlarm")
	public String replyAlarm(@Valid AlarmVO alarmVO, BindingResult result,
	        HttpServletRequest request, RedirectAttributes redirect, HttpSession session,
	        @AuthenticationPrincipal PrincipalDetails principal) throws IllegalStateException, IOException {
	    
	    log.debug("<<답장알람>>" + alarmVO);
	    
	    
	    //alarmService.deleteReplyAlarm();
	    
	    // 답장 알람을 vo에 설정하기
	    MemberVO memberVO = principal.getMemberVO();
	    alarmVO.setUser_num(memberVO.getUser_num());
	    
	    alarmService.insertReplyAlarm(alarmVO);
	    
	    return "redirect:/alarm/reAlarm";
	}

	// 알람 jsp에 답장알람 넣기
	// 답장 알람 열기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reAlarm")
	public String replyAlarm2(Model model,@AuthenticationPrincipal PrincipalDetails principal) {
	    AlarmVO alarmVO = new AlarmVO();
	    MemberVO memberVO = principal.getMemberVO();
	    Long user_num = memberVO.getUser_num();
	    // 사용자 번호에 해당하는 알람 리스트 가져오기
	    List<AlarmVO> alarmList = alarmService.selectInfo(user_num);
	    
	    // 모델에 리스트 추가
	    model.addAttribute("alarmList", alarmList);
	    return "alarm/nav_alarm"; 
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/userPerformAlarm")
	public String returnFormAtrist2(Model model) {
		// 파라미터 값을 모델에 추가
		model.addAttribute("ch_kind", 1); 
		return "alarm/nav_alarm";  // JSP 경로
	}
	

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/userPerformAlarm")
	public String performanceAlarm(@Valid AlarmVO alarmVO, Model model, @AuthenticationPrincipal PrincipalDetails principal) throws IllegalStateException, IOException {
	    
	    // 전체/검색 레코드수
	    MemberVO memberVO = principal.getMemberVO();
	    Long user_num = memberVO.getUser_num();
	    alarmVO.setUser_num(user_num);
	    
	    // 레코드 개수 확인
	    int count = alarmService.selectRowCount(user_num);
	    
	    // 아티스트 리스트 조회
	    List<Long> artist_list = alarmService.serchArtisList(user_num);
	   
	    log.debug("<<공연알람>>" + artist_list);
	    // 그룹 리스트 초기화
	    List<Long> group_list = new ArrayList<>();
	    
	    if (count > 0) {
	        // 아티스트 리스트에서 그룹 리스트 가져오기
	        for (Long artist_num : artist_list) {
	            System.out.println(artist_num);
	            // 그룹 리스트 가져오는 메서드를 한 번만 호출
	            
	            group_list.add(alarmService.serchGroupNum(artist_num));
	        }
	        
	        // 그룹 리스트를 기반으로 공연 알람 삽입
	        for (Long group_num : group_list) {
	            // 각 그룹에 대해 공연 알람 설정
	        	alarmVO.setUser_num(user_num);
	            alarmVO.setArtist_num(group_num);  // 그룹 번호 설정
	            alarmService.insertPerformAlarm(alarmVO);  // 공연 알람 삽입
	        }
	    }
	    
	    // 디버깅용 로그
	    log.debug("<<공연알람>>" + group_list);
	    log.debug("<<공연알람>>" + alarmVO);
	    
	    // 알람 페이지로 리디렉션
	    return "redirect:/alarm/reAlarm2";
	}

	// 알람 jsp에 답장알람 넣기
	// 답장 알람 열기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reAlarm2")
	public String replyAlarm3(Model model) {
	    AlarmVO alarmVO = new AlarmVO();
	    model.addAttribute("alarmVO", alarmVO);
	    return "alarm/nav_alarm";
	}
}
