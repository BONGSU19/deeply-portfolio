package kr.spring.booking.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.booking.service.BookingService;
import kr.spring.event.vo.EventVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/booking")
public class BookingAjaxController {
	
	@Autowired
	BookingService bookingService;
	
	@GetMapping("/detail")
	@ResponseBody
	public Map<String,Object> getDetail(@RequestParam("perf_num") long perf_num,
										@RequestParam("isMembership") int isMembership,
										@AuthenticationPrincipal PrincipalDetails principal){
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("result", "success");
		EventVO event = bookingService.showEventDetail(perf_num);
		event.setIsMembership(isMembership);
		mapAjax.put("event", event);
		
		if(principal.getMemberVO() != null) {
			long user_num = principal.getMemberVO().getUser_num();
			log.debug("<<AJAX - USER_NUM >> : " + user_num);
			int isBooked = bookingService.countIfUserBooked(user_num, perf_num);
			
			if(isBooked > 0) {
				mapAjax.put("isBooked", isBooked);
			}
		}
		
		
		log.debug("<<event 제목>> : " + event.getPerf_title());
		log.debug("<<event 멤버십 상태> : " + event.getIsMembership());
		return mapAjax;
	}
	
	
}